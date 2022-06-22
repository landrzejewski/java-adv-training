package pl.training.processor.adv.search.adapters.provider;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import pl.training.processor.adv.search.ports.Repository;

import java.util.List;

@Mapper
public interface RetrofitProviderMapper {

    @IterableMapping(elementTargetType = Repository.class)
    List<Repository> toDomain(List<RepositoryDto> repositoryDtos);

}
