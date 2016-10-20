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
import nl.yrck.comicsprout.api.models.IssueWrapper;
import nl.yrck.comicsprout.loaders.CharacterDetailLoader;
import nl.yrck.comicsprout.loaders.IssueDetailLoader;

public class IssueDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<IssueWrapper>
{

    String detailId;

    TextView description;

    public IssueDetailFragment() {
        // Required empty public constructor
    }


    public static IssueDetailFragment newInstance(String detailId) {
        IssueDetailFragment fragment = new IssueDetailFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_character, container, false);

        description = (TextView) view.findViewById(R.id.url);

        return view;
    }

    @Override
    public Loader<IssueWrapper> onCreateLoader(int id, Bundle args) {
        return new IssueDetailLoader(getActivity(), args.getString(DetailActivity.EXTRA_DETAIL_ID));
    }

    @Override
    public void onLoadFinished(Loader<IssueWrapper> loader, IssueWrapper data) {
        if (data == null) {
            Toast.makeText(getActivity(), "No data received",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            description.setText(Html.fromHtml(data.results.description));
        } catch (Exception e) {
            System.err.println("Error parsing description to html");
            description.setText(data.results.description);
        }
    }

    @Override
    public void onLoaderReset(Loader<IssueWrapper> loader) {
        Log.d("reset", "loader reset");
    }
}
