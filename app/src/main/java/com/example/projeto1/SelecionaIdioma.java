package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.util.Locale;

public class SelecionaIdioma extends AppCompatActivity {

    ImageView country_flag_br;
    ImageView country_flag_en;
    ImageView country_flag_es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_idioma);

        country_flag_br = findViewById(R.id.country_flag_br);
        country_flag_en = findViewById(R.id.country_flag_en);
        country_flag_es = findViewById(R.id.country_flag_es);

        country_flag_br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configurarLinguagem("br");
            }
        });

        country_flag_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configurarLinguagem("en");
            }
        });

        country_flag_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                configurarLinguagem("es");
            }
        });

    }

    // método que configura a linguagem selecionada
    public void configurarLinguagem(String linguagem){
        Locale minhaLocalidade = new Locale(linguagem);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = minhaLocalidade;
        resources.updateConfiguration(config, dm);

        Intent tela2 = new Intent(this, MainActivity.class);
        startActivity(tela2);
        finish();


    }

}