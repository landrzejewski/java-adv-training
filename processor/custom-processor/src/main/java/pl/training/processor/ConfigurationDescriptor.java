package pl.training.processor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "descriptor")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDescriptor {

    private String className;
    private String description;

    public ConfigurationDescriptor() {
    }

    public ConfigurationDescriptor(String className, String description) {
        this.className = className;
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

}
