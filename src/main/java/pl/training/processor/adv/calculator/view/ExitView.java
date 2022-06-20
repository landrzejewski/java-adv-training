package pl.training.processor.adv.calculator.view;

import jakarta.enterprise.context.ApplicationScoped;
import pl.training.processor.adv.calculator.commons.View;

import java.util.Map;

@ApplicationScoped
public class ExitView implements View {

    @Override
    public void render(Map<String, String> data) {
        System.out.println("Closing calculator");
    }

}
