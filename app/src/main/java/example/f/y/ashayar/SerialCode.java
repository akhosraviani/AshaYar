package example.f.y.ashayar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.InfoBarcode;
import Model.InformationRow;
import Model.Response;
import Parser.JsonParse;
import       RecyclerView.SerialCodeRecycler;
/**
 * Created by jasmine on 8/3/2016.
 */
public class SerialCode extends AppCompatActivity {
    List<InformationRow> rows;
    TextView textviewPhiwm;
    FloatingActionButton fab;
    String PartSerialCode;
    String phiwm,username,ip,uri;
    Toolbar toolbar;
    List<String> SerialCode;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.show_list_inventory);


            toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
            setSupportActionBar(toolbar);
            // Get the ActionBar here to configure the way it behaves.
            final ActionBar ab = getSupportActionBar();
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowCustomEnabled(true);
            Intent intent=getIntent();
            phiwm=intent.getStringExtra("phiwm");
            username=intent.getStringExtra("userName");
            ip=intent.getStringExtra("ip");
           uri="http://"+ip+"/api/asha/PhysicalWarehousing/SerialWarehousing";

            initiation();
            Listener();
        }catch (Exception e){
            e.getMessage();
        }


    }
    public void initiation(){
        SerialCode=new ArrayList<>();
        fab=(FloatingActionButton)findViewById(R.id.fab);
recyclerView=(RecyclerView)findViewById(R.id.Inventory);
    }
public class MyTaskPartSerial extends AsyncTask<String,Void ,List<String>  >{

    @Override
    protected List<String>  doInBackground(String... params) {
        RequestPackage requestPackage=new RequestPackage();
        requestPackage.setUri(uri);
        requestPackage.setMethod("Post");
        requestPackage.setParam("UserCode", username);
        requestPackage.setParam("PartSerialCode", params[0]);
        HttpUrlConnection connection=new HttpUrlConnection();
        Response response = null;
        try {
         response =  connection.sendData(requestPackage, phiwm, "serialcode", null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParse parse=new JsonParse();
         List<String> result=parse.FeedSerialCode(response.getContent());
        return result;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        if (strings.get(1).equals(""))
            SerialCode.add(strings.get(0));
        else {
        SerialCode.add(strings.get(1));
            if (PartSerialCode!=null){
                if (strings.get(1).equals(PartSerialCode))
                    Sound();
                else
                    Sound();
            }
        PartSerialCode=strings.get(1);}
        setRecyclerView();

        super.onPostExecute(strings);
    }
}
    public void setRecyclerView(){
    SerialCodeRecycler serialCodeRecycler=new SerialCodeRecycler(SerialCode,this);
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(serialCodeRecycler);
    }
    public void Sound(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String  contain;
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        InfoBarcode infoBarcode;
        try {
            if (data!=null){
                contain= intentResult.getContents();
                MyTaskPartSerial myTaskPartSerial=new MyTaskPartSerial();
                myTaskPartSerial.execute(contain);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(SerialCode.this);
                        intentIntegrator.initiateScan();
                    }
                }, 1000);
            }
        }catch (Exception e){
            e.getMessage();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void Listener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(SerialCode.this);
                intentIntegrator.initiateScan();
            }
        });

    }


}
