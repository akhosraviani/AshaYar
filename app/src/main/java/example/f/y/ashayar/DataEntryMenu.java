package example.f.y.ashayar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.Location;
import Model.LotCode;
import Model.Response;
import Parser.JsonParse;

/**
 * Created by jasmine on 5/3/2016.
 */
public class DataEntryMenu extends AppCompatActivity {
    Toolbar toolbar;
    TextView PHIWM;
    TextView Sequence;
    TextView PartCode;
    TextView PartTitle;
    TextView UOM;
    String UOMTitle;
    EditText Qty;
    String phiwm,sequence,partcode,parttitle,uom,qty,ip,lotCode,loCation,title;
    Button cancel,save;
    Spinner lotcode,location;
    List<Location> locations;
    List<LotCode> lotCodes;
    String UriLotCode,UriLocation,UriSave,InventoryCode;
    String locationCode,lotcodeCode;
    boolean flag=false;
    Intent intent;
    String QTYS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_entry_code);
        toolbar=(Toolbar)findViewById(R.id.toolbar_entry_data);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);
        lotcode=(Spinner)findViewById(R.id.lcS);
        location=(Spinner)findViewById(R.id.ScL);
        cancel=(Button)findViewById(R.id.cancel);
        save=(Button)findViewById(R.id.save);
        PHIWM =(TextView)findViewById(R.id.phiwm);
        Sequence=(TextView)findViewById(R.id.sequence);
        PartCode=(TextView)findViewById(R.id.partcode);
        PartTitle=(TextView)findViewById(R.id.parttitle);
        UOM=(TextView)findViewById(R.id.UOM);
        Qty=(EditText)findViewById(R.id.qty);
        intent=getIntent();
        phiwm=intent.getStringExtra("partcode");
        title=intent.getStringExtra("Title");
        UOMTitle=intent.getStringExtra("UOMTitle");
        PHIWM.setText(title);
        InventoryCode=intent.getStringExtra("InventoryCode");
        sequence=intent.getStringExtra("sequence");
        partcode=intent.getStringExtra("partcode");
        parttitle=intent.getStringExtra("parttitle");
        uom=intent.getStringExtra("UOM");
        qty=intent.getStringExtra("Qty");
        ip=intent.getStringExtra("ip");
//        PHIWM.setText(partcode);
        Sequence.setText(sequence);
        PartCode.setText(partcode);
        PartTitle.setText(parttitle);
        UOM.setText(UOMTitle);
        if (qty.equals("null")){
            Qty.setText("");
        }else {
        Qty.setText(qty);}
        Qty.setSelection(Qty.getText().length());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    Intent intent1=new Intent(DataEntryMenu.this,ShowListInventory.class);
                    intent1.putExtra("position",intent.getIntExtra("position", 0));
                    String qty=Qty.getText().toString();
                    intent1.putExtra("QTY",QTYS);
                    setResult(102,intent1);
                    finish();
                }else {
                finish();}
            }
        });
        UriLocation="http://"+ip+"/api/asha/InventoryLocation/GetInventoryLocations";
        UriLotCode="http://"+ip+"/api/asha/InventoryLot/GetPartLot";
        UriSave="http://"+ip+"/api/asha/PhysicalWarehousing";
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String qty=Qty.getText().toString();
                MyTaskSend send=new MyTaskSend();
                send.execute(sequence,partcode,parttitle,lotCode,loCation,UriSave);
            }
        });
     //   String t=Qty.getText().toString();
        try {
            MyTaskSpinner myTask=new MyTaskSpinner();
            myTask.execute(UriLotCode,UriLocation);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

public void DisplaySpinner(String s){
    if (s==null){
    String LOCATION[]=new String[locations.size()];
    for (int i=0;i< locations.size();i++){
        LOCATION[i]=locations.get(i).getTitle();
    }
    String LOTCODE[]=new String[lotCodes.size()];
    for (int i=0;i< lotCodes.size();i++){
        LOTCODE[i]=lotCodes.get(i).getTitle();
    }
    setAdapterSpinner1(LOCATION,LOTCODE);}
    else {
        Toast.makeText(DataEntryMenu.this,s,Toast.LENGTH_SHORT);
    }
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            if (flag){
                Intent intent1=new Intent(DataEntryMenu.this,ShowListInventory.class);
                intent1.putExtra("position", intent.getIntExtra("position", 0));
                intent1.putExtra("QTY",QTYS);
                setResult(102,intent1);
                finish();
            }else {
                finish();}}
        return super.onOptionsItemSelected(item);
    }

    public void setAdapterSpinner1(String[] LOCATION,String[] LOTCODE) {
        location.setPrompt("location");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,LOCATION);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(dataAdapter);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationCode = locations.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> dataAdapterLocation = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,LOTCODE);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lotcode.setAdapter(dataAdapterLocation);
        lotcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lotcodeCode = lotCodes.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    class MyTaskSpinner extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            HttpUrlConnection connection=new HttpUrlConnection();
            RequestPackage p=new RequestPackage();
            p.setParam("InventoryCode",InventoryCode);
            p.setMethod("GET");
            p.setUri(params[1]);
            Response response=new Response();
            response= connection.getData(p, "Location");
            JsonParse parse=new JsonParse();
            if (response.getResponse()==200){

            locations=parse.FeedDataLocaion(response.getContent());}
            else {
                return AnswerToResponse.Answer2Response(response.getResponse());
            }

            //HttpUrlConnection connection1=new HttpUrlConnection();
            try{
            RequestPackage p1=new RequestPackage();
            p1.setParam("PartCode", partcode);
            p1.setUri(params[0]);
                p1.setMethod("GET");

            response=connection.getData(p1,"lotCode");
                if (response.getResponse()==200){
                lotCodes=parse.FeedDataLotCode(response.getContent());}
                else {
                    return AnswerToResponse.Answer2Response(response.getResponse());
                }
            }catch (Exception e){
                e.printStackTrace();

            }
return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DisplaySpinner(s);
        }
    }
public class MyTaskSend extends AsyncTask<String ,String,String>{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        QTYS=Qty.getText().toString();
    }
    @Override
    protected String doInBackground(String... params) {
        RequestPackage requestPackage=new RequestPackage();
        requestPackage.setParam("QTY",QTYS);
        requestPackage.setParam("Sequence",params[0]);
        requestPackage.setParam("LotCode",lotcodeCode);
        requestPackage.setParam("Location", locationCode);
        requestPackage.setUri(params[5]);
        requestPackage.setMethod("PUT");
        HttpUrlConnection connection=new HttpUrlConnection();
        Response response= null;
        try {
            response = connection.sendData(requestPackage, phiwm, "DataEntry",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String error= AnswerToResponse.Answer2Response(response.getResponse());
        return error;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(DataEntryMenu.this,s,Toast.LENGTH_SHORT).show();
        if (s.equals("ذخیره شد")){
        flag=true;}

    }
}

}
