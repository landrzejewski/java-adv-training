package pl.training.processor.adv.search;

import lombok.extern.java.Log;

@Log
public class Application {

    public static void main(String[] args) throws InterruptedException {
        var client =  new SerachConfiguration().getClient();
        var disposable = client.start()
                .subscribe(client::onRepositoriesLoaded, throwable -> log.warning(throwable.toString()));
        Thread.sleep(100_000);
        log.info("Disposing subscriptions");
        disposable.dispose();
    }


}
