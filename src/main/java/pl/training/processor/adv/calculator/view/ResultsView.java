package pl.training.processor.adv.calculator.view;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import pl.training.processor.adv.calculator.commons.View;
import pl.training.processor.adv.calculator.controller.CalculatorController;

import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ResultsView implements View {

    private final CalculatorController controller;

    @Override
    public void render(Map<String, String> data) {
        System.out.println("Result: %s".formatted(data.get("result")));
        controller.showMenu();
    }

}
