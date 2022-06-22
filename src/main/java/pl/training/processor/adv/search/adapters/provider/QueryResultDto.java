package pl.training.processor.adv.search.adapters.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QueryResultDto {

    private List<RepositoryDto> items;

}
