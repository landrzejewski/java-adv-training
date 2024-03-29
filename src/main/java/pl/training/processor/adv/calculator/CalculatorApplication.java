package pl.training.processor.adv.calculator;

import org.jboss.weld.environment.se.Weld;
import pl.training.processor.adv.calculator.controller.CalculatorController;

public class CalculatorApplication {

    public static void main(String[] args) {
        var weld = new Weld();
        try (var container = weld.initialize()) {
            container.select(CalculatorController.class).get().showMenu();
        }
    }

}
