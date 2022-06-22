package pl.training.processor.adv.search;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.mapstruct.factory.Mappers;
import pl.training.processor.adv.search.adapters.provider.GithubApi;
import pl.training.processor.adv.search.adapters.provider.RetrofitProviderMapper;
import pl.training.processor.adv.search.adapters.provider.RetrofitRepositoriesProvider;
import pl.training.processor.adv.search.domain.SearchRepositoryService;
import pl.training.processor.adv.search.ports.RepositoriesProvider;
import pl.training.processor.adv.search.ports.SearchRepositoryUseCase;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

public class SerachConfiguration {

    private static final String GITHUB_API = "https://api.github.com";

    public HttpLoggingInterceptor loggingInterceptor() {
        var interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BASIC);
        return interceptor;
    }

    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public GithubApi githubApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GithubApi.class);
    }

    public RetrofitProviderMapper providerMapper() {
        return Mappers.getMapper(RetrofitProviderMapper.class);
    }

    public RepositoriesProvider repositoriesProvider(GithubApi githubApi, RetrofitProviderMapper mapper) {
        return new RetrofitRepositoriesProvider(githubApi, mapper);
    }

    public SearchRepositoryUseCase searchRepositoryUseCase(RepositoriesProvider repositoriesProvider) {
        return new SearchRepositoryService(repositoriesProvider);
    }

    public SearchRepositoryUseCase initialize() {
        var interceptor = loggingInterceptor();
        var clientHttp = okHttpClient(interceptor);
        var githubApi = githubApi(clientHttp);
        var providerMapper = providerMapper();
        var repositoriesProvider = repositoriesProvider(githubApi, providerMapper);
        return searchRepositoryUseCase(repositoriesProvider);
    }

}
