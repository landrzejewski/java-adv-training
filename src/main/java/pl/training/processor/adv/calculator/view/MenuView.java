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
public class MenuView implements View {

    private final CalculatorController controller;
    private final Scanner scanner;

    @Override
    public void render(Map<String, String> data) {
        System.out.println("Menu");
        System.out.println("1 - Dodaj");
        System.out.println("2 - Odejmij");
        System.out.println("3 - Wyjdź");
        controller.selectOption(scanner.nextInt());
    }

}
