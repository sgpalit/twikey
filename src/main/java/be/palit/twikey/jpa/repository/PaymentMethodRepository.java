package be.palit.twikey.jpa.repository;

import be.palit.twikey.jpa.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    List<PaymentMethod> findByUserId(UUID userId);
}
