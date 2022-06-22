package pl.training.processor.adv.search.adapters.provider;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {

    @GET("/search/repositories")
    Single<QueryResultDto> get(@Query("q") String query);

}
