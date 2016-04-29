package com.example.marcio.googlemaps;
import android.util.*;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.auth.GoogleAuthUtil;

import java.sql.Connection;

public class splashActivity extends AppCompatActivity {

    Context context = splashActivity.this;
    AccountManager mAccountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        syncGoogleAccount();
    }

    private String[] getAccount (){

        mAccountManager = mAccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[]names = new String [accounts.length];

        for(int i=0 ; i< names.length; i++){
            names[i]=accounts[i].name;


        }
        return names;
    }

    private AbstractGetNameTask getTask (splashActivity activity, String email, String scope ){
        return new GetNameInForeground(activity,email,scope);
    }

    private void syncGoogleAccount(){
        if(isNetworkAvailable() == true ){
        String[] contas = getAccount();

            if(contas.length > 0){
                getTask(splashActivity.this, contas[0], SCOPE ).execute();
            }else{
                Toast.makeText(splashActivity.this, "Sem Conta do Google para sincronizar!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(splashActivity.this, "Sem Rede para conectar!",
                    Toast.LENGTH_SHORT).show();

        }

    }

    public boolean isNetworkAvailable(){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            Log.e ("Testando rede","Disponivel");
            return true;
        }

        Log.e ("Testando rede","Não Disponivel");
        return false;
    }


/*    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/
}
