package com.example.objetivossaludables.adapters;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.modelo.Noticias;

import java.util.ArrayList;
import java.util.Collections;

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

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_card_noticias, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNoticias.MyViewHolder holder, int position) {

        Noticias noticias = listaNoticias.get(position);
        holder.tvTitulo.setText(noticias.getTitulo());
        holder.tituloImagen.setImageResource(noticias.getTitleImage());
        holder.btn_noticiasItem.setOnClickListener(v -> {
            Intent intentWeb = new Intent();
            intentWeb.setAction(Intent.ACTION_VIEW);
            intentWeb.setData(Uri.parse(noticias.getLink_btn()));
            context.startActivity(intentWeb);
        });
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo;
        ImageView tituloImagen;
        Button btn_noticiasItem;

        public MyViewHolder(@NonNull View view) {
            super(view);
            setColorState(view.getContext(), Collections.singletonList(itemView.findViewById(R.id.btn_noticiasItem)));

            tvTitulo = itemView.findViewById(R.id.txt_noticiasItem);
            tituloImagen = itemView.findViewById(R.id.img_noticiasItem);
            btn_noticiasItem = itemView.findViewById(R.id.btn_noticiasItem);

        }
    }
}
