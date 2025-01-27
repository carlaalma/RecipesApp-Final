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

public class PostresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);

        List<Receta> recetas = new ArrayList<>();
        recetas.add(new Receta(
                "Tarta de Queso",
                "Cremosa tarta de queso al horno con base de galletas.",
                "",
                "Queso crema, galletas, mantequilla, huevos, azúcar, vainilla",
                "1. Mezcla las galletas con mantequilla para la base. \n2. Prepara la mezcla de queso y huevos. \n3. Hornea hasta que esté firme.",
                R.drawable.tarta_queso
        ));

        recetas.add(new Receta(
                "Flan de Huevo",
                "Postre suave de huevo y caramelo.",

                "",
                "Huevos, leche, azúcar, esencia de vainilla",
                "1. Prepara el caramelo y ponlo en moldes. \n2. Mezcla los demás ingredientes y vierte en los moldes. \n3. Hornea al baño maría.",
                R.drawable.flan_huevo
        ));

        recetas.add(new Receta(
                "Helado de Chocolate Casero",
                "Cremoso helado hecho en casa con chocolate puro.",

                "",
                "Chocolate puro, nata, leche, azúcar, yemas de huevo",
                "1. Prepara una crema con chocolate, leche y yemas. \n2. Añade nata montada. \n3. Congela y remueve periódicamente.",
                R.drawable.helado_chocolate
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
            Intent intent = new Intent(PostresActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de postres. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}