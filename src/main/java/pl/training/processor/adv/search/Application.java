package pl.training.processor.adv.search;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.java.Log;
import pl.training.processor.adv.search.commons.ObservableInputStream;
import pl.training.processor.adv.search.ports.Repository;

import java.util.List;

import static pl.training.processor.adv.search.commons.ObservableInputStream.fromInputStream;

@Log
public class Application {

    public static void main(String[] args) throws InterruptedException {
        var searchService = new SerachConfiguration().initialize();

        var disposable = fromInputStream(System.in)
                .flatMap(text -> searchService.search(text).toObservable())
                .subscribeOn(Schedulers.io())
                .subscribe(Application::onResponse, throwable -> log.warning(throwable.toString()));

        Thread.sleep(10_000);
        log.info("Disposing subscriptions");
        disposable.dispose();

    }

    private static void onResponse(List<Repository> repositories) {
        repositories.stream()
                .map(Repository::name)
                .map(String::toLowerCase)
                .sorted()
                .distinct()
                .forEach(System.out::println);
    }

}
