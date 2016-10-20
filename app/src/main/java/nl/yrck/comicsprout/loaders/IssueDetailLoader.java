package nl.yrck.comicsprout.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import nl.yrck.comicsprout.BaseActivity;
import nl.yrck.comicsprout.api.models.CharacterWrapper;
import nl.yrck.comicsprout.api.models.IssueWrapper;
import retrofit2.Call;

public class IssueDetailLoader extends AsyncTaskLoader<IssueWrapper> {

    private static String TAG = "ISSUE_DETAIL_LOADER";

    private String characterId;

    public IssueDetailLoader(Context context, String characterId) {
        super(context);
        this.characterId = characterId;
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
    public IssueWrapper loadInBackground() {
        try {
            Log.d(TAG, "Doing id lookup with: " + characterId);
            Call<IssueWrapper> call = BaseActivity.getComicVine().issues().get("4005-" + characterId);
            return call.execute().body();
        } catch (IOException e) {
           Log.d(TAG, "Call failed " + characterId);
        }
        return null;
    }

    @Override
    public void deliverResult(IssueWrapper issueWrapper) {
        super.deliverResult(issueWrapper);
    }
}