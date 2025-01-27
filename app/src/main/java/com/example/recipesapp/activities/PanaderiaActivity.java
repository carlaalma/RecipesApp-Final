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

public class PanaderiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);


        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(
                "Pan Rústico",
                "Pan artesanal con corteza crujiente y miga suave.",
                "",
                "Harina, levadura, sal, agua",
                "1. Mezcla los ingredientes y amasa. \n2. Deja fermentar. \n3. Hornea hasta que tenga una corteza crujiente.",
                R.drawable.pan_rustico
        ));

        recetas.add(new Receta(
                "Croissants",
                "Deliciosos croissants hojaldrados con mantequilla.",
                "",
                "Harina, mantequilla, azúcar, levadura, leche",
                "1. Prepara la masa y añade capas de mantequilla. \n2. Dobla y refrigera varias veces. \n3. Hornea hasta que estén dorados.",
                R.drawable.croissants
        ));

        recetas.add(new Receta(
                "Focaccia Italiana",
                "Pan plano italiano con romero y aceite de oliva.",
                "",
                "Harina, agua, levadura, aceite de oliva, romero, sal",
                "1. Prepara la masa y deja fermentar. \n2. Añade aceite de oliva y romero. \n3. Hornea hasta que esté dorada.",
                R.drawable.focaccia
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
            Intent intent = new Intent(PanaderiaActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de panadería. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}