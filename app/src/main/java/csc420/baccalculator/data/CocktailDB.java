package csc420.baccalculator.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class CocktailDB {

    private static final String endpoint = "https://www.thecocktaildb.com/api/json/v1/1/";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static class SearchByNameCall implements Callable<List<Drink>> {
        String param;
        Context context;

        SearchByNameCall(String param, Context context) {
            this.param = param;
            this.context = context;
        }

        String saveImgToFilesystem(String url) throws IOException {
            // TODO: Put this somewhere else
            String fileName = UUID.randomUUID().toString() + ".jpg";
            File imgFile = new File(context.getExternalFilesDir(null), fileName);
            URL imgUrl = new URL(url);
            Bitmap bitmap = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
            OutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return fileName;
        }

        @Override
        public List<Drink> call() throws Exception {
            List<Drink> drinks = new ArrayList<>();
            String url = endpoint + "search.php?s=" + param;
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            if (connection.getResponseCode() != 200) {
                return new ArrayList<>(); // Bad response code
            }
            InputStreamReader response = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            JsonReader jsonReader = new JsonReader(response);
            jsonReader.beginObject();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                List<Ingredient> ingredients = new ArrayList<>();
                String drinkName = "";
                String imgUrl = "";
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("strDrink")) {
                        drinkName = jsonReader.nextString();
                    } else if (key.equals("strDrinkThumb")) {
                        imgUrl = jsonReader.nextString();
                    } else if (key.startsWith("strIngredient")) {
                        ingredients.add(new Ingredient(jsonReader.nextString()));
                    }
                }
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(imgUrl).openConnection().getInputStream());
                drinks.add(new Drink(ingredients, Drink.DrinkType.MIXED, bitmap, drinkName));
                jsonReader.endObject();
            }
            jsonReader.endArray();
            jsonReader.endObject();
            jsonReader.close();
            connection.disconnect();
            return drinks;
        }
    }

    public static Future<List<Drink>> searchByName(String param, Context context) {
        SearchByNameCall caller = new SearchByNameCall(param, context);
        Future<List<Drink>> drinksRes = executor.submit(caller);
        return drinksRes;
    }

}
