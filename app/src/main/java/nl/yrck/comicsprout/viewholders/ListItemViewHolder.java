package nl.yrck.comicsprout.viewholders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.models.SavedItem;

public class ListItemViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView id;

    public ListItemViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_name);
        id = (TextView) itemView.findViewById(R.id.item_id);
    }

    public void bindToPost(SavedItem savedItem) {
        name.setText(savedItem.name);
        id.setText(savedItem.id);
    }
}
