package csc420.baccalculator.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
            final JsonElement root = new JsonParser().parse(jsonReader);
            final JsonArray drinksArray = root.getAsJsonObject().getAsJsonArray("drinks");
            for (JsonElement jsonElem : drinksArray) {
                List<Ingredient> ingredients = new ArrayList<>();
                JsonObject obj = jsonElem.getAsJsonObject();
                for (int i = 1; i <= 15; i++) {
                    String ingredient = obj.get("strIngredient" + i).getAsString();
                    if (!ingredient.equals("")) {
                        ingredients.add(new Ingredient(ingredient));
                    }
                }
                String drinkName = obj.get("strDrink").getAsString();
                URL imgUrl = new URL(obj.get("strDrinkThumb").getAsString());
                Bitmap img = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream());
                drinks.add(new Drink(ingredients, Drink.DrinkType.MIXED, img, drinkName));
            }
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
