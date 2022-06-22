package pl.training.processor.adv.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.Arrays;
import java.util.function.*;

@Log
public class Functions {

    private int abs(int value) {
        return value < 0 ? -value : value;
    }

    private int power(int value) {
        return value * value;
    }

    private int powerAbs(int value) {
        return power(abs(value));
    }

    private <A, B, C> Function<A, C> compose(Function<B, C> f, Function<A, B> g) {
        return a -> f.apply(g.apply(a));
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
        var powerAbs = compose(this::power, this::abs);
        powerAbs.apply(3);

        Option<Integer> optionalLong = new Some<>(1);
        Option<Integer> emptyOptionalLong = new None<>();
        var result = optionalLong
                .flatMap(value -> divide(value, 3))
                .map(this::abs)
                .getOrElse(() -> 3);

        System.out.println(result);

        //------------------------------------------------------------

        //var list = new NotEmpty<Long>(1L, new NotEmpty<>(2L, new Empty<>()));

        var list = FunctionalList.of(2, 3, 4, 5);
        list.map(this::power);
    }

    private Option<Integer> divide(Integer value, Integer divider) {
        if (value == 0) {
            return new None<>();
        } else {
            return new Some<>(value / divider);
        }
    }

    sealed class Option<A> permits Some, None  {

        public <B> Option<B> map(Function<A, B> mapper) {
            return switch (this) {
                case None<A> none -> new None<>();
                case Some<A> some -> new Some<>(mapper.apply(some.value));
                default -> throw new IllegalArgumentException();
            };
        }

        public <B> Option<B> flatMap(Function<A, Option<B>> mapper) {
            /*return switch (map(mapper)) {
                case None<B> none -> new None<>();
                case Some<B> some -> new Some<>(some.value);
                default -> throw new IllegalArgumentException();
            };*/
            return map(mapper).getOrElse(None::new);
        }

        public A getOrElse(Supplier<A> supplier) {
            return switch (this) {
                case None<A> none -> supplier.get();
                case Some<A> some -> some.value;
                default -> throw new IllegalArgumentException();
            };
        }

        public Option<A> filter(Predicate<A> predicate) {
            return flatMap(value -> predicate.test(value) ? new Some<>(value) : new None<>());
        }

    }

    @RequiredArgsConstructor
    final class Some<A> extends Option<A> {

        private final A value;

    }

    final class None<A> extends Option<A> {
    }


    private <A, B> Function<Option<A>, Option<B>> lift(Function<A, B> function) {
        return optionA -> optionA.map(function);
    }

    static sealed class FunctionalList<A> permits Empty, NotEmpty {

        private FunctionalList() {
        }

        public static <A> FunctionalList<A> of(A ... values) {
            if (values.length == 0) {
                return new Empty<>();
            }
            var tail = Arrays.copyOfRange(values, 1, values.length);
            return new NotEmpty<>(values[0], FunctionalList.of(tail));
        }

        public <B> FunctionalList<B> map(Function<A, B> mapper) {
            return switch (this) {
                case Empty<A> empty -> new Empty<>();
                case NotEmpty<A> notEmpty -> new NotEmpty<>(mapper.apply(notEmpty.head), notEmpty.tail.map(mapper));
                default -> throw new IllegalArgumentException();
            };
        }

    }

    static final class Empty<A> extends FunctionalList<A> {
    }



    @RequiredArgsConstructor
    static final class NotEmpty<A> extends FunctionalList<A> {

        private final A head;
        private final FunctionalList<A> tail;

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
