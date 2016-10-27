/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import nl.yrck.comicsprout.BaseActivity;
import nl.yrck.comicsprout.api.models.search.SearchWrapper;
import retrofit2.Call;

/**
 * Loads search results
 */
public class SearchLoader extends AsyncTaskLoader<SearchWrapper> {

    private static String TAG = "SEARCH_LOADER";

    String searchQuery;

    public SearchLoader(Context context, String searchQuery) {
        super(context);
        this.searchQuery = searchQuery;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public SearchWrapper loadInBackground() {
        try {
            Call<SearchWrapper> searchWrapperCall = BaseActivity.getComicVine().search().query(
                    this.searchQuery,
                    null,
                    null,
                    null,
                    "character,issue"
            );
            return searchWrapperCall.execute().body();
        } catch (IOException e) {
            Log.e(TAG, "Search failed");
        }
        return null;
    }

    @Override
    public void deliverResult(SearchWrapper searchWrapper) {
        super.deliverResult(searchWrapper);
    }
}