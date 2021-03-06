package csc420.baccalculator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesPageFragment extends Fragment {

    OnFragmentSelectionListener activityCallback;

    public FavoritesPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button searchButton = getView().findViewById(R.id.drink_search_button);
        searchButton.setOnClickListener(v -> {
            CocktailDBSearchFragment fragment = new CocktailDBSearchFragment();
            activityCallback.onFragmentSelection(fragment);
        });
    }

    public void setOnFragmentSelectionListener(OnFragmentSelectionListener activity) {
        activityCallback = activity;
    }

    public interface OnFragmentSelectionListener {
        void onFragmentSelection(Fragment fragment);
    }
}
