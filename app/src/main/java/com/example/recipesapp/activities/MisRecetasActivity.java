package com.example.recipesapp.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesapp.R;
import com.example.recipesapp.RecetaAdapter;
import com.example.recipesapp.database.DatabaseManager;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MisRecetasActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private ListView listViewMisRecetas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recetas);

        databaseManager = new DatabaseManager(this);
        listViewMisRecetas = findViewById(R.id.listView_mis_recetas);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        setupBottomNavigation(bottomNavigationView);
        FloatingActionButton fabAddReceta = findViewById(R.id.fab_add_receta);

        // Cargar recetas de la base de datos
        loadRecetas();

        // Navegar a la actividad de agregar nueva receta
        fabAddReceta.setOnClickListener(v -> {
            Intent intent = new Intent(MisRecetasActivity.this, NuevaRecetaActivity.class);
            startActivity(intent);
        });
    }
    @SuppressLint("NonConstantResourceId")
    private void setupBottomNavigation(BottomNavigationView bottomNavigationView) {
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
        listViewMisRecetas.setOnItemClickListener((parent, view, position, id) -> {
            Receta selectedReceta = (Receta) parent.getItemAtPosition(position);
            Intent intent = new Intent(MisRecetasActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta",selectedReceta);
            startActivity(intent);
        });

    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra tus recetas almacenadas. Usa el botón flotante para agregar nuevas recetas.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void loadRecetas() {
        List<Receta> recetas = databaseManager.getAllRecetas();
        RecetaAdapter adapter = new RecetaAdapter(this, recetas);
        listViewMisRecetas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecetas(); // Recargar recetas al volver a la actividad
    }
}
