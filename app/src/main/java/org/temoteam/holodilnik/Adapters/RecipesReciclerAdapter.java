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

public class RecipesReciclerAdapter extends RecyclerView.Adapter<RecipesReciclerAdapter.ViewHolder> {



    private Activity activity;

    private ArrayList<String> ids;
    private ArrayList<String> titles;
    private ArrayList<String> likes;
    private ArrayList<String> time;


    public RecipesReciclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public void init(ArrayList<String> titles,ArrayList<String> ids,ArrayList<String> likes,ArrayList<String> time){
        this.titles = titles;
        this.ids = ids;
        this.likes = likes;
        this.time = time;
    }

    @Override
    public RecipesReciclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recicler_recipe, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        class MyOnClicler implements View.OnClickListener,View.OnLongClickListener{

            String id;
            CardView cw;

            public MyOnClicler (String id,String text,CardView cw){
                this.id = id;
                this.cw = cw;
            }


            @Override
            public void onClick(View v) {
                RecipeActivity.updata(id);
                activity.startActivity(new Intent(activity,RecipeActivity.class));
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }

        holder.title.setText(titles.get(position));
        holder.time.setText(time.get(position));
        holder.likes.setText(likes.get(position));
        holder.cardView.setOnClickListener(new MyOnClicler(ids.get(position),titles.get(position),holder.cardView));
        Picasso.with(activity).load("http://shvedcom.esy.es/hlad/photo/recipe_"+ids.get(position)+".png").error(R.drawable.loading).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView likes;
        TextView time;
        ImageView pic;
        CardView cardView;


        ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            likes = (TextView) v.findViewById(R.id.likes);
            time = (TextView) v.findViewById(R.id.time);
            pic=(ImageView) v.findViewById(R.id.logo);
            cardView = (CardView) v.findViewById(R.id.cardView);
        }
    }


}
