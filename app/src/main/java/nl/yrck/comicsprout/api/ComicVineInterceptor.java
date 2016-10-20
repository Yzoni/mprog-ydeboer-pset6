package nl.yrck.comicsprout.api;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ComicVineInterceptor implements Interceptor {

    private ComicVine comicVine;

    public ComicVineInterceptor(ComicVine comicVine) {
        this.comicVine = comicVine;
    }

    public Response intercept(Chain chain) throws IOException {
        return handleIntercept(chain);
    }

    public static Response handleIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!ComicVine.API_HOST.equals(request.url().host())) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }
        HttpUrl currentUrl = chain.request().url();
        Request.Builder builder = request.newBuilder();
        currentUrl = currentUrl.newBuilder().addQueryParameter("api_key", ComicVine.apiKey).build();
        builder.url(currentUrl);

        return chain.proceed(builder.build());
    }
}
