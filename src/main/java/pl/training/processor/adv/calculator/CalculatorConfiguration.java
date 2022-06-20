package pl.training.processor.adv.calculator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import java.util.Scanner;

@ApplicationScoped
public class CalculatorConfiguration {

    @Singleton
    @Produces
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
