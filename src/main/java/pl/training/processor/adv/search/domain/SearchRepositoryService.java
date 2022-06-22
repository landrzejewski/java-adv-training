package pl.training.processor.adv.search.domain;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import pl.training.processor.adv.search.commons.ListCommons;
import pl.training.processor.adv.search.ports.RepositoriesProvider;
import pl.training.processor.adv.search.ports.Repository;
import pl.training.processor.adv.search.ports.SearchRepositoryUseCase;

import java.util.List;

@RequiredArgsConstructor
public class SearchRepositoryService implements SearchRepositoryUseCase {

    private static final int MIN_LENGTH = 3;

    private final RepositoriesProvider repositoriesProvider;

    @Override
    public Single<List<Repository>> search(String query) {
        var lowerCaseQuery = repositoriesProvider.getBy(query.toLowerCase())
                .toObservable();
        var upperCaseQuery = repositoriesProvider.getBy(query.toUpperCase())
                .toObservable();
        return Observable.zip(lowerCaseQuery, upperCaseQuery, ListCommons::concat)
                .flatMap(Observable::fromIterable)
                .filter(this::isValid)
                .toList();
        /*return repositoriesProvider.getBy(query)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .filter(this::isValid)
                .toList();*/
    }

    private boolean isValid(Repository repository) {
        return repository.hasLength(MIN_LENGTH);
    }

}
