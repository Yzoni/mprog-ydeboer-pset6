package nl.yrck.comicsprout.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import nl.yrck.comicsprout.R;
import nl.yrck.comicsprout.api.models.BasicResults;

public class SearchResultsAdapter
        extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private List<BasicResults> data;
    private Context context;

    public SearchResultsAdapter(List<BasicResults> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SearchResultsAdapter.clickListener = clickListener;
    }

    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.type.setText(data.get(position).type);
        Picasso.with(context)
                .load(data.get(position).image.thumb_url)
                .placeholder(R.drawable.no_poster)
                .into(holder.image);
        String id = Integer.toString(data.get(position).id);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView name;
        public ImageView image;
        public TextView type;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
            name = (TextView) v.findViewById(R.id.item_name);
            type = (TextView) v.findViewById(R.id.item_type);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}