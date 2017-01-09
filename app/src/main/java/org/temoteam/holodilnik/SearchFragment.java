package org.temoteam.holodilnik;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class SearchFragment extends Fragment implements TextWatcher {

    TextView selected;
    EditText search;
    RecyclerView recyclerView;
    Loader loader;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_search, container, false);
        search = (EditText)result.findViewById(R.id.editText_search);
        selected = (TextView) result.findViewById(R.id.textView_selected);
        recyclerView = (RecyclerView) result.findViewById(R.id.recicler) ;
        search.addTextChangedListener(this);
        loader = new Loader(recyclerView,getActivity());
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
