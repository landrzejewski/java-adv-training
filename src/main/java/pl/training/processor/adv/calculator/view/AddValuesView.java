package pl.training.processor.adv.calculator.view;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import pl.training.processor.adv.calculator.commons.View;
import pl.training.processor.adv.calculator.controller.CalculatorController;

import java.util.Map;
import java.util.Scanner;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AddValuesView implements View {

    private final CalculatorController controller;
    private final Scanner scanner;

    @Override
    public void render(Map<String, String> data) {
        System.out.println("Dodawanie:");
        System.out.println("Podaj pierwszą liczbę: ");
        var firstValue = scanner.nextBigDecimal();
        System.out.println("Podaj drugą liczbę: ");
        var secondValue = scanner.nextBigDecimal();
        controller.addValues(firstValue, secondValue);
    }

}
