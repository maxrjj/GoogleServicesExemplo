package com.example.marcio.googlemaps;

/**
 * Created by Marcio on 05/03/2016.
 */
public class _Default {
    protected String Mensagem;
    protected Boolean _status;

    public _Default(){
        this._status = true;
        this.Mensagem = "";


    }

    //Mensagem -------------------------------
    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }

    //Status ---------------------------------
    public Boolean get_status() {
        return _status;
    }

    public void set_status(Boolean _status) {
        this._status = _status;
    }
}
