package com.example.recipesapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipesapp.R;
import com.example.recipesapp.RecetaAdapter;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SegundosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);


        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(
                "Dorada al Horno",
                "Pescado dorado al horno con patatas y limón.",

                "",
                "Dorada, patatas, limón, aceite de oliva, ajo, perejil, sal",
                "1. Coloca las patatas en la bandeja. \n2. Pon la dorada encima y aliña con limón, ajo y perejil. \n3. Hornea hasta que esté dorada.",
                R.drawable.dorada_horno
        ));

        recetas.add(new Receta(
                "Filete de Ternera con Salsa de Champiñones",
                "Jugosa ternera servida con una cremosa salsa de champiñones.",

                "",
                "Filetes de ternera, champiñones, nata, cebolla, ajo, mantequilla, sal, pimienta",
                "1. Cocina los filetes. \n2. Prepara la salsa con champiñones, nata y mantequilla. \n3. Sirve la carne con la salsa encima.",
                R.drawable.filete_ternera
        ));

        recetas.add(new Receta(
                "Tortilla Española",
                "Tortilla de patatas clásica con huevo y cebolla.",

                "",
                "Patatas, huevos, cebolla, aceite de oliva, sal",
                "1. Fríe las patatas con la cebolla. \n2. Mezcla con los huevos batidos. \n3. Cocina en una sartén hasta que cuaje.",
                R.drawable.tortilla
        ));


        ListView listView = findViewById(R.id.list_recetas);
        RecetaAdapter adapter = new RecetaAdapter(this, recetas);
        listView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_back) {
                // Volver a la actividad anterior
                onBackPressed();
                return true;
            } else if (item.getItemId() == R.id.action_about) {
                // Mostrar un diálogo de "Acerca de"
                showAboutDialog();
                return true;
            }
            return false;
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Receta selectedReceta = (Receta) parent.getItemAtPosition(position); // Obtener la receta seleccionada
            Intent intent = new Intent(SegundosActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de segundos platos. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}