package com.example.projeto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.projeto1.DAO.DAO;
import com.example.projeto1.OBJETOS.Veiculo;

import java.io.ByteArrayOutputStream;

public class AddVeiculo extends AppCompatActivity {

    Context context;
    ImageView adicionarImagem;
    String imagemEmString = "";
    String id;
    EditText modelo;
    EditText placa;
    EditText diaria;
    EditText marca;
    Button adicionar;
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_veiculo);
        context = getApplicationContext();



        adicionarImagem = findViewById(R.id.activity_add_veiculo_icone);
        modelo = findViewById(R.id.activity_add_veiculo_edit_modelo);
        diaria = findViewById(R.id.activity_add_veiculo_edit_diaria);
        marca = findViewById(R.id.activity_add_veiculo_edit_marca);
        placa = findViewById(R.id.activity_add_veiculo_edit_placa);
        adicionar = findViewById(R.id.activity_add_veiculo_botao_adicionar);
        cancelar = findViewById(R.id.activity_add_veiculo_botao_cancelar);

        Intent intent = getIntent();

        if(intent.getStringExtra("editando").equals("s")){

            id = intent.getStringExtra("id");

            modelo.setText( intent.getStringExtra("modelo"));

            diaria.setText(intent.getStringExtra("diaria"));

            marca.setText(intent.getStringExtra("marca"));

            placa.setText(intent.getStringExtra("placa"));

            if (intent.getStringExtra("imagem") != null) {
                if (intent.getStringExtra("imagem").equals("") ||
                        intent.getStringExtra("imagem").equals("null")) {
                    adicionarImagem.setImageResource(android.R.drawable.ic_menu_camera);
                } else {
                    byte[] imagemEmBytes;
                    imagemEmBytes = Base64.decode(
                            intent.getStringExtra("imagem"),
                            Base64.DEFAULT);
                    Bitmap imagemDecodificada =
                            BitmapFactory.decodeByteArray(
                                    imagemEmBytes,
                                    0,
                                    imagemEmBytes.length);
                    adicionarImagem.setImageBitmap(
                            imagemDecodificada);
                }
            }
        }

        adicionarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insereVeiculo();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void insereVeiculo() {
        DAO dao = new DAO(getApplicationContext());
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(modelo.getText().toString());

        try{
            veiculo.setId(Integer.valueOf(id));
        } catch (Exception e) {

        }


        veiculo.setDiaria(diaria.getText().toString());
        veiculo.setPlaca(placa.getText().toString());
        veiculo.setMarca(marca.getText().toString());
        veiculo.setImagem(imagemEmString);
        dao.insereVeiculo(veiculo);
        dao.close();
        finish();
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        super.onActivityResult(requestCode, resultCode, dados);
        if(requestCode == 1) {

        } else if(requestCode == 2) {
            if(resultCode == RESULT_OK){
                try{

                    Uri imageUri = dados.getData();

                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(fotoBuscada, 256, 256, true);

                    adicionarImagem.setImageBitmap(fotoRedimensionada);
                    byte[] fotoEmBytes;
                    ByteArrayOutputStream streamDaFotoEmBytes =
                            new ByteArrayOutputStream();

                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG,
                            70,
                            streamDaFotoEmBytes);

                    fotoEmBytes = streamDaFotoEmBytes.toByteArray();

                    imagemEmString = Base64.encodeToString(
                            fotoEmBytes,
                            Base64.DEFAULT);

                } catch (Exception e) {

                }
            } else {
                adicionarImagem.setImageResource(android.R.drawable.ic_menu_camera);
                imagemEmString = "null";
            }
        }
    }

}