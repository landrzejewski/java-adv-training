package pl.training.processor;

import jakarta.xml.bind.JAXB;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.StandardLocation.CLASS_OUTPUT;

@SupportedAnnotationTypes("pl.training.processor.Configurable")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class ConfigurableProcessor extends AbstractProcessor {

    private static final String DEFAULT_DESCRIPTION = "None";
    private static final String DESCRIPTORS_FILE = "descriptors.xml";

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment roundEnv) {
        elements.forEach(typeElement -> onTypeElement(typeElement, roundEnv));
        return true;
    }

    private void onTypeElement(TypeElement typeElement, RoundEnvironment roundEnv) {
        var descriptors = roundEnv.getElementsAnnotatedWith(typeElement).stream()
                .map(this::toDescriptor)
                .collect(toSet());
        saveDescriptors(new ConfigurationDescriptorWrapper(descriptors));
    }

    private ConfigurationDescriptor toDescriptor(Element element) {
        return new ConfigurationDescriptor(element.toString(), DEFAULT_DESCRIPTION);
    }

    private File getFile() throws IOException {
        var filer = processingEnv.getFiler();
        var fileObject = filer.getResource(CLASS_OUTPUT, "", DESCRIPTORS_FILE);
        return new File(fileObject.toUri());
    }

    private void saveDescriptors(ConfigurationDescriptorWrapper descriptors) {
        try {
            var file = getFile();
            createIfNotExists(file);
            JAXB.marshal(descriptors, file);
        } catch (Exception exception) {
            processingEnv.getMessager().printMessage(ERROR, "Failed to write descriptor file " + exception.getMessage());
        }
    }

    private void createIfNotExists(File file) throws IOException {
        var parentFolder = file.getParentFile();
        if (!parentFolder.exists() && !parentFolder.mkdirs()) {
            throw new IOException("Failed to create target directory");
        }
    }

}
