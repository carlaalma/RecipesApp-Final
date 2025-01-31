package com.example.recipesapp.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipesapp.R;
import com.example.recipesapp.database.DatabaseManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;


public class AceleracionActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private TextView txtAceleracionActual, txtAceleracionMedia, txtTiempoMovimiento, txtTimestamp;
    private Button btnReiniciar;
    private Button btnEstadisticas;
    private boolean enMovimiento = false;
    private long tiempoInicio = 0;
    private long tiempoMovimiento = 0;
    private float sumaAceleracion = 0;
    private int contadorLecturas = 0;
    private static final float UMBRAL_MOVIMIENTO = 0.5f; // Umbral mínimo de aceleración
    private DatabaseManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceleracion);

        // Inicializar los elementos de la UI
        txtAceleracionActual = findViewById(R.id.txt_aceleracion_actual);
        txtAceleracionMedia = findViewById(R.id.txt_aceleracion_media);
        txtTiempoMovimiento = findViewById(R.id.txt_tiempo_movimiento);
        txtTimestamp = findViewById(R.id.txt_timestamp);
        btnReiniciar = findViewById(R.id.btn_reiniciar);
        btnEstadisticas=findViewById(R.id.btn_estadisticas);
        // Inicializar SensorManager y el acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Inicializar Base de Datos
        dbManager = new DatabaseManager(this);

        // Registrar el listener del sensor
        if (acelerometro != null) {
            sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            txtAceleracionActual.setText("Sensor no disponible");
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_back) {
                onBackPressed();
                return true;
            } else if (item.getItemId() == R.id.action_about) {
                showAboutDialog();
                return true;
            }else if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(AceleracionActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Botón para reiniciar la medición
        btnReiniciar.setOnClickListener(v -> reiniciarMedicion());
        aplicarPreferencias();

        btnEstadisticas.setOnClickListener(v -> {
            Intent intent = new Intent(AceleracionActivity.this, EstadisticasActivity.class);
            startActivity(intent);
        });


    }
    private void aplicarPreferencias() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String textSize = prefs.getString("pref_text_size", "16sp");
        String textColor = prefs.getString("pref_text_color", "#000000");

        txtAceleracionActual.setTextSize(Float.parseFloat(textSize.replace("sp", "")));
        txtAceleracionMedia.setTextSize(Float.parseFloat(textSize.replace("sp", "")));
        txtTiempoMovimiento.setTextSize(Float.parseFloat(textSize.replace("sp", "")));
        txtTimestamp.setTextSize(Float.parseFloat(textSize.replace("sp", "")));

        txtAceleracionActual.setTextColor(android.graphics.Color.parseColor(textColor));
        txtAceleracionMedia.setTextColor(android.graphics.Color.parseColor(textColor));
        txtTiempoMovimiento.setTextColor(android.graphics.Color.parseColor(textColor));
        txtTimestamp.setTextColor(android.graphics.Color.parseColor(textColor));

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float ax = event.values[0];
            float ay = event.values[1];
            float az = event.values[2];

            // Calcular la magnitud de la aceleración
            float aceleracionTotal = (float) Math.sqrt(ax * ax + ay * ay + az * az);
            txtAceleracionActual.setText(String.format(Locale.US, "Aceleración Actual: %.2f m/s²", aceleracionTotal));

            if (aceleracionTotal > UMBRAL_MOVIMIENTO) {
                if (!enMovimiento) {
                    // Iniciar la medición
                    enMovimiento = true;
                    tiempoInicio = System.currentTimeMillis();
                    txtTimestamp.setText("Inicio del Movimiento: " + obtenerTimestamp());
                    Log.d("AceleracionActivity", "Movimiento detectado, inicio: " + obtenerTimestamp());
                }
                // Acumular datos
                sumaAceleracion += aceleracionTotal;
                contadorLecturas++;
            } else if (enMovimiento) {
                // Se ha detenido
                enMovimiento = false;
                tiempoMovimiento = System.currentTimeMillis() - tiempoInicio;
                float aceleracionMedia = (contadorLecturas > 0) ? (sumaAceleracion / contadorLecturas) : 0;

                // Mostrar datos en pantalla
                txtAceleracionMedia.setText(String.format(Locale.US, "Aceleración Media: %.2f m/s²", aceleracionMedia));
                txtTiempoMovimiento.setText("Tiempo en Movimiento: " + tiempoMovimiento + " ms");

                // Guardar en la base de datos
                dbManager.insertarAceleracion(tiempoInicio, tiempoMovimiento, aceleracionMedia);

                Log.d("AceleracionActivity", "Movimiento finalizado. Tiempo: " + tiempoMovimiento + " ms, Aceleración Media: " + aceleracionMedia);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementar
    }

    private void reiniciarMedicion() {
        enMovimiento = false;
        tiempoInicio = 0;
        tiempoMovimiento = 0;
        sumaAceleracion = 0;
        contadorLecturas = 0;

        txtAceleracionActual.setText("Aceleración Actual: 0.00 m/s²");
        txtAceleracionMedia.setText("Aceleración Media: 0.00 m/s²");
        txtTiempoMovimiento.setText("Tiempo en Movimiento: 0 ms");
        txtTimestamp.setText("Inicio del Movimiento: -");

        Log.d("AceleracionActivity", "Medición reiniciada");
    }

    private String obtenerTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra los datos de aceleracion del dispositivo. Haz clic en el botón para reiniciar la medición.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }



}
