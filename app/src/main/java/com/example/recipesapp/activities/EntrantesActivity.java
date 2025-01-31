package com.example.recipesapp.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesapp.R;
import com.example.recipesapp.RecetaAdapter;
import com.example.recipesapp.database.DatabaseManager;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EntrantesActivity extends AppCompatActivity {

    private ArrayList<Receta> recetas; // Lista de recetas

    // Método para cargar recetas desde la base de datos
    private void cargarRecetas() {
        DatabaseManager dbManager = new DatabaseManager(this);
        SQLiteDatabase db = dbManager.getReadableDatabase();

        // Inicializar la lista de recetas
        recetas = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseManager.TABLE_RECETAS,
                null,
                "LOWER(" + DatabaseManager.COLUMN_TIPO_RECETA + ") = ?",
                new String[]{"entrante"}, // Forzar minúsculas para evitar problemas
                null, null, null
        );

        // Procesar los resultados del cursor
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    Receta receta = new Receta(
                            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_TITULO)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_DESCRIPCION)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_IMAGEN)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_INGREDIENTES)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_PASOS)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_TIPO_RECETA))
                    );
                    Log.d("EntrantesActivity", "Receta cargada: " + receta.getTitulo() + ", Imagen: " + receta.getImagen());
                    recetas.add(receta);
                } catch (Exception e) {
                    Log.e("EntrantesActivity", "Error al procesar receta: " + e.getMessage());
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        else {
            Log.w("EntrantesActivity", "No se encontraron recetas para el tipo 'Entrante'.");
        }
        db.close();

        Log.d("EntrantesActivity", "Número de recetas cargadas: " + recetas.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrantes);

        // Cargar las recetas desde la base de datos
        cargarRecetas();
        // Verificar que la lista no sea null
        if (recetas == null) {
            recetas = new ArrayList<>();
            Log.d("EntrantesActivity", "La lista de recetas estaba null. Se inicializó.");
        }


        // Configurar el adaptador para el ListView
        ListView listView = findViewById(R.id.list_recetas);
        RecetaAdapter adapter = new RecetaAdapter(this, recetas);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        // Configurar la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_back) {
                onBackPressed();
                return true;
            } else if (item.getItemId() == R.id.action_about) {
                showAboutDialog();
                return true;
            }
            return false;
        });

        // Configurar el clic en los elementos del ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Receta selectedReceta = (Receta) parent.getItemAtPosition(position); // Obtener la receta seleccionada
            Intent intent = new Intent(EntrantesActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", selectedReceta); // Pasar la receta seleccionada
            startActivity(intent);
        });
    }

    // Muestra un diálogo "Acerca de"
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra recetas de entrantes. Haz clic en una receta para más detalles.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
