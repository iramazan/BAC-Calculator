package csc420.baccalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import csc420.baccalculator.data.DatabaseManager;
import csc420.baccalculator.data.Drink;
import csc420.baccalculator.data.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DrinkImageAdapter extends BaseAdapter {

    private Context context;
    private FragmentActivity activity;
    private List<Drink> drinks;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    public DrinkImageAdapter(Context context, FragmentActivity activity, User user) {
        this.context = context;
        this.activity = activity;
        try {
            this.drinks = executor.submit(() ->
                    DatabaseManager.getInstance(context).userDao().getDrinksForUser(user.uid)).get();
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
            imageView.setLayoutParams(new ViewGroup.LayoutParams(450, 450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8,  8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap bitmap = null;
        try {
            bitmap = loadImgFromFilesystem(drinks.get(position).drinkPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
        drinks.get(position).drinkImage = bitmap;
        imageView.setOnClickListener(v -> {
            DrinkDialogFragment dialog = DrinkDialogFragment.newInstance(drinks.get(position));
            dialog.show(activity.getSupportFragmentManager(), null);
        });
        return imageView;
    }

    private Bitmap loadImgFromFilesystem(String fileName) throws IOException {
        File imgFile = new File(context.getExternalFilesDir(null), fileName);
        InputStream in = new FileInputStream(imgFile);
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        in.close();
        return bitmap;
    }
}
