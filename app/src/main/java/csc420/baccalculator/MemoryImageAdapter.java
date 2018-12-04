package csc420.baccalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import csc420.baccalculator.data.Drink;

import java.util.List;

public class MemoryImageAdapter extends BaseAdapter {

    private Context context;
    private FragmentActivity activity;
    private List<Drink> drinks;

    public MemoryImageAdapter(Context context, FragmentActivity activity, List<Drink> drinks) {
        this.context = context;
        this.activity = activity;
        this.drinks = drinks;
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
        imageView.setOnClickListener(v -> {
            DrinkDialogFragment dialog = DrinkDialogFragment.newInstance(drinks.get(position));
            dialog.show(activity.getSupportFragmentManager(), null);
        });
        Bitmap bitmap = drinks.get(position).drinkImage;
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

}
