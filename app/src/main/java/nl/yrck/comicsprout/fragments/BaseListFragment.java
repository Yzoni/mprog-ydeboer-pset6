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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import nl.yrck.comicsprout.DetailActivity;
import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.models.SavedItem;
import nl.yrck.comicsprout.viewholders.ListItemViewHolder;

public abstract class BaseListFragment extends Fragment {
    private static final String TAG = "PostListFragment";

    private DatabaseReference database;
    private FirebaseRecyclerAdapter<SavedItem, ListItemViewHolder> adapter;
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
        adapter = new FirebaseRecyclerAdapter<SavedItem, ListItemViewHolder>(SavedItem.class, R.layout.list_item,
                ListItemViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final ListItemViewHolder viewHolder, final SavedItem model, final int position) {
                viewHolder.itemView.setOnClickListener((view) -> {
                            TextView id = (TextView) view.findViewById(R.id.item_id);
                            TextView name = (TextView) view.findViewById(R.id.item_name);

                            Bundle bundle = new Bundle();
                            bundle.putString(DetailActivity.EXTRA_DETAIL_ID, id.getText().toString());
                            bundle.putString(DetailActivity.EXTRA_DETAIL_TITLE, name.getText().toString());
                            bundle.putString(DetailActivity.EXTRA_DETAIL_TYPE, getType());
                            startActivity(new Intent(getActivity(), DetailActivity.class).putExtras(bundle));
                        }
                );
                viewHolder.bindToPost(model);
            }
        };
        recycler.setAdapter(adapter);

        return rootView;
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

    public abstract String getType();

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
