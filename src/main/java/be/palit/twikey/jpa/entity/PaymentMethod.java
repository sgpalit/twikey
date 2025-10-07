package be.palit.twikey.jpa.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tw_payment_method")
public class PaymentMethod {

    public enum Type {PRIMARY, SECONDARY}

    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column
    private Type type;
    @Column
    private String cardNumber;
    @Column
    private Integer ExpirationMonth;
    @Column
    private Integer expirationYear;
    @Column
    private Integer cvv;

    public PaymentMethod() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getExpirationMonth() {
        return ExpirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        ExpirationMonth = expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
}
