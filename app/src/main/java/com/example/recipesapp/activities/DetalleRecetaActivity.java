package com.example.recipesapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesapp.R;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetalleRecetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        ImageView imgReceta = findViewById(R.id.img_receta_detalle);
        TextView txtTitulo = findViewById(R.id.txt_titulo_detalle);
        TextView txtIngredientes = findViewById(R.id.txt_lista_ingredientes);
        TextView txtPasos = findViewById(R.id.txt_lista_pasos);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Obtener datos de la receta desde el Intent
        Intent intent = getIntent();
        Receta receta = (Receta) intent.getSerializableExtra("receta");

        if (receta != null) {
            // Mostrar los datos de la receta
            txtTitulo.setText(receta.getTitulo() );
            txtIngredientes.setText(receta.getIngredientes());
            txtPasos.setText(receta.getPasos());
            imgReceta.setImageResource(receta.getImagenResId());
            // Imagen por defecto
            }



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


        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
        });
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad muestra los detalles de una receta seleccionada.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
