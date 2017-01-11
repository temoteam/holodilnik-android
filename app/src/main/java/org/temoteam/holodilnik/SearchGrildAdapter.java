package org.temoteam.holodilnik;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchGrildAdapter extends BaseAdapter {

    private Activity activity;
    Context mContext;

    private ArrayList<String> ids;
    private ArrayList<String> titles;

    public SearchGrildAdapter(Activity activity) {
        this.activity = activity;
        mContext = activity;
    }

    public void init(ArrayList<String> titles, ArrayList<String> ids){
        this.titles = titles;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.item_grid_search, parent, false);
            ImageView imageView = (ImageView) grid.findViewById(R.id.logo);
            TextView textView = (TextView) grid.findViewById(R.id.title);
            textView.setText(titles.get(position));
            Log.i("Setting up",position+":"+titles.get(position));
            Loader.loadIMG(ids.get(position),titles.get(position),imageView,activity);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}