package com.example.marcio.googlemaps;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Marcio on 05/03/2016.
 para não ter problema no banco (erro: SELECT nao pode ser realizada na thread principal)
 Provavelmente por cause de rede ou network connection
 entao fui obrigado a gerar uma classe extendendo a AsyncTask para realizar essa pesquisa
 */

//
public class ExecuteDB extends AsyncTask<String,Void,ResultSet> {

    private Connection conn;
    private String sqlQuery;

    public ExecuteDB(Connection conn, String sqlQuery) {
        this.conn = conn;
        this.sqlQuery = sqlQuery;
    }

    @Override
    protected ResultSet doInBackground(String... params) {
        ResultSet resultSet =  null;
        try{
            resultSet = conn.prepareCall(sqlQuery).executeQuery();

        }
        catch (Exception ex){


        }
        finally {
            try{conn.close();}catch (Exception ex){}
        }

        return resultSet;
    }
}
