package pl.training.processor.adv.search.commons;

import io.reactivex.rxjava3.core.Observable;

import java.io.InputStream;
import java.util.Scanner;

public class ObservableInputStream {

    public static Observable<String> fromInputStream(InputStream inputStream) {
        var scanner = new Scanner(inputStream);
        return Observable.create(emitter -> {
            String text;
            do {
                text = scanner.next();
                emitter.onNext(text);
            } while (!text.equals("\\q"));
            emitter.onComplete();
        });
    }

}
