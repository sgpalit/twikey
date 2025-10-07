package be.palit.twikey.service;

import be.palit.twikey.jpa.entity.Payment;
import be.palit.twikey.jpa.entity.PaymentMethod;
import be.palit.twikey.jpa.entity.Subscription;
import be.palit.twikey.jpa.repository.PaymentMethodRepository;
import be.palit.twikey.jpa.repository.PaymentRepository;
import be.palit.twikey.jpa.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessPaymentService {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentService.class);

    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    private final Pageable pageable = PageRequest.of(0, 1000);

    public ProcessPaymentService(
            SubscriptionRepository subscriptionRepository,
            PaymentRepository paymentRepository,
            PaymentMethodRepository paymentMethodRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public void startPaymentProcess(Integer month, Integer year) {
        LocalDate now = LocalDate.now();

        processSubscriptions(month, year, now);
    }

    private void processSubscriptions(Integer month, Integer year, LocalDate now) {
        Page<Subscription> subscriptions = subscriptionRepository.findAllActiveSubscriptionsToBeProcessed(pageable, now, month, year);
        if (subscriptions.getTotalElements() == 0) {
            log.info("No subscriptions found");
        } else {
            processPayments(subscriptions, month, year);
            if (subscriptions.getTotalElements() > pageable.getPageSize()) {
                processSubscriptions(month, year, now);
            }
        }
    }

    private void processPayments(Page<Subscription> subscriptions, Integer month, Integer year) {

        LocalDate invoiceDate = LocalDate.of(year, month, 1);
        for (Subscription subscription : subscriptions.getContent()) {

            UUID userId = subscription.getUser().getId();
            List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserId(userId);
            Optional<PaymentMethod> primaryPaymentMethodOpt = paymentMethods.stream()
                    .filter(p -> p.getType() == PaymentMethod.Type.PRIMARY)
                    .filter(p-> LocalDate.of(p.getExpirationYear(), p.getExpirationMonth(), 1).isAfter(invoiceDate))
                    .findFirst();
            // we can also check secondary etc, but will skip it for now.
            // user needs to be notified that it has no valid payment methods anymore.
            PaymentMethod paymentMethod = primaryPaymentMethodOpt.orElse(null);

            // insert payment INIT record
            Payment payment = new Payment();
            payment.setSubscription(subscription);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentMonth(month);
            payment.setPaymentYear(year);
            payment.setStatus(paymentMethod == null ? Payment.Status.ERROR : Payment.Status.INIT);
            paymentRepository.save(payment);

            if(paymentMethod != null) {
                // TODO: call bank

                // update payment record with PAID or ERROR according to the answer of bank
                payment.setStatus(Payment.Status.PAID);
                paymentRepository.save(payment);

                subscription.setNextPaymentDate(LocalDate.of(year, month + 1, subscription.getStartDate().getDayOfMonth()));
                subscriptionRepository.save(subscription);
            }

        }
    }

}
