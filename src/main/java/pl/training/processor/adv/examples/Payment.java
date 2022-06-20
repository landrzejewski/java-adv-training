package pl.training.processor.adv.examples;

import java.math.BigDecimal;
import java.time.Instant;

public record Payment(BigDecimal value, Currency currency, Instant timestamp) {

    public static final Currency  DEFAULT_CURRENCY =  Currency.PLN;

    public Payment {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
    }

    Payment(BigDecimal value) {
        this(value, DEFAULT_CURRENCY, Instant.now());
    }

    public boolean isGreaterThan(Payment payment) {
        return this.value.compareTo(payment.value) > 0;
    }

}
