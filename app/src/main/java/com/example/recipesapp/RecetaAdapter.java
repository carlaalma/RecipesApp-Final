package com.example.recipesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipesapp.models.Receta;

import java.util.List;

public class RecetaAdapter extends BaseAdapter {

    private Context context;
    private List<Receta> recetas;

    public RecetaAdapter(Context context, List<Receta> recetas) {
        this.context = context;
        this.recetas = recetas;
    }

    @Override
    public int getCount() {
        return recetas.size();
    }

    @Override
    public Object getItem(int position) {

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

        // Configurar los datos
        Receta receta = recetas.get(position);
        imgReceta.setImageResource(receta.getImagenResId());
        txtNombre.setText(receta.getTitulo());
        txtDescripcion.setText(receta.getDescripcion());

        return convertView;
    }
}
