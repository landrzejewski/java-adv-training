package pl.training.processor.adv.search.adapters.view;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import pl.training.processor.adv.search.ports.Repository;
import pl.training.processor.adv.search.ports.SearchRepositoryUseCase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static pl.training.processor.adv.search.commons.ObservableInputStream.fromInputStream;

@RequiredArgsConstructor
public class ConsoleClient {

    private final SearchRepositoryUseCase searchService;

    public Observable<List<Repository>> start() {
        return fromInputStream(System.in)
                .debounce(5, TimeUnit.SECONDS)
                .flatMap(text -> searchService.search(text).toObservable())
                .subscribeOn(Schedulers.io());
    }

    public void onRepositoriesLoaded(List<Repository> repositories) {
        repositories.stream()
                .map(Repository::name)
                .map(String::toLowerCase)
                .sorted()
                .distinct()
                .forEach(System.out::println);
    }

}
