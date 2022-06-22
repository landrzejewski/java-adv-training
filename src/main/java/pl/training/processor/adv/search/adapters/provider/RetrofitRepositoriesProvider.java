package pl.training.processor.adv.search.adapters.provider;

import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import pl.training.processor.adv.search.ports.RepositoriesProvider;
import pl.training.processor.adv.search.ports.Repository;

import java.util.List;

@RequiredArgsConstructor
public class RetrofitRepositoriesProvider implements RepositoriesProvider {

    private final GithubApi githubApi;
    private final RetrofitProviderMapper mapper;

    @Override
    public Single<List<Repository>> getBy(String query) {
        return githubApi.get(query)
                .map(QueryResultDto::getItems)
                .map(mapper::toDomain);
    }

}
