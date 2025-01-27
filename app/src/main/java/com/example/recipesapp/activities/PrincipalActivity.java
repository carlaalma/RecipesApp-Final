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

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);


        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(
                "Paella Valenciana",
                "Tradicional arroz español con pollo, conejo y verduras.",
                "", // URI de imagen, si la tuvieras
                "Arroz, pollo, conejo, judías verdes, tomate, pimiento, aceite de oliva, azafrán",
                "1. Sofríe el pollo y el conejo. \n2. Añade las verduras y sofríe. \n3. Incorpora el arroz, caldo y azafrán. \n4. Cocina hasta que el arroz esté listo.",
                R.drawable.paella_valenciana // Recurso local
        ));

        recetas.add(new Receta(
                "Lasaña de Carne",
                "Capas de pasta rellenas de carne, salsa de tomate y queso.",
                "",
                "Pasta para lasaña, carne picada, tomate, cebolla, ajo, queso rallado, bechamel",
                "1. Prepara el relleno de carne. \n2. Alterna capas de pasta, relleno y bechamel. \n3. Hornea hasta dorar.",
                R.drawable.lasagna
        ));

        recetas.add(new Receta(
                "Pollo al Curry",
                "Pollo cocinado con especias y una cremosa salsa de curry.",
                "",
                "Pollo, leche de coco, cebolla, ajo, curry en polvo, especias, arroz",
                "1. Sofríe el pollo con las especias. \n2. Añade la leche de coco y cocina. \n3. Sirve con arroz.",
                R.drawable.pollo_curry
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
            Intent intent = new Intent(PrincipalActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de platos principales. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}