package com.example.recipesapp;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recipesapp.activities.EntrantesActivity;
import com.example.recipesapp.activities.MisRecetasActivity;
import com.example.recipesapp.activities.PanaderiaActivity;
import com.example.recipesapp.activities.PostresActivity;
import com.example.recipesapp.activities.PrincipalActivity;
import com.example.recipesapp.activities.ReposteriaActivity;
import com.example.recipesapp.activities.SegundosActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            Button btn_entrante = findViewById(R.id.btn_entrante);
            Button btn_principal = findViewById(R.id.btn_principal);
            Button btn_segundos = findViewById(R.id.btn_segundos);
            Button btn_postres = findViewById(R.id.btn_postres);
            Button btn_reposteria = findViewById(R.id.btn_reposteria);
            Button btn_panaderia = findViewById(R.id.btn_panadería);
            Button btn_mis_recetas = findViewById(R.id.btn_misrecetas);

            btn_entrante.setOnClickListener(v -> {
               Intent intent = new Intent(MainActivity.this, EntrantesActivity.class);
               startActivity(intent);
            });
            btn_principal.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                startActivity(intent);
            });
            btn_segundos.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SegundosActivity.class);
                startActivity(intent);
            });
            btn_postres.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PostresActivity.class);
                startActivity(intent);
            });
            btn_reposteria.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ReposteriaActivity.class);
                startActivity(intent);
            });
            btn_panaderia.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PanaderiaActivity.class);
                startActivity(intent);
            });
            btn_mis_recetas.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, MisRecetasActivity.class);
                startActivity(intent);
            });

        }
    }

