package pl.training.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.NOTE;

@SupportedAnnotationTypes("pl.training.processor.Log")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class LogProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment roundEnv) {
        elements.forEach(typeElement -> onTypeElement(typeElement, roundEnv));
        return true;
    }

    private void onTypeElement(TypeElement typeElement, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(typeElement).forEach(this::onElement);
    }

    private void onElement(Element element) {
        var message = "Hit at %s".formatted(element);
        processingEnv.getMessager().printMessage(NOTE, message);
    }

}
