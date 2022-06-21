package pl.training.processor.adv.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.UUID;

public class DynamicProxy {

    public static void main(String[] args) {
        var uuidGenerator = new UuidGenerator();
        var generator = (IdGenerator) Proxy.newProxyInstance(IdGenerator.class.getClassLoader(),
                new Class[] { IdGenerator.class},
                new Stopper(uuidGenerator));
        System.out.println(generator.getNext());
        //---------------------------------------------------------------------------------------
        var fakeRemoteTimeProvider = new FakeRemoteTimeProvider();
        var timeProvider = (TimeProvider) Proxy.newProxyInstance(TimeProvider.class.getClassLoader(),
                new Class[] { TimeProvider.class },
                new Repeater(fakeRemoteTimeProvider));
        timeProvider.getTimestamp();
    }

}

/*@Log
class IdGeneratorStopper implements IdGenerator {

    private IdGenerator idGenerator;

    @Override
    public String getNext() {
        var startTime = System.nanoTime();
        var result =  idGenerator.getNext();
        var totalTime = System.nanoTime() - startTime;
        log.info("Execution time for method %s equals %d ns".formatted("getNext()", totalTime));
        return result;
    }

}*/

interface IdGenerator {

    String getNext();

}

class UuidGenerator implements IdGenerator {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }

}

@RequiredArgsConstructor
@Log
class Stopper implements InvocationHandler {

    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var startTime = System.nanoTime();
        var result =  method.invoke(target, args);
        var totalTime = System.nanoTime() - startTime;
        log.info("Execution time for method %s equals %d ns".formatted(method.getName(), totalTime));
        return result;
    }

}

interface TimeProvider {

    Instant getTimestamp();

}

class FakeRemoteTimeProvider implements TimeProvider {

    @Retry
    @Override
    public Instant getTimestamp() {
        // return Instant.now();
        throw new RuntimeException();
    }

}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Retry {

    int attempts() default 3;

}

@Log
@RequiredArgsConstructor
class Repeater implements InvocationHandler {

    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var retry= target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes())
                .getAnnotation(Retry.class);
        if (retry == null) {
            throw new IllegalStateException();
        }
        var attempts = 0;
        Exception throwable;
        do {
            attempts++;
            try {
                return method.invoke(target, args);
            } catch (Exception exception) {
                log.info("%s method execution failed (attempt: %d)".formatted(method.getName(), attempts));
                throwable = exception;
            }
        } while (attempts < retry.attempts());
        throw throwable;
    }

}
