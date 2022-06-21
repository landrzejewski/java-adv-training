package pl.training.processor.adv.examples;

import lombok.extern.java.Log;

import java.time.Instant;
import java.util.function.*;

@Log
public class Functions {

    private int abs(int value) {
        return value < 0 ? -value : value;
    }

    private String prepareResultMessage(Integer value, IntUnaryOperator calculation) {
        return "Result for %d is %d".formatted(value, calculation.applyAsInt(value));
    }

    private Converter<Integer, String> toStringConverter = Object::toString;
    private Converter<Integer, Long> toLongConverter = Long::valueOf;

    private Integer loop(Integer n, Integer result) {
        return n <= 0 ? result : loop(n - 1, n * result);
    }

    private Integer factorial(Integer number) {
        return loop(number, 1);
    }

    @FunctionalInterface
    interface Converter<I, O> {

        O convert(I input);

    }

    private void start() {
        log.info("Result: " + (abs(-3) == (3 < 0 ? -3 : 3)));
        log.info("Factorial for 4: " + factorial(4));
        log.info("Abs: " + prepareResultMessage(4, this::abs));
        log.info("Factorial: " + prepareResultMessage(4, this::factorial));
    }

    public static void main(String[] args) {
        new Functions().start();

        var dataLoader = new DataLoader();

        /*dataLoader.load(new DataLoaderCallback() {
            @Override
            public void onDataLoaded(String data) {
                log.info("Loaded data: " + data);
            }
        });*/

        /*dataLoader.load(data -> log.info("Loaded data: " + data));
        dataLoader.load((String data) -> log.info("Loaded data: " + data));
        dataLoader.load((var data) -> log.info("Loaded data: " + data));
        dataLoader.load(data -> {
            log.info("Loaded data: " + data);
        });*/
        //dataLoader.load(Functions::onData);

        //Predicate<Integer> isEven = value -> value % 2 == 0;
        IntPredicate isEven = value -> value % 2 == 0;
        // log.info("%d is even = %s".formatted(6, isEven.test(6)));

        Supplier<Instant> timeSupplier = Instant::now;
        timeSupplier.get();

        Consumer<String> textConsumer = log::info;
        //textConsumer.accept("Hello");

        Function<Integer, String> converter = Object::toString;
        converter.apply(4);
    }

    private static void onData(String data) {
        log.info("Loaded data: " + data);
    }

}

@Log
class DataLoader {

    public void load(DataLoaderCallback callback) {
        log.info("Loading...");
        callback.onDataLoaded("Some data...");
    }

}

@FunctionalInterface
interface DataLoaderCallback {

    void onDataLoaded(String data);

}
