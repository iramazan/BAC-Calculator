package csc420.baccalculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        long userId = sharedPref.getLong(getString(R.string.current_user_key), 0);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        User user;
        try {
            user = executor.submit(() -> DatabaseManager.getInstance(getActivity().getApplicationContext())
                            .userDao().getUserById(userId)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = getView().findViewById(R.id.drinks_grid);
        gridView.setAdapter(new DrinkImageAdapter(this.getContext(), user));
    }
}
