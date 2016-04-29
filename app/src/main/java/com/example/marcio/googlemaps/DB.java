package com.example.marcio.googlemaps;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by Marcio on 05/03/2016.
 */
/*
Implementa classe Runnable porque precisamos rodar em uma thread
Porque precisamos conectar o banco em outra thread
Youtube explicando passo a passo a criação dessa classe:
https://www.youtube.com/watch?v=WOsQow2UKNU
*/
public class DB extends _Default implements Runnable{

    private Connection conn;
    private String host = "sql3.freemysqlhosting.net";
    private String db = "sql3109404";
    private int port = 3306;
    private String user = "sql3109404";
    private String pass = "aMDpiwdvKn";
    private String url = "jdbc:mysql://%s:%d/%s";
    //private String url = "jdbc:postgresql://sql3.freemysqlhosting.net:3306/sql3109404";

    public DB(){
        super();
        this.url = String.format(this.url,this.host,this.port,this.db);

        //Abre conexao
        this.Conecta();
        //fecha conexao
        this.Disconecta();
    }

    @Override
    public void run() {
        try{
            //Realiza a conexao com o banco de dados
            //Aqui dizemos qual driver o ODBC vai utilizar (MySQL)
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.url,this.user,this.pass);

        }catch (Exception ex){
            this.Mensagem = ex.getMessage();
            this._status = false;
        }

    }

    private void Conecta(){

        //coloca a classe que esta implementando o Runnable (esta classe)
        Thread thread = new Thread(this);
        //No momento que dou o start a thread vai rodar o que estiver no metodo run()
        thread.start();

        //se sair do try sem erro a conexao com o banco foi efetuada com sucesso
        try{
            //Vai esperar a conclusáo do metodo run()
            thread.join();
        }catch(Exception ex ){
            this.Mensagem = ex.getMessage();
            this._status = false;
        }


    }

    private void Disconecta(){

        //verifica se conn está nulo
        if(this.conn != null) {

           try{this.conn.close();}
           catch (Exception ex){ }
           finally {this.conn = null;}
        }

    }

    public ResultSet select (String query){

        this.Conecta();
        ResultSet resultSet = null;

        try {
            resultSet = new ExecuteDB(this.conn, query).execute().get();
        }catch (Exception ex){

            this.Mensagem = ex.getMessage();
            this._status = false;
        }

        return resultSet;
    }


    public ResultSet excute (String query){

        this.Conecta();
        ResultSet resultSet = null;

        try {
            resultSet = new ExecuteDB(this.conn, query).execute().get();
        }catch (Exception ex){

            this.Mensagem = ex.getMessage();
            this._status = false;
        }

        return resultSet;
    }

}
