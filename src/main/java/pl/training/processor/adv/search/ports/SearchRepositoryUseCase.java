package pl.training.processor.adv.search.ports;

import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface SearchRepositoryUseCase {

    Single<List<Repository>> search(String query);

}
