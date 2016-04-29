package com.example.marcio.googlemaps;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by Marcio on 05/03/2016.
 */
public class GetNameInForeground extends AbstractGetNameTask {
    public GetNameInForeground(splashActivity mainActivity, String mEmail, String mScope) {
        super(mainActivity, mEmail, mScope);
    }

    @Override
    protected String fetchToken() throws IOException {
        try{
            return GoogleAuthUtil.getToken(mainActivity,mEmail,mScope);


        }
        catch (GooglePlayServicesAvailabilityException playEx){}
        catch (UserRecoverableAuthException userEx){
            mainActivity.startActivityForResult(userEx.getIntent(),mRequest);

        }
        catch ( GoogleAuthException fatalEx){
            fatalEx.printStackTrace();


        }
        return null;

    }
}
