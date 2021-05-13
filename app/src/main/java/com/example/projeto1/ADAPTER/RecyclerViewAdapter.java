package com.example.projeto1.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto1.AddVeiculo;
import com.example.projeto1.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    List<String> modelos = new ArrayList<String>();
    public static List<Integer> veiculosParaRemover = new ArrayList<Integer>();

    Integer[] id;
    String[] marcas;
    String[] placas;
    String[] diarias;
    String[] imagens;

    View viewOnCreate;
    ViewHolder viewHolderLocal;


    public RecyclerViewAdapter(Context contextReceived,
                               Integer[] idReceived,
                               String[] modelosRecebidos,
                               String[] placasRecebidas,
                               String[] marcasRecebidas,
                               String[] diariasRecebidas,
                               String[] imagensRecebidas) {

        context = contextReceived;
        modelos.addAll(Arrays.asList(modelosRecebidos));
        id = idReceived;
        placas = placasRecebidas;
        marcas = marcasRecebidas;
        diarias = diariasRecebidas;
        imagens = imagensRecebidas;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textMarca;
        public TextView textPlaca;
        public TextView textModelo;
        public TextView textDiaria;
        public ImageView icone;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            textMarca = itemView.findViewById(R.id.itens_recyclerviewadapter_marca);
            textModelo = itemView.findViewById(R.id.itens_recyclerviewadapter_modelo);
            textPlaca = itemView.findViewById(R.id.itens_recyclerviewadapter_placa);
            textDiaria = itemView.findViewById(R.id.itens_recyclerviewadapter_diaria);
            icone = itemView.findViewById(R.id.itens_recyclerviewadapter_icone);
            checkBox = itemView.findViewById(R.id.itens_recyclerviewadapter_checkbox);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewOnCreate = LayoutInflater.from(context).inflate(R.layout.itens_recyclerviewadapter, parent, false);
        viewHolderLocal = new ViewHolder(viewOnCreate);
        return viewHolderLocal;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.textModelo.setText(modelos.get(position));

        DecimalFormat formatMoney = new DecimalFormat("'R$' #,##");
        holder.textDiaria.setText(formatMoney.format(new BigDecimal(diarias[position])));

        holder.textMarca.setText(marcas[position]);
        holder.textPlaca.setText(placas[position]);

        if(imagens[position].equals("") || imagens[position].equals("null")){

            holder.icone.setImageResource(R.mipmap.ic_sem_imagem);

        } else {
            byte[] imageInBytes;
            imageInBytes = Base64.decode(imagens[position], Base64.DEFAULT);
            Bitmap imageDecoded = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
            holder.icone.setImageBitmap(imageDecoded);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(veiculosParaRemover.contains(id[position])){
                    veiculosParaRemover.remove(id[position]);
                } else {
                    veiculosParaRemover.add(id[position]);
                }

            }
        });


        viewOnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veiculosParaRemover = new ArrayList<>();
                Intent intent = new Intent(context, AddVeiculo.class);
                intent.putExtra("editando", "s");
                intent.putExtra("id", String.valueOf(id[position]));
                intent.putExtra("modelo", modelos.get(position));
                intent.putExtra("marca", marcas[position]);
                intent.putExtra("placa", placas[position]);
                intent.putExtra("diaria", diarias[position]);
                intent.putExtra("imagem", imagens[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return modelos.size();
    }

}

