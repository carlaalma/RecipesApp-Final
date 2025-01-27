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

public class ReposteriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);


        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(
                "Brownies de Chocolate",
                "Pastelitos densos y ricos en chocolate.",
                "",
                "Chocolate, mantequilla, azúcar, huevos, harina",
                "1. Derrite el chocolate con la mantequilla. \n2. Mezcla con los demás ingredientes. \n3. Hornea hasta que estén hechos.",
                R.drawable.brownies
        ));

        recetas.add(new Receta(
                "Magdalenas de Limón",
                "Esponjosas magdalenas con un toque cítrico.",
                "",
                "Harina, azúcar, huevos, mantequilla, limón",
                "1. Prepara la masa con los ingredientes. \n2. Viértela en moldes. \n3. Hornea hasta que estén doradas.",
                R.drawable.magdalenas
        ));

        recetas.add(new Receta(
                "Galletas con Chispas de Chocolate",
                "Galletas dulces con trozos de chocolate.",
                "",
                "Harina, azúcar, mantequilla, huevos, chispas de chocolate",
                "1. Mezcla los ingredientes. \n2. Forma las galletas y añade las chispas. \n3. Hornea hasta que estén doradas.",
                R.drawable.galletas_chocolate
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
            Intent intent = new Intent(ReposteriaActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de repostería. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}