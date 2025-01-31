package com.example.recipesapp.activities;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipesapp.MainActivity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NuevaRecetaActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgReceta;
    private Uri imageUri;
    private DatabaseManager databaseManager;
    private Spinner spinnerTipoReceta;
    private String tipoRecetaSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);

        spinnerTipoReceta = findViewById(R.id.spinner_tipo_receta);
        List<String> tiposRecetas = new ArrayList<>();
        tiposRecetas.add("Seleccione un tipo de receta"); // Placeholder
        tiposRecetas.addAll(Arrays.asList(getResources().getStringArray(R.array.tipos_recetas)));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_recetas,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoReceta.setAdapter(adapter);
        spinnerTipoReceta.setSelection(0, false);
        // Escucha la selección del usuario
        spinnerTipoReceta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    tipoRecetaSeleccionado = ""; // No se ha seleccionado un tipo válido
                } else {
                    tipoRecetaSeleccionado = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoRecetaSeleccionado = ""; // Asegurar que no haya un valor predeterminado
            }
        });
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
            int imagenNombre;
            if (imageUri != null) {
                // Obtener el nombre de la imagen y mapearlo a un recurso de drawable
                String nombreImagen = imageUri.getLastPathSegment();
                imagenNombre = getResources().getIdentifier(nombreImagen, "drawable", getPackageName());

                // Si no se encuentra el recurso, usa la imagen por defecto
                if (imagenNombre == 0) {
                    imagenNombre = R.drawable.icono; // Icono por defecto si la imagen no es válida
                }
            } else {
                imagenNombre = R.drawable.icono; // Si no se seleccionó imagen, usar icono por defecto
            }

            if (!titulo.isEmpty() && !descripcion.isEmpty()) {
                Receta nuevaReceta = new Receta(titulo, descripcion, imagenNombre, ingredientes, pasos, tipoRecetaSeleccionado);
                databaseManager.addReceta(nuevaReceta);

                Toast.makeText(this, "Receta guardada correctamente", Toast.LENGTH_SHORT).show();

                // Refrescar la lista de recetas
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
