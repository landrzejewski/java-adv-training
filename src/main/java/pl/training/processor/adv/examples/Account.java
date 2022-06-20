package pl.training.processor.adv.examples;

import java.math.BigDecimal;

public sealed class Account permits PremiumAccount {

    protected BigDecimal balance = BigDecimal.ZERO;

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }

}
