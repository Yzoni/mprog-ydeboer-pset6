package nl.yrck.comicsprout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.yrck.comicsprout.adapters.SearchResultsAdapter;
import nl.yrck.comicsprout.api.models.BasicResults;
import nl.yrck.comicsprout.api.models.search.SearchWrapper;
import nl.yrck.comicsprout.loaders.SearchLoader;

public class SearchActivity extends BaseActivity
        implements LoaderManager.LoaderCallbacks<SearchWrapper> {

    public static String TAG = "SEARCH_ACTIVITY";

    ArrayList<BasicResults> searchResults;
    SearchView searchView;
    Boolean savedInstance = false;

    private RecyclerView recycler;
    private SearchResultsAdapter adapter;
    private RecyclerView.LayoutManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get search intent
        handleIntent(getIntent());

        // Remember if activity has already been started
        if (savedInstanceState != null) {
            savedInstance = savedInstanceState.getBoolean("SAVED_INSTANCE");
        }

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);

        lm = new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);

        if (savedInstanceState == null) {
            searchResults = new ArrayList<>();
        } else {
            searchResults = (ArrayList<BasicResults>) savedInstanceState.getSerializable("data");
        }
        adapter = new SearchResultsAdapter(searchResults, getApplicationContext());
        adapter.setOnItemClickListener((position, v) -> startDetailsActivity(v));

        recycler.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Get the search query text and start loading results
     *
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            Bundle bundle = new Bundle();
            bundle.putString("SEARCH_QUERY", searchQuery);
            getSupportLoaderManager().restartLoader(0, bundle, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("SAVED_INSTANCE", true);
        outState.putSerializable("data", searchResults);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem searchMenu = menu.findItem(R.id.action_search);

        // Do not expand the searchview on for example rotate
        if (!savedInstance) {
            MenuItemCompat.expandActionView(searchMenu);
        }

        return true;
    }

    private void startDetailsActivity(View view) {
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView type = (TextView) view.findViewById(R.id.item_type);

        Bundle bundle = new Bundle();
        System.out.println(view.getTag());
        bundle.putString(DetailActivity.EXTRA_DETAIL_ID, (String) view.getTag());
        bundle.putString(DetailActivity.EXTRA_DETAIL_TITLE, name.getText().toString());
        bundle.putString(DetailActivity.EXTRA_DETAIL_TYPE, type.getText().toString());
        startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtras(bundle));
    }

    @Override
    public Loader<SearchWrapper> onCreateLoader(int id, Bundle args) {
        showProgressDialog();
        String searchQuery = args.getString("SEARCH_QUERY");
        return new SearchLoader(this, searchQuery);
    }

    @Override
    public void onLoadFinished(Loader<SearchWrapper> loader, SearchWrapper data) {

        searchResults.clear();
        if (data == null) {
            Toast.makeText(this, "No data received",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * Merge all different types of data to one list using polymorphism to go
         * back to BasicResults
         */
        if (data.results.characters != null) {
            searchResults.addAll(data.results.characters);
        }
        if (data.results.issues != null) {
            searchResults.addAll(data.results.issues);
        }
        if (data.results.episodes != null) {
            searchResults.addAll(data.results.episodes);
        }
        if (data.results.teams != null) {
            searchResults.addAll(data.results.teams);
        }

        adapter.notifyDataSetChanged();
        hideProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<SearchWrapper> loader) {
        Log.d(TAG, "search loader reset");
    }

}
