package com.example.recipesapp.activities;


import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.recipesapp.R;
import com.example.recipesapp.database.DatabaseManager;
import com.example.recipesapp.models.Receta;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;


public class NuevaRecetaActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgReceta;
    private Uri imageUri;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);

        databaseManager = new DatabaseManager(this);

        imgReceta = findViewById(R.id.img_receta);
        Button btnSelectImage = findViewById(R.id.btn_select_image);
        EditText editTitulo = findViewById(R.id.edit_titulo);
        EditText editDescripcion = findViewById(R.id.edit_descripcion);
        EditText editIngredientes = findViewById(R.id.edit_ingredientes);
        EditText editPasos = findViewById(R.id.edit_pasos);
        Button btnGuardar = findViewById(R.id.btn_guardar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation(bottomNavigationView);

        btnSelectImage.setOnClickListener(v -> openImageSelector());

        btnGuardar.setOnClickListener(v -> {
            String titulo = editTitulo.getText().toString();
            String descripcion = editDescripcion.getText().toString();
            String ingredientes = editIngredientes.getText().toString();
            String pasos = editPasos.getText().toString();

            if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                databaseManager.addReceta(new Receta(titulo, descripcion, "", ingredientes, pasos, 0));
                Toast.makeText(this, "Receta guardada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
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
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acerca de")
                .setMessage("Esta actividad permite agregar una nueva receta. Completa los campos y guarda tu receta.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgReceta.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
