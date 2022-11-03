package uz.gita.quizzappcountries;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Data {
    private ArrayList<ModelCountry> countries = new ArrayList<>();
    private int MAX_SIZE = 41;
    private static Data data;
    private static Context context;
    private Data() {
        loadData();
    }
    public static Data getInstance(Context temp) {
        if(data == null) {
            context = temp;
            data = new Data();
        }
        return data;
    }

    public int getName(int id) {
        return countries.get(id).name;
    }

    public int getImage(int id) {
        return countries.get(id).image;
    }

    private void loadData() {
        int idName, idImage;
        String temp;
        for(int i = 0; i < MAX_SIZE; ++ i) {
            temp = "c" + i;
            if(i < 10)
                temp = ("c" + 0) + i;
            idName = context.getResources().getIdentifier(temp, "string", context.getPackageName());
            temp = "ic_" + i;
            if(i < 10)
                temp = ("ic_" + 0) + i;
            Log.d("TTT", temp);
            idImage = context.getResources().getIdentifier(temp, "drawable", context.getPackageName());
            Log.d("TTT", String.valueOf(idImage));
            countries.add(new ModelCountry(idName, idImage));
        }
    }

    public ArrayList<ModelCountry> getCountries() {
        return countries;
    }
}
