package csc420.baccalculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.Drink;
import csc420.baccalculator.data.Ingredient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkDialogFragment extends DialogFragment {

    private Drink drink;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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
        ImageView imageView = getView().findViewById(R.id.dialog_drink_image);
        TextView drinkName = getView().findViewById(R.id.dialog_drink_name);
        imageView.setImageBitmap(drink.drinkImage);
        drinkName.setText(drink.drinkName);
        LinearLayout ingredientList = getView().findViewById(R.id.ingredients_list_layout);
        List<Ingredient> ingredients;
        try {
            ingredients = executor.submit(() ->
                    DatabaseManager.getInstance(getActivity().getApplicationContext())
                            .userDao().getIngredientsForDrink(drink.uid)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        for (Ingredient ingredient : ingredients) {
            TextView textView = new TextView(getContext());
            textView.setText(ingredient.name);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            ingredientList.addView(textView);
        }
        ImageButton favoriteButton = getView().findViewById(R.id.dialog_favorite_button);
        favoriteButton.setOnClickListener(v -> {
            executor.execute(this::addDrinkToFavorites);
            this.dismiss();
        });
    }

    private String saveImgToFilesystem(Bitmap bitmap) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File imgFile = new File(getActivity().getExternalFilesDir(null), fileName);
        OutputStream out = new FileOutputStream(imgFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();
        return fileName;
    }

    private void addDrinkToFavorites() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        drink.userId = sharedPref.getLong(getString(R.string.current_user_key), 0);
        try {
            drink.drinkPath = saveImgToFilesystem(drink.drinkImage);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        drink.uid = DatabaseManager.getInstance(getActivity().getApplicationContext()).userDao().insert(drink);
        for (Ingredient ingredient : drink.ingredients) {
            ingredient.drinkId = drink.uid;
        }
        DatabaseManager.getInstance(getActivity().getApplicationContext()).userDao().insertAll(drink.ingredients);
    }
}
