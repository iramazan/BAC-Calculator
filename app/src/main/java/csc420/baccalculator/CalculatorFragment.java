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
import android.widget.Button;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.Drink;
import csc420.baccalculator.data.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment implements SelectFavoriteFragment.OnDrinkSelectionListener {

    public CalculatorFragment() {
        // Required empty public constructor
    }

    Drink selectedDrink;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button submitButton = getView().findViewById(R.id.submit_drink_button);
        submitButton.setOnClickListener(v -> this.calculateBAC());
    }

    private void calculateBAC() {
        executor.execute(() -> {
            SharedPreferences sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            double alcoholConsumed = Double.parseDouble(sharedPref.getString(getString(R.string.alcohol_consumed_key), "0"));
            Drink drink = selectedDrink;
            SharedPreferences.Editor editor = sharedPref.edit();
            double totalGrams = alcoholConsumed + drink.gramsOfAlcohol;
            editor.putString(getString(R.string.alcohol_consumed_key), Double.toString(totalGrams));
            User user = DatabaseManager.getInstance(getActivity().getApplicationContext())
                    .userDao().getUserById(sharedPref.getLong(getString(R.string.current_user_key), 0));
            double bac = (totalGrams / (user.weight) * user.getConstant()) * 100;
            editor.putString(getString(R.string.bac_key), Double.toString(bac));
        });
    }

    @Override
    public void onDrinkSelection(Drink drink) {
        this.selectedDrink = drink;
    }
}
