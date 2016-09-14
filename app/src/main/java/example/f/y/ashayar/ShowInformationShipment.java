package example.f.y.ashayar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

import Model.ShipmentAuthorize;
import RecyclerView.RecyclerAdapterAuthorize;
/**
 * Created by jasmine on 7/31/2016.
 */
public class ShowInformationShipment extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ShipmentAuthorize>  authorizes;
    Toolbar toolbar;
    String ip,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info_ship);
        initiation();
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
SetData();
        SetRecycler();
    }
    public void initiation(){
        recyclerView=(RecyclerView)findViewById(R.id.info_ship);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);

    }
    public void SetData(){
Intent intent=getIntent();
        authorizes=(List) intent.getParcelableArrayListExtra("contact");
        ip=intent.getStringExtra("ip");
        code=intent.getStringExtra("shipment");
    }
    public void SetRecycler(){
        RecyclerAdapterAuthorize adapter=new RecyclerAdapterAuthorize(authorizes,ShowInformationShipment.this,ip,code);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
