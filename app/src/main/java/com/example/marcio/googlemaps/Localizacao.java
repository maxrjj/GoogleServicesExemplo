package com.example.marcio.googlemaps;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Marcio on 05/03/2016.
 */
public class Localizacao extends _Default {

    private int _idCliente;
    private String _lat;
    private String _long;

    public int get_idCliente() {
        return _idCliente;
    }

    public void set_idCliente(int _idCliente) {
        this._idCliente = _idCliente;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public Localizacao(){
        super();
        //0 - Marcio
        //1 - ryam
        //-1 - null
        this._idCliente = -1;
        this._lat = "0";
        this._long = "0";
    }

    //Consulta
    public ArrayList<Localizacao> getLista(){
        DB db = new DB();
        ArrayList<Localizacao> lista = new ArrayList<>();

        try {
            ResultSet resultSet = db.select("Select * from Local");
            if(resultSet != null){
                while(resultSet.next()){
                    Localizacao obj = new Localizacao();
                    obj.set_idCliente(resultSet.getInt("idCliente"));
                    obj.set_lat(resultSet.getString("Lat"));
                    obj.set_long(resultSet.getString("Long"));
                    lista.add(obj);
                    obj = null;

                }

            }
        }
        catch(Exception ex){
            this.Mensagem = ex.getMessage();
            this._status = false;
        }

        return lista;

    }

    //Grava
    public void salvar (){

        String comando = "";

        //Se idCliente == -1 é um Insert
        //Caso contrario é um Update
        if(this.get_idCliente() == -1){
            comando = String.format("INSERT INTO Local (idCliente, Lat, Long) VALUES ('%d','%s','%s')",
                    this.get_idCliente(),this.get_lat(),this.get_long());
        }
        else
        {
            comando = String.format("UPDATE Local SET Lat = '%s', Long = '%s' WHERE idCliente = '%d'",
                    this.get_lat(),this.get_long(),this.get_idCliente());

        }

        DB db = new DB();
        db.excute(comando);
        this.Mensagem = db.Mensagem;
        this._status = db._status;

    }




}
