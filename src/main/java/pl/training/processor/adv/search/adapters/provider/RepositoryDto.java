package pl.training.processor.adv.search.adapters.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RepositoryDto {

    private String name;

}
