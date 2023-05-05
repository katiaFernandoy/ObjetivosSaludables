package com.example.objetivossaludables.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.modelo.Noticias;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AdapterNoticias extends RecyclerView.Adapter<AdapterNoticias.MyViewHolder> {

    Context context;
    ArrayList<Noticias> listaNoticias;

    public AdapterNoticias(Context context, ArrayList<Noticias> listaNoticias) {
        this.context = context;
        this.listaNoticias = listaNoticias;
    }

    @NonNull
    @Override
    public AdapterNoticias.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNoticias.MyViewHolder holder, int position) {

        Noticias noticias = listaNoticias.get(position);
        holder.tvTitulo.setText(noticias.getTitulo());
        holder.tituloImagen.setImageResource(noticias.getTitleImage());
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

           TextView tvTitulo;
           ImageView tituloImagen;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvTitulo = itemView.findViewById(R.id.txt_noticiasItem);
            tituloImagen = itemView.findViewById(R.id.img_noticiasItem);

        }
    }
}
