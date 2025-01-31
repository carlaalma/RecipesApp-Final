package com.example.recipesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recipesapp.R;
import com.example.recipesapp.models.Receta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recetas.db";
    private static final int DATABASE_VERSION = 8;

    public static final String TABLE_RECETAS = "recetas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_INGREDIENTES = "ingredientes";
    public static final String COLUMN_PASOS = "pasos";
    public static final String COLUMN_TIPO_RECETA = "tipo_receta";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_RECETAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_IMAGEN + " INTEGER, " +
                COLUMN_INGREDIENTES + " TEXT, " +
                COLUMN_PASOS + " TEXT, " +
                COLUMN_TIPO_RECETA + " TEXT)";
        String createTableAceleracion = "CREATE TABLE IF NOT EXISTS aceleraciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestamp TEXT, " +
                "tiempo_movimiento INTEGER, " +
                "aceleracion_media REAL)";
        db.execSQL(createTableQuery);
        db.execSQL(createTableAceleracion);

        // Insertar recetas predeterminadas
        insertarRecetaInicial(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETAS);
        onCreate(db);
    }

    private void insertarRecetaInicial(SQLiteDatabase db) {
        insertarReceta(db,new Receta("Bruschetta", "Pan tostado con tomate y albahaca",
                R.drawable.bruschetta,
                "Pan, tomate, albahaca, aceite de oliva, sal",
                "1. Tuesta el pan.\n2. Añade el tomate y la albahaca.\n3. Rocía con aceite de oliva y sal.",
                "Entrante"));

        insertarReceta(db,new Receta("Ensalada César", "Ensalada con pollo, lechuga y parmesano",
                R.drawable.ensalada_cesar,
                "Pollo, lechuga, queso parmesano, crutones, aderezo César",
                "1. Cocina el pollo.\n2. Mezcla los ingredientes.\n3. Añade el aderezo.",
                "Entrante"));

        insertarReceta(db,new Receta("Gazpacho", "Sopa fría de tomate y verduras",
                R.drawable.gazpacho,
                "Tomates, pimientos, pepino, ajo, aceite de oliva, vinagre, sal",
                "1. Licúa todos los ingredientes.\n2. Refrigera antes de servir.",
                "Entrante"));

        insertarReceta(db,new Receta("Paella Valenciana", "Tradicional arroz español con pollo, conejo y verduras.",
                R.drawable.paella_valenciana,
                "Arroz, pollo, conejo, judías verdes, tomate, pimiento, aceite de oliva, azafrán",
                "1. Sofríe el pollo y el conejo. \n2. Añade las verduras y sofríe. \n3. Incorpora el arroz, caldo y azafrán. \n4. Cocina hasta que el arroz esté listo.",
                "Plato Principal"));

        insertarReceta(db,new Receta("Lasaña de Carne", "Capas de pasta rellenas de carne, salsa de tomate y queso.",
                R.drawable.lasagna,
                "Pasta para lasaña, carne picada, tomate, cebolla, ajo, queso rallado, bechamel",
                "1. Prepara el relleno de carne. \n2. Alterna capas de pasta, relleno y bechamel. \n3. Hornea hasta dorar.",
                "Plato Principal"));

        insertarReceta(db,new Receta("Pollo al Curry", "Pollo cocinado con especias y una cremosa salsa de curry.",
                R.drawable.pollo_curry,
                "Pollo, leche de coco, cebolla, ajo, curry en polvo, especias, arroz",
                "1. Sofríe el pollo con las especias. \n2. Añade la leche de coco y cocina. \n3. Sirve con arroz.",
                "Plato Principal"));

        insertarReceta(db,new Receta("Brownies de Chocolate", "Pastelitos densos y ricos en chocolate.",
                R.drawable.brownies,
                "Chocolate, mantequilla, azúcar, huevos, harina",
                "1. Derrite el chocolate con la mantequilla. \n2. Mezcla con los demás ingredientes. \n3. Hornea hasta que estén hechos.",
                "Repostería"));

        insertarReceta(db,new Receta("Magdalenas de Limón", "Esponjosas magdalenas con un toque cítrico.",
                R.drawable.magdalenas,
                "Harina, azúcar, huevos, mantequilla, limón",
                "1. Prepara la masa con los ingredientes. \n2. Viértela en moldes. \n3. Hornea hasta que estén doradas.",
                "Repostería"));

        insertarReceta(db,new Receta("Galletas con Chispas de Chocolate", "Galletas dulces con trozos de chocolate.",
                R.drawable.galletas_chocolate,
                "Harina, azúcar, mantequilla, huevos, chispas de chocolate",
                "1. Mezcla los ingredientes. \n2. Forma las galletas y añade las chispas. \n3. Hornea hasta que estén doradas.",
                "Repostería"));

        insertarReceta(db,new Receta("Dorada al Horno", "Pescado dorado al horno con patatas y limón.",
                R.drawable.dorada_horno,
                "Dorada, patatas, limón, aceite de oliva, ajo, perejil, sal",
                "1. Coloca las patatas en la bandeja. \n2. Pon la dorada encima y aliña con limón, ajo y perejil. \n3. Hornea hasta que esté dorada.",
                "Plato Segundo"));

        insertarReceta(db,new Receta("Filete con Salsa de Champiñones", "Jugosa ternera servida con una cremosa salsa de champiñones.",
                R.drawable.filete_ternera,
                "Filetes de ternera, champiñones, nata, cebolla, ajo, mantequilla, sal, pimienta",
                "1. Cocina los filetes. \n2. Prepara la salsa con champiñones, nata y mantequilla. \n3. Sirve la carne con la salsa encima.",
                "Plato Segundo"));

        insertarReceta(db,new Receta("Tortilla Española", "Tortilla de patatas clásica con huevo y cebolla.",
                R.drawable.tortilla,
                "Patatas, huevos, cebolla, aceite de oliva, sal",
                "1. Fríe las patatas con la cebolla. \n2. Mezcla con los huevos batidos. \n3. Cocina en una sartén hasta que cuaje.",
                "Plato Segundo"));

        insertarReceta(db,new Receta("Pan Rústico", "Pan artesanal con corteza crujiente y miga suave.",
                R.drawable.pan_rustico,
                "Harina, levadura, sal, agua",
                "1. Mezcla los ingredientes y amasa. \n2. Deja fermentar. \n3. Hornea hasta que tenga una corteza crujiente.",
                "Panadería"));

        insertarReceta(db,new Receta("Croissants", "Deliciosos croissants hojaldrados con mantequilla.",
                R.drawable.croissants,
                "Harina, mantequilla, azúcar, levadura, leche",
                "1. Prepara la masa y añade capas de mantequilla. \n2. Dobla y refrigera varias veces. \n3. Hornea hasta que estén dorados.",
                "Panadería"));

        insertarReceta(db,new Receta("Focaccia Italiana", "Pan plano italiano con romero y aceite de oliva.",
                R.drawable.focaccia,
                "Harina, agua, levadura, aceite de oliva, romero, sal",
                "1. Prepara la masa y deja fermentar. \n2. Añade aceite de oliva y romero. \n3. Hornea hasta que esté dorada.",
                "Panadería"));

        insertarReceta(db,new Receta("Tarta de Queso", "Cremosa tarta de queso al horno con base de galletas.",
                R.drawable.tarta_queso,
                "Queso crema, galletas, mantequilla, huevos, azúcar, vainilla",
                "1. Mezcla las galletas con mantequilla para la base. \n2. Prepara la mezcla de queso y huevos. \n3. Hornea hasta que esté firme.",
                "Postres"));

        insertarReceta(db,new Receta("Flan de Huevo", "Postre suave de huevo y caramelo.",
                R.drawable.flan_huevo,
                "Huevos, leche, azúcar, esencia de vainilla",
                "1. Prepara el caramelo y ponlo en moldes. \n2. Mezcla los demás ingredientes y vierte en los moldes. \n3. Hornea al baño maría.",
                "Postres"));

        insertarReceta(db,new Receta("Helado de Chocolate Casero", "Cremoso helado hecho en casa con chocolate puro.",
                R.drawable.helado_chocolate,
                "Chocolate puro, nata, leche, azúcar, yemas de huevo",
                "1. Prepara una crema con chocolate, leche y yemas. \n2. Añade nata montada. \n3. Congela y remueve periódicamente.",
                "Postres"));
    }

    // Método para insertar recetas
    public void insertarReceta(SQLiteDatabase db,Receta receta) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, receta.getTitulo());
        values.put(COLUMN_DESCRIPCION, receta.getDescripcion());
        values.put(COLUMN_IMAGEN, receta.getImagen()); // Guardamos el resource ID
        values.put(COLUMN_INGREDIENTES, receta.getIngredientes());
        values.put(COLUMN_PASOS, receta.getPasos());
        values.put(COLUMN_TIPO_RECETA, receta.getTipoReceta());

        long resultado = db.insert(TABLE_RECETAS, null, values);
        if (resultado == -1) {
            Log.e("DatabaseManager", "Error al insertar receta: " + receta.getTitulo());
        } else {
            Log.d("DatabaseManager", "Receta insertada correctamente: " + receta.getTitulo());
        }

    }

    // Método para agregar una nueva receta (CRUD: Create)
    public long addReceta(Receta receta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, receta.getTitulo());
        values.put(COLUMN_DESCRIPCION, receta.getDescripcion());
        values.put(COLUMN_IMAGEN, receta.getImagen());
        values.put(COLUMN_INGREDIENTES, receta.getIngredientes());
        values.put(COLUMN_PASOS, receta.getPasos());
        values.put(COLUMN_TIPO_RECETA, receta.getTipoReceta()); // Asegurar que el tipo de receta se guarda

        long id = db.insert(TABLE_RECETAS, null, values);
        db.close();
        Log.d("DatabaseManager", "Receta añadida: " + receta.getTitulo() + " - Tipo: " + receta.getTipoReceta());
        return id;
    }


    // Método para obtener todas las recetas (CRUD: Read)
    public List<Receta> getAllRecetas() {
        List<Receta> recetas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECETAS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Receta receta = new Receta(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASOS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_RECETA))
                );
                recetas.add(receta);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return recetas;
    }

    // Método para obtener recetas por tipo (CRUD: Read)
    public List<Receta> getRecetasByTipo(String tipoReceta) {
        List<Receta> recetas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECETAS, null,
                COLUMN_TIPO_RECETA + " = ?",
                new String[]{tipoReceta}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Receta receta = new Receta(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASOS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_RECETA))
                );
                recetas.add(receta);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return recetas;
    }

    // Método para actualizar una receta (CRUD: Update)
    public int updateReceta(Receta receta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, receta.getTitulo());
        values.put(COLUMN_DESCRIPCION, receta.getDescripcion());
        values.put(COLUMN_IMAGEN, receta.getImagen());
        values.put(COLUMN_INGREDIENTES, receta.getIngredientes());
        values.put(COLUMN_PASOS, receta.getPasos());
        values.put(COLUMN_TIPO_RECETA, receta.getTipoReceta());

        int rowsAffected = db.update(TABLE_RECETAS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(receta.getId())});
        db.close();
        return rowsAffected;
    }

    // Método para eliminar una receta (CRUD: Delete)
    public int deleteReceta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_RECETAS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    public void insertarAceleracion(long tiempoInicio, long tiempoMovimiento, float aceleracionMedia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Convertir el timestamp a formato legible
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(tiempoInicio));

        values.put("timestamp", timestamp);
        values.put("tiempo_movimiento", tiempoMovimiento);
        values.put("aceleracion_media", aceleracionMedia);

        long resultado = db.insert("aceleraciones", null, values);
        db.close();

        if (resultado == -1) {
            Log.e("DatabaseManager", "Error al insertar datos de aceleración en la base de datos");
        } else {
            Log.d("DatabaseManager", "Aceleración guardada correctamente: " + timestamp);
        }
    }

}
