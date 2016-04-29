package com.example.marcio.googlemaps;


import android.content.Intent;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marcio on 05/03/2016.
 */
public abstract class AbstractGetNameTask extends AsyncTask<Void,Void,Void> {

    protected splashActivity mainActivity;
    public static String GOOGLE_USER_DATA = "no data";

    protected String mScope;
    protected String mEmail;
    protected int mRequest;

    public AbstractGetNameTask(splashActivity mainActivity, String mEmail, String mScope) {
        this.mainActivity = mainActivity;
        this.mEmail = mEmail;
        this.mScope = mScope;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try{
            fetchNameFromProfileSever();
        }
        catch (IOException ex){
            onnError("Tente Novamente: "+ ex.getMessage(),ex);


        }
        catch(JSONException ex){

            onnError("Bad Response: "+ ex.getMessage(),ex);
        }
        return null;
    }

    protected void onnError(String msg, Exception e){
        if(e!=null){
            Log.e("","Exception: ",e);

        }


    }

    protected abstract String fetchToken()throws IOException;

    private void fetchNameFromProfileSever() throws IOException,JSONException{
        String token = fetchToken();
        URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + token);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        int sc = con.getResponseCode();
        if(sc==200){
            InputStream is = con.getInputStream();
            GOOGLE_USER_DATA = readResponse(is);
            is.close();
            Intent intent = new Intent(mainActivity,MapsActivity.class);
            intent.putExtra("email_id", mEmail);
            mainActivity.startActivity(intent);
            mainActivity.finish();
            return;

        }else if(sc==401){
            GoogleAuthUtil.invalidateToken(mainActivity,token);
            onnError("Server Auth Error: " + sc, null);
            return;

        }else{
            onnError("Return By Server: "+ sc,null);
            return;
        }

    }

    private static String readResponse(InputStream is) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while( ( len = is.read(data, 0, data.length))>=0){
            bos.write(data,0,len);
        }
        return new String (bos.toByteArray(), "UTF-8");
    }




}
