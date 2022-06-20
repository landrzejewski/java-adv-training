package pl.training.processor.adv.examples;

import jakarta.validation.Validation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static pl.training.processor.adv.examples.Currency.PLN;

public class OtherExamples {

    public static void main(String[] args) {
        var payment = new Payment(BigDecimal.TEN, PLN, Instant.now());
        var otherPayment = new Payment(BigDecimal.TEN, PLN, Instant.now());
        System.out.println("Payment value: %f %s".formatted(payment.value(), payment.currency()));
        System.out.println(payment.toString());
        System.out.println(payment.equals(otherPayment));
        System.out.println(payment.isGreaterThan(otherPayment));

        //--------------------------------------------------------------------

        Account account = new PremiumAccount();
        /*if (account instanceof PremiumAccount) {
            var premiumAccount = (PremiumAccount) account;
            System.out.println(premiumAccount.getInfo());
        }*/

        if (account instanceof PremiumAccount premiumAccount) {
            System.out.println(premiumAccount.getInfo());
        }

        switch (account) {
            case PremiumAccount premiumAccount:
                System.out.println(premiumAccount.getInfo());
                break;
            default:
                System.out.println(account.toString());
        }

        //--------------------------------------------------------------------

        var order = new Order(1L, List.of(1L, 3L));
        System.out.println(order);

        var validatorFactory = Validation.buildDefaultValidatorFactory();
        var validator = validatorFactory.getValidator();
        var user = User.builder()
                .age(16)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("training.pl")
                .build();
        validator.validate(user).forEach(violation -> System.out.println(violation.getMessage()));
    }

}
