package csc420.baccalculator;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import csc420.baccalculator.data.CocktailDB;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CocktailDBSearchFragment extends Fragment {


    public CocktailDBSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cocktail_dbsearch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = getView().findViewById(R.id.cdb_search_bar);
        GridView gridView = getView().findViewById(R.id.search_drinks_frag);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Context context = getActivity().getApplicationContext();
                try {
                    MemoryImageAdapter adapter =
                            new MemoryImageAdapter(context, getActivity(), CocktailDB.searchByName(query, context).get());
                    gridView.invalidateViews();
                    gridView.setAdapter(adapter);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // do nothing
                return false;
            }
        });
    }
}
