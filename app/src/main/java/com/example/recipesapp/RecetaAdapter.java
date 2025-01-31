package com.example.recipesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipesapp.models.Receta;

import java.util.ArrayList;
import java.util.List;

public class RecetaAdapter extends ArrayAdapter<Receta> {

    private Context context;
    private List<Receta> recetas;

    public RecetaAdapter(Context context, ArrayList<Receta> recetas) {
        super(context, 0, recetas);
        this.context = context;
        this.recetas = recetas;
    }

    @Override
    public int getCount() {
        return (recetas != null) ? recetas.size() : 0;
    }

    @Override
    public Receta getItem(int position) {
        return recetas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receta, parent, false);
        }

        // Obtener elementos del layout
        ImageView imgReceta = convertView.findViewById(R.id.img_receta);
        TextView txtNombre = convertView.findViewById(R.id.txt_nombre);
        TextView txtDescripcion = convertView.findViewById(R.id.txt_descripcion);

        // Obtener la receta actual
        Receta receta = getItem(position);

        if (receta != null) {
            txtNombre.setText(receta.getTitulo());
            txtDescripcion.setText(receta.getDescripcion());
                imgReceta.setImageResource(receta.getImagen());
                 // Imagen de respaldo si es nulo
        }

        return convertView;
    }
}

