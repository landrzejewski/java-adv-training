package pl.training.processor.adv.examples;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

        Class<Tasks> tasksClass = Tasks.class;
        for (Method method : tasksClass.getDeclaredMethods()) {
            var annotation = method.getAnnotation(Task.class);
            if (annotation != null) {
                method.invoke(tasksClass.getDeclaredConstructor().newInstance());
            }
        }
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
