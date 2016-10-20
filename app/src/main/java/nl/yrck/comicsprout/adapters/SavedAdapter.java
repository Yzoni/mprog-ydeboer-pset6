package nl.yrck.comicsprout.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import nl.yrck.comicsprout.DetailActivity;
import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.fragments.BaseListFragment;
import nl.yrck.comicsprout.models.SavedItem;

public class SavedAdapter extends FirebaseRecyclerAdapter<SavedItem, SavedAdapter.ListItemViewHolder> {

    private static SearchResultsAdapter.ClickListener clickListener;

    public SavedAdapter(Class<SavedItem> modelClass, int modelLayout, Query ref) {
        super(modelClass, modelLayout, ListItemViewHolder.class, ref);
    }

    @Override
    protected void populateViewHolder(final SavedAdapter.ListItemViewHolder viewHolder, final SavedItem model, final int position) {
        viewHolder.bindToPost(model);
    }

    public void setOnItemClickListener(SearchResultsAdapter.ClickListener clickListener) {
        SavedAdapter.clickListener = clickListener;
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView id;

        public ListItemViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.item_name);
            id = (TextView) v.findViewById(R.id.item_id);

            v.setOnClickListener(this);

        }

        public void bindToPost(SavedItem savedItem) {
            name.setText(savedItem.name);
            id.setText(savedItem.id);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
