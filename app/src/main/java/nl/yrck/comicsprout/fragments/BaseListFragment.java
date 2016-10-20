package nl.yrck.comicsprout.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import nl.yrck.comicsprout.DetailActivity;
import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.adapters.SavedAdapter;
import nl.yrck.comicsprout.models.SavedItem;


/**
 * This is the base list fragment. All fragments displaying saved data in a list extend from this
 * class.
 */
public abstract class BaseListFragment extends Fragment {

    private DatabaseReference database;
    private SavedAdapter adapter;
    private RecyclerView recycler;
    private LinearLayoutManager lm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        database = FirebaseDatabase.getInstance().getReference();

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);

        lm = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lm);

        Query postsQuery = getQuery(database);
        adapter = new SavedAdapter(SavedItem.class, R.layout.list_item, postsQuery);
        adapter.setOnItemClickListener((position, v) -> startDetailActivity(v));
        recycler.setAdapter(adapter);

        return rootView;
    }

    private void startDetailActivity(View v) {
        TextView id = (TextView) v.findViewById(R.id.item_id);
        TextView name = (TextView) v.findViewById(R.id.item_name);

        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.EXTRA_DETAIL_ID, id.getText().toString());
        bundle.putString(DetailActivity.EXTRA_DETAIL_TITLE, name.getText().toString());
        bundle.putString(DetailActivity.EXTRA_DETAIL_TYPE, getType());
        startActivity(new Intent(getActivity(), DetailActivity.class).putExtras(bundle));
    }

    /**
     * The query to the database
     *
     * @param databaseReference
     * @return
     */
    public abstract Query getQuery(DatabaseReference databaseReference);

    /**
     * The type of data eg character, issue, volume
     *
     * @return
     */
    public abstract String getType();

    /**
     * Get the current user from fragments
     *
     * @return
     */
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
