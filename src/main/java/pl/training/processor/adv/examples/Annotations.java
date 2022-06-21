package pl.training.processor.adv.examples;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static pl.training.processor.adv.examples.Profile.ProfileName.DEVELOPMENT;
import static pl.training.processor.adv.examples.Profile.ProfileName.TEST;

@Profile(description = "Some description", value = DEVELOPMENT, tags = "test")
@Profile(description = "Other description", value = TEST)

/*@Profiles({
        @Profile(description = "Some description", value = DEVELOPMENT, tags = "test"),
        @Profile(description = "Other description", value = TEST)
})*/
public class Annotations {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(Profile.CONFIG_FILE_NAME);
        @SuppressWarnings("unchecked")
        var container = new Container();

        //-------------------------------------------------------------

        var tasksClass = Tasks.class;
        /*for (Method method : tasksClass.getDeclaredMethods()) {
            var annotation = method.getAnnotation(Task.class);
            if (annotation != null) {
                method.invoke(tasksClass.getDeclaredConstructor().newInstance());
            }
        }*/

        Arrays.stream(Tasks.class.getDeclaredMethods())
                .filter(Annotations::hasTestAnnotation)
                .forEach(runMethod(tasksClass));
    }

    private static Consumer<Method> runMethod(Class<Tasks> tasksClass) {
        return method -> {
            try {
                method.invoke(tasksClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static boolean hasTestAnnotation(Method method) {
        return method.getAnnotation(Task.class) != null;
    }

}

@FunctionalInterface
interface Converter<I, O> {

    @Deprecated(forRemoval = true, since = "1.11")
    O convert(I input);

}

class Container {

    private List data;

    public Container() {
        this.data = new ArrayList();
    }

}
