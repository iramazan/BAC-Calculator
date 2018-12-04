package csc420.baccalculator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import csc420.baccalculator.data.CocktailDB;
import csc420.baccalculator.data.Drink;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private String paramArg;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("param", param);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            paramArg = bundle.getString("param", null);
        }
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Drink> drinks;
        try {
            drinks = CocktailDB.searchByName(paramArg, getActivity().getApplicationContext()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        GridView gridView = getView().findViewById(R.id.search_grid);
        MemoryImageAdapter adapter = new MemoryImageAdapter(this.getContext(), drinks);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, v, pos, id) -> {
            Drink drink = (Drink) adapter.getItem(pos);
            DrinkDialogFragment.newInstance(drink).show(getActivity().getSupportFragmentManager(), null);
        });
    }
}
