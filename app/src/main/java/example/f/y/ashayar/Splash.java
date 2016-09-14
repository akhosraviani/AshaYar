package example.f.y.ashayar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jasmine on 6/25/2016.
 */
public class Splash extends AppCompatActivity {
    TextView splashShow,splashShowFirst;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.splash);
            splashShow=(TextView)findViewById(R.id.splashText);
            splashShowFirst=(TextView)findViewById(R.id.splashTextfirst);
            Typeface typeface=Typeface.createFromAsset(getAssets(),"byekan.ttf");
            splashShowFirst.setTypeface(typeface);
            splashShowFirst.setText( "ارائه دهنده");
            splashShow.setTypeface(typeface);
            splashShow.setText("نرم افزار های یکپارچه سازمانی" );



        }catch (Exception e){{
        e.printStackTrace();}
        }
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (isOnline()){
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);}
                else {
                    Intent intent=new Intent(Splash.this,CheckWifi.class);
                    startActivity(intent);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    protected  boolean isOnline(){
        try {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
