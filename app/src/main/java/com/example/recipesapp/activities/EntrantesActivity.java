package com.example.recipesapp.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesapp.R;
import com.example.recipesapp.RecetaAdapter;
import com.example.recipesapp.database.DatabaseManager;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EntrantesActivity extends AppCompatActivity {
    private ArrayList<Receta> recetas;

    private void cargarRecetas() {
        DatabaseManager dbManager = new DatabaseManager(this);
        SQLiteDatabase db = dbManager.getReadableDatabase();

        Cursor cursor = db.query(DatabaseManager.TABLE_RECETAS,
                null, // Selecciona todas las columnas
                DatabaseManager.COLUMN_TIPO_RECETA + " = ?", // Condición WHERE
                new String[]{"Entrante"}, // Filtra por tipo_receta
                null, null, null);

        recetas = new ArrayList<>(); // Inicializa la lista
        while (cursor.moveToNext()) {
            @SuppressLint("Range") Receta receta = new Receta(
                    cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITULO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_IMAGEN)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_INGREDIENTES)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_PASOS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TIPO_RECETA))
            );
            recetas.add(receta);
        }
        cursor.close();
        db.close();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);
        cargarRecetas();

        List<Receta> recetas = new ArrayList<>();

        recetas.add(new Receta(
                "Bruschetta",
                "Pan tostado con tomate y albahaca",
                "",
                "Pan, tomate, albahaca, aceite de oliva, sal",
                "1. Tuesta el pan. \n2. Añade el tomate y la albahaca. \n3. Rocía con aceite de oliva y sal.",
                R.drawable.bruschetta
        ));
        recetas.add(new Receta(
                "Ensalada César",
                "Ensalada con pollo, lechuga y parmesano",
                "",
                "Lechuga, pollo, parmesano, croutons, aderezo César",
                "1. Corta la lechuga. \n2. Cocina el pollo. \n3. Mezcla con el aderezo y los demás ingredientes.",
                R.drawable.ensalada_cesar
        ));

        recetas.add(new Receta(
                "Gazpacho",
                "Sopa fría de tomate y verduras",
                "",
                "Tomate, pimiento, pepino, ajo, pan, aceite de oliva, vinagre, sal",
                "1. Mezcla todos los ingredientes. \n2. Licua hasta obtener una textura suave. \n3. Refrigera antes de servir.",
                R.drawable.gazpacho
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
            Intent intent = new Intent(EntrantesActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });

    }



    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de entrantes. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
