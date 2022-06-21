package pl.training.processor;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.util.DecoratedCollection;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import static java.lang.Character.toLowerCase;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.tools.Diagnostic.Kind.ERROR;

@SupportedAnnotationTypes("pl.training.processor.JsonType")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class JsonTypeProcessor extends AbstractProcessor {

    private final Set<String> excludedMethods = Set.of("getClass");
    private final Mustache template = new DefaultMustacheFactory().compile("json-type.mustache");

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
                .forEach(typeModel -> process(typeModel, typeElement));
    }

    private void process(TypeModel typeModel, TypeElement typeElement) {
        var filer = processingEnv.getFiler();
        try {
            var fileObject = filer.createSourceFile(typeModel.getQualifiedTargetClassName(), typeElement);
            try (var writer = fileObject.openWriter()) {
                template.execute(writer, typeModel);
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(ERROR, "Error: " + e.getMessage());
        }
    }

    private TypeModel toFields(TypeElement typeElement) {
        var fields = processingEnv.getElementUtils()
                .getAllMembers(typeElement).stream()
                .filter(this::isMethod)
                .map(this::toSimpleName)
                .filter(this::isGetter)
                .filter(methodName -> !excludedMethods.contains(methodName))
                .map(Field::new)
                .toList();
        return new TypeModel(getPackageName(typeElement), toSimpleName(typeElement), fields);
    }

    private String getPackageName(TypeElement typeElement) {
        if (typeElement.getEnclosingElement() instanceof  PackageElement packageElement) {
            return packageElement.getQualifiedName().toString();
        } else {
            processingEnv.getMessager().printMessage(ERROR, "Invalid type element");
            return "";
        }
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

    private record TypeModel(String packageName, String className, List<Field> fields) {

        private static final String CLASS_NAME_SUFFIX = "JsonMapper";

        public String getQualifiedTargetClassName() {
            return "%s.%s".formatted(getPackageName(), getTargetClassName());
        }

        public String getPackageName() {
            return packageName;
        }

        public String getSourceClassName() {
            return className;
        }

        public String getTargetClassName() {
            return className + CLASS_NAME_SUFFIX;
        }

        public DecoratedCollection<Field> getFields() {
            return new DecoratedCollection<>(fields);
        }

    }

    private record Field(String getterName) {

        public static final String GETTER_PREFIX = "get";
        private static final int START_INDEX = 1;

        public String getFieldName() {
            var name = getterName.substring(GETTER_PREFIX.length());
            return toLowerCase(name.charAt(0)) + name.substring(START_INDEX);
        }

        public String getGetterName() {
            return getterName();
        }

    }

}
