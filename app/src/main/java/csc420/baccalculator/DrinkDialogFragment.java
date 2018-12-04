package csc420.baccalculator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import csc420.baccalculator.data.Drink;
import csc420.baccalculator.data.Ingredient;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkDialogFragment extends DialogFragment {

    private Drink drink;

    public DrinkDialogFragment() {
        // Required empty public constructor
    }

    public static DrinkDialogFragment newInstance(Drink drink) {
        DrinkDialogFragment fragment = new DrinkDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("drink", drink);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            drink = (Drink) bundle.getSerializable("drink");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView drinkName = getView().findViewById(R.id.dialog_drink_name);
        drinkName.setText(drink.drinkName);
        LinearLayout ingredientList = getView().findViewById(R.id.ingredients_list_layout);
        for (Ingredient ingredient : drink.ingredients) {
            TextView textView = new TextView(getContext());
            textView.setText(ingredient.name);
            ingredientList.addView(textView);
        }
    }
}
