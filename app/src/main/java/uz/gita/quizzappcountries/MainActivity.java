package uz.gita.quizzappcountries;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ModelCountry> list;
    private int position = 0;
    private int MAX_SIZE = 10;
    private int chosen = -1;
    private int ans = 0;
    private RadioButton[] variant = new RadioButton[4];
    private ArrayList<Integer> variants = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = Data.getInstance(this);
        list = data.getCountries();

        goShuffle();

        variant[0] = findViewById(R.id.vA);
        variant[1] = findViewById(R.id.vB);
        variant[2] = findViewById(R.id.vC);
        variant[3] = findViewById(R.id.vD);

        for(int i = 0; i < 4; ++ i) {
            variant[i].setTag(i);
            variant[i].setOnClickListener(view -> goCheck((Integer) view.getTag()));
        }

        findViewById(R.id.next).setEnabled(false);
        findViewById(R.id.next).setOnClickListener(view -> goNext());

        goLoadData();

    }

    public void goLoadData() {
        ImageView image = findViewById(R.id.image);
        image.setBackground(getResources().getDrawable(list.get(position).image));

        variants.clear();

        variants.add(list.get(position).name);

        Random num = new Random();
        int temp;

        while(variants.size() < 4) {
            temp = num.nextInt(list.size());
            if(!variants.contains(list.get(temp).name))
                variants.add(list.get(temp).name);
        }

        Collections.shuffle(variants);

        for(int i = 0; i < 4; ++ i) {
            variant[i].setText(getResources().getString(variants.get(i)));
            variant[i].setChecked(false);
        }
    }

    public void goShuffle() {
        for(int i = 0; i < 5; ++ i)
            Collections.shuffle(list);
    }

    public void goCheck(int index) {
        if(chosen != -1) {
            variant[chosen].setChecked(false);
        }
        variant[index].setChecked(true);
        chosen = index;
        findViewById(R.id.next).setEnabled(true);
    }

    public void goCalculate() {
        if(getResources().getString(list.get(position).name).equals(variant[chosen].getText()))
            ans ++;
    }

    public void goNext() {

        goCalculate();

        findViewById(R.id.next).setEnabled(false);

        if(position ++ == MAX_SIZE) {
            Intent intent = new Intent(this, Entrance.class);
            Dialog dialog = new AlertDialog.Builder(this).setCancelable(false).setTitle("Your score").setMessage("You answered correctly to " + ans + " question(s) out of " + MAX_SIZE).setPositiveButton("OK", (dia, which) -> {
                dia.dismiss();
                startActivity(intent);
                finish();
            }).show();
        } else
            goLoadData();

        chosen = -1;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Entrance.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}