/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.api;

import nl.yrck.comicsprout.api.services.CharacterService;
import nl.yrck.comicsprout.api.services.IssueService;
import nl.yrck.comicsprout.api.services.SearchService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ComicVine {

    public static final String API_HOST = "comicvine.gamespot.com";
    public static final String API_URL = "http://" + API_HOST + "/api/";

    public static String apiKey;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logging;

    private boolean enableDebugLogging;

    public ComicVine(String apiKey) {
        ComicVine.apiKey = apiKey;
    }

    public String apiKey() {
        return apiKey;
    }

    public ComicVine enableDebugLogging(boolean enable) {
        this.enableDebugLogging = enable;
        if (logging != null) {
            logging.setLevel(enable ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        return this;
    }

    protected Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient());
    }

    protected synchronized OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            setOkHttpClientDefaults(builder);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
        builder.addNetworkInterceptor(new ComicVineInterceptor(this));
        if (enableDebugLogging) {
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }

    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build();
        }
        return retrofit;
    }

    public CharacterService characters() {
        return getRetrofit().create(CharacterService.class);
    }

    public IssueService issues() {
        return getRetrofit().create(IssueService.class);
    }

    public SearchService search() {
        return getRetrofit().create(SearchService.class);
    }

}
