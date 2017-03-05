package org.temoteam.holodilnik.Main;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import org.temoteam.holodilnik.Source.Loader;
import org.temoteam.holodilnik.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    public RecipesFragment() {

    }

    RecyclerView rw;
    FloatingActionButton fab;

    String q = "";
    String type;
    String desc;
    String sort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rw = (RecyclerView) v.findViewById(R.id.recicler);
        rw.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void dataUpdate(String q, String type, String sort, String desc){
        this.type = type;
        this.q = q;
        this.desc = desc;
        this.sort = sort;

    }

    public void update(){
        if(!(q.equals("")))
        new Loader.GetRecipes(getActivity(),type,q,sort,desc,rw).execute();
    }

    class SettingsAlert extends AlertDialog{

        protected SettingsAlert(Context context) {
            super(context);

        }
    }


}
