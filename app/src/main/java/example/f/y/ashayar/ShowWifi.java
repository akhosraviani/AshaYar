package example.f.y.ashayar;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;

import RecyclerView.ShowWifiAdapter;

/**
 * Created by jasmine on 6/25/2016.
 */
public class ShowWifi extends AppCompatActivity {
    WifiManager wifiManager;
    RecyclerView recyclerView;
    List<ScanResult> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_wifi);
        recyclerView=(RecyclerView)findViewById(R.id.show_wifi);
        wifiManager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        results=wifiManager.getScanResults();
        SetRecycler();
    }
    public void SetRecycler(){
        ShowWifiAdapter adapter=new ShowWifiAdapter(ShowWifi.this,results);
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        try{
            recyclerView.setLayoutManager(ll);
        }catch (Exception e){
            e.printStackTrace();
        }
        recyclerView.setAdapter(adapter);
    }
}
