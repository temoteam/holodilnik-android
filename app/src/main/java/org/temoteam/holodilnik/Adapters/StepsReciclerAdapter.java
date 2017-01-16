package org.temoteam.holodilnik.Adapters;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.temoteam.holodilnik.Main.RecipeActivity;
import org.temoteam.holodilnik.R;

import java.util.ArrayList;

public class StepsReciclerAdapter extends RecyclerView.Adapter<StepsReciclerAdapter.ViewHolder> {



    private Activity activity;

    private ArrayList<String> titles;
    private ArrayList<String> links;


    public StepsReciclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public void init(ArrayList<String> titles,ArrayList<String> links){
        this.titles = titles;
        this.links = links;
    }

    @Override
    public StepsReciclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recicler_step, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(titles.get(position));
        Picasso.with(activity).load(links.get(position)).error(R.drawable.loading).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;


        ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.step);
            pic=(ImageView) v.findViewById(R.id.logo);
        }
    }


}
