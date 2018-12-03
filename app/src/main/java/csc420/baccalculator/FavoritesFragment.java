package csc420.baccalculator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import csc420.baccalculator.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: Get current user
        User user = null;
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = getView().findViewById(R.id.drinks_grid);
        gridView.setAdapter(new DrinkImageAdapter(this.getContext(), user));
    }
}
