/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import nl.yrck.comicsprout.DetailActivity;
import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.api.models.CharacterWrapper;
import nl.yrck.comicsprout.loaders.CharacterDetailLoader;

public class CharacterDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<CharacterWrapper>
{
    private static String TAG = "CHARACTER_DETAIL_FRAG";

    String detailId;

    TextView description;

    public CharacterDetailFragment() {
        // Required empty public constructor
    }


    public static CharacterDetailFragment newInstance(String detailId) {
        CharacterDetailFragment fragment = new CharacterDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.EXTRA_DETAIL_ID, detailId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            detailId = getArguments().getString(DetailActivity.EXTRA_DETAIL_ID);
        }
        getActivity().getSupportLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_character, container, false);
        description = (TextView) view.findViewById(R.id.url);

        return view;
    }

    @Override
    public Loader<CharacterWrapper> onCreateLoader(int id, Bundle args) {
        return new CharacterDetailLoader(getActivity(), args.getString(DetailActivity.EXTRA_DETAIL_ID));
    }

    @Override
    public void onLoadFinished(Loader<CharacterWrapper> loader, CharacterWrapper data) {
        if (data == null) {
            Toast.makeText(getActivity(), R.string.error_no_data, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            description.setText(Html.fromHtml(data.results.description));
        } catch (Exception e) {
            Log.e("Character Detail", "Error parsing description to html");
            description.setText(data.results.description);
        }
    }

    @Override
    public void onLoaderReset(Loader<CharacterWrapper> loader) {
        Log.d(TAG, "loader reset");
    }
}
