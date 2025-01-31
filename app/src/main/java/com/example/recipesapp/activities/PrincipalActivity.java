package com.example.recipesapp.activities;

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
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    private ArrayList<Receta> recetas;

    private void cargarRecetas() {
        DatabaseManager dbManager = new DatabaseManager(this);
        SQLiteDatabase db = dbManager.getReadableDatabase();

        recetas = new ArrayList<>();

        Cursor cursor = db.query(
                DatabaseManager.TABLE_RECETAS,
                null,
                "LOWER(" + DatabaseManager.COLUMN_TIPO_RECETA + ") = ?",
                new String[]{"plato principal"},
                null, null, null
        );

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
                    Log.d("PrincipalActivity", "Receta cargada: " + receta.getTitulo());
                    recetas.add(receta);
                } catch (Exception e) {
                    Log.e("PrincipalActivity", "Error al procesar receta: " + e.getMessage());
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.w("PrincipalActivity", "No se encontraron recetas para 'Postres'.");
        }
        db.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        cargarRecetas();
        // Verificar que la lista no sea null
        if (recetas == null) {
            recetas = new ArrayList<>();
            Log.d("PrincipalActivity", "La lista de recetas estaba null. Se inicializó.");
        }


        ListView listView = findViewById(R.id.list_recetas);
        RecetaAdapter adapter = new RecetaAdapter(this, (ArrayList<Receta>) recetas);
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