package com.example.recipesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipesapp.models.Receta;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recetas.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECETAS = "recetas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_INGREDIENTES = "ingredientes";
    public static final String COLUMN_PASOS = "pasos";
    public static final String COLUMN_IMAGEN_RES_ID = "imagen_res_id"; // Nueva columna

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RECETAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_CATEGORIA + " TEXT, " +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_INGREDIENTES + " TEXT, " +
                COLUMN_PASOS + " TEXT, " +
                COLUMN_IMAGEN_RES_ID + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETAS);
        onCreate(db);
    }

    // CRUD Methods

    // 1. Create (Insertar una receta)
    public long addReceta(Receta receta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, receta.getTitulo());
        values.put(COLUMN_DESCRIPCION, receta.getDescripcion());
        values.put(COLUMN_CATEGORIA, receta.getCategoria());
        values.put(COLUMN_IMAGEN, receta.getImagen());
        values.put(COLUMN_INGREDIENTES, receta.getIngredientes());
        values.put(COLUMN_PASOS, receta.getPasos());
        values.put(COLUMN_IMAGEN_RES_ID, receta.getImagenResId());
        long id = db.insert(TABLE_RECETAS, null, values);
        db.close();
        return id;
    }

    // 2. Read (Obtener todas las recetas)
    public List<Receta> getAllRecetas() {
        List<Receta> recetas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RECETAS;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Receta receta = new Receta(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASOS))
                );
                receta.setImagenResId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_RES_ID)));
                recetas.add(receta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recetas;
    }

    // 3. Read (Obtener una receta por ID)
    public Receta getRecetaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RECETAS + " WHERE " + COLUMN_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            Receta receta = new Receta(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASOS))
            );
            receta.setImagenResId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_RES_ID)));
            cursor.close();
            db.close();
            return receta;
        }
        cursor.close();
        db.close();
        return null;
    }

    // 4. Update (Actualizar una receta)
    public int updateReceta(Receta receta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, receta.getTitulo());
        values.put(COLUMN_DESCRIPCION, receta.getDescripcion());
        values.put(COLUMN_CATEGORIA, receta.getCategoria());
        values.put(COLUMN_IMAGEN, receta.getImagen());
        values.put(COLUMN_INGREDIENTES, receta.getIngredientes());
        values.put(COLUMN_PASOS, receta.getPasos());
        values.put(COLUMN_IMAGEN_RES_ID, receta.getImagenResId());

        int rowsAffected = db.update(TABLE_RECETAS, values, COLUMN_ID + "=?", new String[]{String.valueOf(receta.getId())});
        db.close();
        return rowsAffected;
    }

    // 5. Delete (Eliminar una receta)
    public int deleteReceta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_RECETAS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}

