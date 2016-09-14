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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.Location;
import Model.LotCode;
import Model.PartCode;
import Model.Response;
import Parser.JsonParse;

/**
 * Created by jasmine on 5/3/2016.
 */
public class NewPartMenu extends AppCompatActivity {
    ImageButton Barcode;
    String partcode,sequence;
    static final String ACTION_SCAN="com.google.zxing.client.android.SCAN";
  static final   int Activity_Resualt=101;
    Toolbar toolbar;
    String PHIWM,ip,InventoryCode,lotcodeCode,locationCode,error,title;
    TextView phiwm,partCode,UOM,TITLE,Sequence;
    Spinner lotcode,location;
    List<Location> locations;
    List<LotCode> lotCodes;
    List<PartCode> partCodes;
    Button cancel,save;
    EditText QTY;
    boolean flag=false;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_part_menu);
        toolbar=(Toolbar)findViewById(R.id.toolbar_entry_data);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);
        initialize();
        addListener();
        partCode=(TextView)findViewById(R.id.partCode);
        Intent intent=getIntent();
        PHIWM=intent.getStringExtra("partcode");
        title=intent.getStringExtra("Title");
        InventoryCode=intent.getStringExtra("InventoryCode");
        phiwm =(TextView)findViewById(R.id.phiwm);
        phiwm.setText(title);
        ip=intent.getStringExtra("ip");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            if (flag){
                Intent intent=new Intent(NewPartMenu.this,ShowListInventory.class);
                intent.putExtra("sequence",sequence);
                intent.putExtra("partcode",partcode);
                if (partCodes.size()==0){
                    intent.putExtra("partTitle","null");
                    intent.putExtra("UOM","null");
                }
                else {
                    intent.putExtra("partTitle",partCodes.get(0).getTitle());
                    intent.putExtra("UOM",partCodes.get(0).getUOM());
                }
                intent.putExtra("QTY",QTY.getText());
                setResult(101, intent);
                finish();

            }else {
                setResult(100, intent);
                finish();}
        return super.onOptionsItemSelected(item);
    }

    private void addListener() {
        Barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(NewPartMenu.this,QrC.class);
//                startActivity(intent);
                IntentIntegrator intentIntegrator = new IntentIntegrator(NewPartMenu.this);
                intentIntegrator.initiateScan();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    Intent intent=new Intent(NewPartMenu.this,ShowListInventory.class);
                    intent.putExtra("sequence",sequence);
                    intent.putExtra("partcode",partcode);
                    if (partCodes.size()==0){
                        intent.putExtra("partTitle","null");
                        intent.putExtra("UOM","null");
                    }
                    else {
                    intent.putExtra("partTitle",partCodes.get(0).getTitle());
                    intent.putExtra("UOM",partCodes.get(0).getUOM());
                        intent.putExtra("UOM",partCodes.get(0).getUOM());
                    }
                    intent.putExtra("QTY",QTY.getText());
                    setResult(101, intent);
                    finish();

                }else {
                    try{
                        setResult(100, intent);
                        finish();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTaskSend send = new MyTaskSend();
                String UriSend = "http://" + ip + "/api/asha/PhysicalWarehousing";
                send.execute(UriSend, PHIWM, QTY.getText().toString(), UOM.getText().toString());
            }
        });
    }
class MyTaskSend extends AsyncTask<String ,String ,String>{

    @Override
    protected String doInBackground(String... params) {
        HttpUrlConnection connection=new HttpUrlConnection();
        RequestPackage p=new RequestPackage();
        p.setUri(params[0]);
        p.setParam("QTY", params[2]);
        p.setParam("UOM", params[3]);
        p.setMethod("POST");
        p.setParam("Location", locationCode);
        p.setParam("LotCode", lotcodeCode);
        p.setParam("PartCode", partcode);
        Response response= null;
        try {
            response = connection.sendData(p, PHIWM,"NewPart",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        error=AnswerToResponse.Answer2Response(response.getResponse());
        return response.getContent();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(NewPartMenu.this,error,Toast.LENGTH_SHORT).show();
        if (error.equals("save")){
            try {
                Sequence.setText(s);
                sequence=s;
                flag=true;
            }catch (Exception e){
                e.getMessage();
            }

        }
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //    partcode="13111000000007";
            IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        try {
            if (data!=null){
                String contain=intentResult.getContents();
                partcode=contain;
                String FormatName=intentResult.getFormatName();
                partCode.setText(contain);
                String    UriLocation="http://"+ip+"/api/asha/InventoryLocation/GetInventoryLocations";
                String  UriLotCode="http://"+ip+"/api/asha/InventoryLot/GetPartLot";
                String UriPartCode="http://"+ip+"/api/asha/InventoryPart";
                MyTaskSpinner myTask=new MyTaskSpinner();
                myTask.execute(UriLotCode,UriLocation,partcode,UriPartCode);
        }

            }catch (Exception e){
            e.printStackTrace();
        }

    }
    class MyTaskSpinner extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            HttpUrlConnection connection=new HttpUrlConnection();
            RequestPackage p=new RequestPackage();
            p.setParam("InventoryCode", InventoryCode);
            p.setMethod("GET");
            p.setUri(params[1]);
            Response response=new Response();
            JsonParse parse=new JsonParse();
            response= HttpUrlConnection.getData(p, "Location");
            if (response.getResponse()==200){
            locations=parse.FeedDataLocaion(response.getContent());}
            else {
                return AnswerToResponse.Answer2Response(response.getResponse());
            }
            //HttpUrlConnection connection1=new HttpUrlConnection();
            try{
                RequestPackage p1=new RequestPackage();
                p1.setParam("PartCode", params[2]);
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

            RequestPackage p2=new RequestPackage();
            p2.setParam("partCode", params[2]);
            p2.setUri(params[3]);
            p2.setMethod("GET");
           response=connection.getData(p2,"partcode");
            if (response.getResponse()==200){
            partCodes=parse.FeedDataNewPart(response.getContent());}
            else {
                return AnswerToResponse.Answer2Response(response.getResponse());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DisplaySpinner(s);
        }
    }
    public void DisplaySpinner(String response){
        if (response==null){
        try{
        TITLE.setText(partCodes.get(0).getTitle());
        UOM.setText(partCodes.get(0).getUOM());}catch (Exception e){
            e.printStackTrace();
        }
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
            Toast.makeText(NewPartMenu.this,response,Toast.LENGTH_SHORT).show();
        }
    }
    public void setAdapterSpinner1(String[] LOCATION,String[] LOTCODE) {
     //   location.setPrompt("location");
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
    private void initialize(){
       intent =new Intent(NewPartMenu.this,ShowListInventory.class);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
    Barcode=(ImageButton)findViewById(R.id.barcodeReader);
        lotcode=(Spinner)findViewById(R.id.lcS);
        location=(Spinner)findViewById(R.id.ScL);
        TITLE=(TextView)findViewById(R.id.parttitle);
        UOM=(TextView)findViewById(R.id.UOM);
        QTY=(EditText)findViewById(R.id.qty);
        Sequence=(TextView)findViewById(R.id.sequence);
}
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
//        try {
//            setResult(100, intent);
//            finish();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
