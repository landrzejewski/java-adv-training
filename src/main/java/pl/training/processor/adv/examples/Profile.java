package pl.training.processor.adv.examples;

import java.lang.annotation.*;

@Repeatable(Profiles.class)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Profile {

    String CONFIG_FILE_NAME = "config.yml";

    // primitive types, String, Class, enums, annotations, arrays
    ProfileName value(); // required
    String description() default "";
    String[] tags() default {};

    enum ProfileName {

        TEST, DEVELOPMENT, PRODUCTION;

    }

}

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Profiles {

    Profile[] value();

}
