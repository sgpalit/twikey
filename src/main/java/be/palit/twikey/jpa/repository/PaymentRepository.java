package be.palit.twikey.jpa.repository;

import be.palit.twikey.jpa.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {


}
