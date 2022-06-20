package pl.training.processor;

import jakarta.xml.bind.annotation.*;

import java.util.Set;

@XmlRootElement(name = "configuration-descriptors")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDescriptorWrapper {

    @XmlElementWrapper(name = "descriptors")
    @XmlElement(name = "entry")
    private Set<ConfigurationDescriptor> configurationDescriptors;

    public ConfigurationDescriptorWrapper() {
    }

    public ConfigurationDescriptorWrapper(Set<ConfigurationDescriptor> configurationDescriptors) {
        this.configurationDescriptors = configurationDescriptors;
    }

    public Set<ConfigurationDescriptor> getConfigurationDescriptors() {
        return configurationDescriptors;
    }

}
