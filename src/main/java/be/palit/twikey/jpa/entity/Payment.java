package be.palit.twikey.jpa.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tw_payment")
public class Payment {

    public enum Status {INIT, PAID, ERROR}

    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column
    private Integer paymentMonth;
    @Column
    private Integer paymentYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Payment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(Integer paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public Integer getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(Integer paymentYear) {
        this.paymentYear = paymentYear;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
