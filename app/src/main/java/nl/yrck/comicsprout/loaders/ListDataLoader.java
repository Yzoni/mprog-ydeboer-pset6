package nl.yrck.comicsprout.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import nl.yrck.comicsprout.models.SavedItem;

public class ListDataLoader extends AsyncTaskLoader {

    public ListDataLoader(Context context) {
        super(context);
    }

    @Override
    public List<SavedItem> loadInBackground() {
        return null;
    }
}
