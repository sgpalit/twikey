package be.palit.twikey.jpa.repository;

import be.palit.twikey.jpa.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {


    @Query(
            value = """
                    SELECT s
                    FROM Subscription s
                    LEFT JOIN Payment p on (p.subscription.id = s.id AND p.paymentMonth=:month AND p.paymentYear=:year)
                    WHERE s.status='ACTIVE' AND s.nextPaymentDate < :process_date AND p.id IS NULL
                    """,
            countQuery = """
                    SELECT count(s)
                    FROM Subscription s
                    LEFT JOIN Payment p on (p.subscription.id = s.id AND p.paymentMonth=:month AND p.paymentYear=:month)
                    WHERE s.status='ACTIVE' AND p.id IS NULL
                    """
    )
    Page<Subscription> findAllActiveSubscriptionsToBeProcessed(
            Pageable pageable,
            @Param("process_date") LocalDate process_date,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

}
