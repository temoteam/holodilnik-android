package org.temoteam.holodilnik.Main;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.temoteam.holodilnik.Adapters.SearchRecrclerAdapter;
import org.temoteam.holodilnik.Source.Loader;
import org.temoteam.holodilnik.R;

public class SearchFragment extends Fragment implements TextWatcher {

    TextView selected;
    EditText search;
    RecyclerView recyclerView;
    Loader loader;

    Animation animation;
    FragmentManager fm;
    RecipesFragment recipesFragment;

    public SearchFragment() {

    }

    public void onBackPressed(){
        if (recipesFragment!=null)
            if(recipesFragment.isVisible()){
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.content_main, this).commit();
            }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_search, container, false);
        search = (EditText) result.findViewById(R.id.editText_search);
        selected = (TextView) result.findViewById(R.id.textView_selected);
        recyclerView = (RecyclerView) result.findViewById(R.id.recicler);
        search.addTextChangedListener(this);
        loader = new Loader(recyclerView, getActivity());
        SearchRecrclerAdapter.setSelectedText((TextView) result.findViewById(R.id.selected));
        ImageView cancel = (ImageView) result.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchRecrclerAdapter.clear();
                v.startAnimation(animation);
            }
        });
        ImageView check = (ImageView) result.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fm.beginTransaction();

                if (recipesFragment == null) recipesFragment = new RecipesFragment();
                recipesFragment.dataUpdate(SearchRecrclerAdapter.reqId,"id","likes","true");
                transaction.replace(R.id.content_main, recipesFragment).commit();

            }
        });
        return result;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        loader.load(s.toString());
    }
}
