package org.temoteam.holodilnik;


import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class SearchRecrclerAdapter extends RecyclerView.Adapter<SearchRecrclerAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<String> ids;
    private ArrayList<String> titles;







    public SearchRecrclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public void init(ArrayList<String> titles,ArrayList<String> ids){
        this.titles = titles;
        this.ids = ids;


    }

    @Override
    public SearchRecrclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recicler_search, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        Loader.loadIMG(ids.get(position),titles.get(position),holder.pic,activity);
        // holder.cardView.setBackgroundColor(Color.parseColor("#CFF9B2"));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        ImageView pic;
        CardView cardView;

        ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            pic=(ImageView) v.findViewById(R.id.logo);
            cardView = (CardView) v.findViewById(R.id.cardView);
        }
    }


}
