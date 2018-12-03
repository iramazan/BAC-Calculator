package csc420.baccalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.Drink;
import csc420.baccalculator.data.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrinkImageAdapter extends BaseAdapter {

    private Context context;
    private List<Drink> drinks;

    public DrinkImageAdapter(Context context, User user) {
        this.context = context;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            this.drinks = executor.submit(() ->
                    DatabaseManager.getIntance(context).userDao().getDrinksForUser(user.uid)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return drinks.size();
    }

    @Override
    public Object getItem(int position) {
        return drinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return drinks.get(position).uid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8,  8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(drinks.get(position).drinkPath);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }
}
