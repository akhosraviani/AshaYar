package example.f.y.ashayar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jasmine on 6/25/2016.
 */
public class CheckWifi extends AppCompatActivity {
    Button retry,setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_wifi);
        initiation();
        Listener();
    }
    public void initiation(){
        retry=(Button)findViewById(R.id.retey);
        setting=(Button)findViewById(R.id.setting);
        setting.getBackground().setAlpha(0);
        font();
    }
    public void font(){
        Typeface typeface=Typeface.createFromAsset(getAssets(),"times.ttf");
        retry.setTypeface(typeface);
        setting.setTypeface(typeface);
    }

    public void Listener(){
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent intent = new Intent(CheckWifi.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CheckWifi.this, "اینترنت خاموش است", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CheckWifi.this, ShowWifi.class);
//                startActivity(intent);
                Toast.makeText(CheckWifi.this,"در درست ساخت",Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
