package pl.training.processor.adv.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class DynamicProxy {

    public static void main(String[] args) {
        var uuidGenerator = new UuidGenerator();
        var generator = (IdGenerator) Proxy.newProxyInstance(IdGenerator.class.getClassLoader(),
                new Class[] { IdGenerator.class},
                new Stopper(uuidGenerator));
        System.out.println(generator.getNext());
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
