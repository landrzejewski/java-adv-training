package pl.training.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

import static java.lang.Character.toLowerCase;
import static javax.lang.model.element.ElementKind.METHOD;

@SupportedAnnotationTypes("pl.training.processor.JsonType")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class JsonTypeProcessor extends AbstractProcessor {

    private final Set<String> excludedMethods = Set.of("getClass");

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment roundEnv) {
        elements.forEach(typeElement -> onTypeElement(typeElement, roundEnv));
        return true;
    }

    private void onTypeElement(TypeElement typeElement, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(typeElement).stream()
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .map(this::toFields)
                .forEach(this::process);
    }

    private void process(List<Field> fields) {
        fields.forEach(field -> processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, field.toString()));
    }

    private List<Field> toFields(TypeElement typeElement) {
        return processingEnv.getElementUtils().getAllMembers(typeElement).stream()
                .filter(this::isMethod)
                .map(this::toSimpleName)
                .filter(this::isGetter)
                .filter(methodName -> !excludedMethods.contains(methodName))
                .map(Field::new)
                .toList();
    }

    private boolean isMethod(Element element) {
        return element.getKind() == METHOD;
    }

    private String toSimpleName(Element element) {
        return element.getSimpleName().toString();
    }

    private boolean isGetter(String methodName) {
        return methodName.startsWith(Field.GETTER_PREFIX);
    }

    private record Field(String getterName) {

        public static final String GETTER_PREFIX = "get";

        public String getFieldName() {
            var name = getterName.substring(GETTER_PREFIX.length());
            return toLowerCase(name.charAt(0)) + name.substring(1);
        }

    }

}
