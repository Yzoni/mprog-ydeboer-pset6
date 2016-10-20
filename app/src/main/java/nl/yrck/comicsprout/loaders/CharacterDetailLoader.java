package nl.yrck.comicsprout.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import nl.yrck.comicsprout.BaseActivity;
import nl.yrck.comicsprout.api.models.CharacterWrapper;
import retrofit2.Call;

public class CharacterDetailLoader extends AsyncTaskLoader<CharacterWrapper> {

    private String characterId;

    public CharacterDetailLoader(Context context, String characterId) {
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
    public CharacterWrapper loadInBackground() {
        try {
            System.out.println("Doing id lookup with: " + characterId);
            Call<CharacterWrapper> call = BaseActivity.getComicVine().characters().get("4005-" + characterId);
            return call.execute().body();
        } catch (IOException e) {
            System.out.println("Call failed " + characterId);
        }
        return null;
    }

    @Override
    public void deliverResult(CharacterWrapper characterWrapper) {
        super.deliverResult(characterWrapper);
    }
}