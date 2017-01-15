package org.temoteam.holodilnik.Adapters;


import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.temoteam.holodilnik.Source.Loader;
import org.temoteam.holodilnik.R;

import java.util.ArrayList;

public class SearchRecrclerAdapter extends RecyclerView.Adapter<SearchRecrclerAdapter.ViewHolder> {

    public static String reqId = "";
    private static String reqText = "";
    private static TextView selectedText = null;

    public static void setSelectedText(TextView selectedText) {
        SearchRecrclerAdapter.selectedText = selectedText;
    }

    public static void clear() {
        reqId = "";
        reqText = "";
        selectedText.setText(reqText);
    }

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

        class MyOnClicler implements View.OnClickListener,View.OnLongClickListener{

            String id;
            String text;
            CardView cw;

            public MyOnClicler (String id,String text,CardView cw){
                this.id = id;
                this.text = text;
                this.cw = cw;
                init();
            }

            void init(){
                if (reqId.contains(id+","))
                    cw.setBackgroundColor(Color.parseColor("#E9FDCF"));
                else
                    cw.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (reqText.length()>0)
                selectedText.setText(reqText.substring(0,reqText.length()-2));
            }

            @Override
            public void onClick(View v) {
                if (!reqId.contains(id+",")){
                    reqId=reqId+id+",";
                    reqText=reqText+text+", ";
                }
                else {
                    reqId=reqId.replaceAll(id+",","");
                    reqText=reqText.replaceAll(text+", ","");
                }
                init();
            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }

        holder.title.setText(titles.get(position));
        holder.cardView.setOnClickListener(new MyOnClicler(ids.get(position),titles.get(position),holder.cardView));
        Loader.loadIMG(ids.get(position),titles.get(position),holder.pic,activity);
        //

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
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
