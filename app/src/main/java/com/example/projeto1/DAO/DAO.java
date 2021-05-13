package com.example.projeto1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projeto1.OBJETOS.Veiculo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {

        super(context,
                "projeto1",
                null,

                6);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_veiculo = "CREATE TABLE veiculo (" +
                "veiculoId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "veiculoMarca TEXT, " +
                "veiculoModelo TEXT, " +
                "veiculoPlaca TEXT, " +
                "veiculoDiaria REAL, " +
                "veiculoImagem TEXT);";
        db.execSQL(sql_veiculo);

        ContentValues data = new ContentValues();

        data.put("veiculoMarca", "Ford");
        data.put("veiculoModelo", "Focus");
        data.put("veiculoPlaca", "FOC-1234");
        data.put("veiculoDiaria", String.valueOf(new BigDecimal(99.00)));
        db.insert("veiculo", null, data );

        data.put("veiculoMarca", "Fiat");
        data.put("veiculoModelo", "Uno");
        data.put("veiculoPlaca", "FAT-2468");
        data.put("veiculoDiaria", String.valueOf(new BigDecimal(79.00)));
        db.insert("veiculo", null, data );

        data.put("veiculoMarca", "Volkswagen");
        data.put("veiculoModelo", "GOL");
        data.put("veiculoPlaca", "VKN-4816");
        data.put("veiculoDiaria", String.valueOf(new BigDecimal(89.00)));
        db.insert("veiculo", null, data );
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_drop_veiculo = "DROP TABLE IF EXISTS veiculo";
        db.execSQL(sql_drop_veiculo);
        onCreate(db);
    }

    public List<Veiculo> buscaVeiculo(String atualizar) {

        String sql = "SELECT * FROM veiculo;";
        SQLiteDatabase db = getReadableDatabase();

        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()) {
            Veiculo veiculo = new Veiculo();
            veiculo.setId(Integer.valueOf(c.getString(c.getColumnIndex("veiculoId"))));
            veiculo.setModelo(c.getString(c.getColumnIndex("veiculoModelo")));
            veiculo.setMarca(c.getString(c.getColumnIndex("veiculoMarca")));
            veiculo.setPlaca(c.getString(c.getColumnIndex("veiculoPlaca")));
            veiculo.setDiaria(c.getString(c.getColumnIndex("veiculoDiaria")));
            veiculo.setImagem(c.getString(c.getColumnIndex("veiculoImagem")));
            veiculos.add(veiculo);
        }
        return veiculos;
    }

    public void insereVeiculo(Veiculo veiculo) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues data = new ContentValues();

        data.put("veiculoId", veiculo.getId());
        data.put("veiculoMarca", veiculo.getMarca());
        data.put("veiculoModelo", veiculo.getModelo());
        data.put("veiculoPlaca", veiculo.getPlaca());
        data.put("veiculoDiaria", veiculo.getDiaria());

        if(!veiculo.getImagem().equals("")){
            data.put("veiculoImagem", veiculo.getImagem());
        }

        try{

            db.insertOrThrow("veiculo", null, data );
        } catch (SQLiteConstraintException e) {
            data.put("veiculoId", veiculo.getId());
            db.update("veiculo", data, "veiculoId = ?", new String[]{String.valueOf(veiculo.getId())});
        }

    }

    public void removeVeiculo(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM veiculo WHERE veiculoId = " + "'" + id + "'";
        db.execSQL(sql);
    }

}
