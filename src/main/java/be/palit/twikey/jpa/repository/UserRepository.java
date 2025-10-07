package be.palit.twikey.jpa.repository;

import be.palit.twikey.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
