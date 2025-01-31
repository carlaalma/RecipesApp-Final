package com.example.recipesapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipesapp.R;
import com.example.recipesapp.database.DatabaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EstadisticasActivity extends AppCompatActivity {
    private TextView txtAceleracionMedia, txtAceleracionMaxima, txtTiempoTotal;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        txtAceleracionMedia = findViewById(R.id.txt_aceleracion_media);
        txtAceleracionMaxima = findViewById(R.id.txt_aceleracion_maxima);
        txtTiempoTotal = findViewById(R.id.txt_tiempo_total);
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
        databaseManager = new DatabaseManager(this);

        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        Cursor cursor = databaseManager.obtenerDatosAceleracion();
        if (cursor != null && cursor.moveToFirst()) {
            float aceleracionMedia = 0;
            float aceleracionMaxima = 0;
            long tiempoTotal = 0;
            int count = 0;

            do {
                float aceleracion = cursor.getFloat(cursor.getColumnIndexOrThrow("aceleracion_media"));
                long tiempo = cursor.getLong(cursor.getColumnIndexOrThrow("tiempo_movimiento"));

                aceleracionMedia += aceleracion;
                tiempoTotal += tiempo;

                if (aceleracion > aceleracionMaxima) {
                    aceleracionMaxima = aceleracion;
                }
                count++;
            } while (cursor.moveToNext());

            if (count > 0) {
                aceleracionMedia /= count;
            }

            txtAceleracionMedia.setText(String.format("Aceleración Media: %.2f m/s²", aceleracionMedia));
            txtAceleracionMaxima.setText(String.format("Aceleración Máxima: %.2f m/s²", aceleracionMaxima));
            txtTiempoTotal.setText(String.format("Tiempo Total en Movimiento: %d ms", tiempoTotal));
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra estadisticas de aceleracion del dispositivo.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

}
