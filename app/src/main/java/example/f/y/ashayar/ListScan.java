package example.f.y.ashayar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import Model.Response;
import Parser.JsonParse;
import RecyclerView.RecyclerShowScanAdapter;

/**
 * Created by jasmine on 6/29/2016.
 */
public class ListScan extends AppCompatActivity {
    Button save;
    private Boolean isFabOpen=false;
    List<InfoBarcode> Scan;
    RecyclerView list_sale;
    boolean flagScan=true;
    String ip,Shipment;
List<String> SerialNumber;
    Toolbar toolbar;
    RecyclerShowScanAdapter adapter;
    String error;
    TextView NewT,ScanT;
    Handler handler;
    int HandelPosition;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    FloatingActionButton first,scan,New;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_scan_floatingbutton);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        Intent intent=getIntent();
        ip=intent.getStringExtra("ip");
        Shipment=intent.getStringExtra("shipment");
        //authorize=intent.getStringExtra("authorize");
        initiation();
       Listener();
        String uriGet="http://"+ip+"/api/asha/SDSO_Shipment";
        MyBarcode myBarcode=new MyBarcode();
        myBarcode.execute(uriGet);
    }
    public void  initiation(){
ScanT=(TextView)findViewById(R.id.ScanT);
        NewT=(TextView)findViewById(R.id.NewT);
        handler=new Handler();
        scan=(FloatingActionButton)findViewById(R.id.scan);
        New=(FloatingActionButton)findViewById(R.id.New);
        first=(FloatingActionButton)findViewById(R.id.add);
fab_open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_backward=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        rotate_forward=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);

     //   finish=(Button)findViewById(R.id.finish);
        save=(Button)findViewById(R.id.save);
        list_sale=(RecyclerView)findViewById(R.id.list_sale);
        Scan=new ArrayList<>();
        SerialNumber=new ArrayList<>();
    }
    public void Listener(){
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ListScan.this);
                intentIntegrator.initiateScan();
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
AnimationFab();
            }
        });
        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog("افزودن", "Add", 0);
            }
        });
    }
    public void AnimationFab(){
        if(isFabOpen){

            first.startAnimation(rotate_backward);
            scan.startAnimation(fab_close);
            New.startAnimation(fab_close);
            New.setClickable(false);
            NewT.startAnimation(fab_close);
            ScanT.startAnimation(fab_close);
            scan.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            first.startAnimation(rotate_forward);
            New.startAnimation(fab_open);
            scan.startAnimation(fab_open);
            New.setClickable(true);
            scan.setClickable(true);
            NewT.startAnimation(fab_open);
            ScanT.startAnimation(fab_open);
            isFabOpen = true;
            Log.d("Raj", "open");

        }

    }
//    public void Listener(){
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (SerialNumber.size()!=0){
//                String uriSave = "http://" + ip + "/api/asha/SDSO_Shipment";
//                MyTaskBarcode myTaskBarcode = new MyTaskBarcode();
//                myTaskBarcode.execute(uriSave);}
//                else {
//                    Toast.makeText(ListScan.this,"سریال جدیدی وارد نشده است",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
public class MyBarcode extends AsyncTask<String,Void,Void>{
    @Override
    protected Void doInBackground(String... params) {
        RequestPackage requestPackage=new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setUri(params[0]);
        requestPackage.setParam("Shipment", Shipment);
        HttpUrlConnection connection=new HttpUrlConnection();
        Response response=connection.getData(requestPackage,"SerialDetails");
        if (response.getResponse()==200){
            JsonParse parse=new JsonParse();
            Scan=parse.FeedDataBarcode(response.getContent());
        }
        if (Scan.size()!=0){
                flagScan=false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        setRecycler();
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        InfoBarcode infoBarcode;
        String contain;

        if (data!=null) {
             contain= intentResult.getContents();
            int count = Scan.size();
            infoBarcode=new InfoBarcode();
            infoBarcode.setBarcode(contain);
            if (!flagScan){
            boolean flag = true;
            try {
                for (int i = 0; i < count; i++) {
                    if (Scan.get(i).getBarcode() .equals(contain) ) {
                        Toast.makeText(ListScan.this, "تکراری", Toast.LENGTH_SHORT).show();
                        flag = false;}}
                if (flag) {
                    Scan.add(infoBarcode);
                    SerialNumber.add(contain);
                    setRecycler();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
            if (flagScan){
                Scan.add(infoBarcode);
                flagScan=false;
                SerialNumber.add(contain);
                setRecycler();

            }
            if (SerialNumber.size()!=0){
                String uriSave = "http://" + ip + "/api/asha/SDSO_Shipment";
                MyTaskBarcode myTaskBarcode = new MyTaskBarcode();
                myTaskBarcode.execute(uriSave,contain);
            }
            else {
                Toast.makeText(ListScan.this,"سریال جدیدی وارد نشده است",Toast.LENGTH_SHORT).show();
            }
           new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    IntentIntegrator intentIntegrator = new IntentIntegrator(ListScan.this);
                    intentIntegrator.initiateScan();
                }
            }, 1000);

        }


    }
    public Runnable Update=new Runnable() {
        @Override
        public void run() {
            IntentIntegrator intentIntegrator = new IntentIntegrator(ListScan.this);
            intentIntegrator.initiateScan();
            handler.postDelayed(Update,3000);
        }
    };


    public void Dialog(String button, final String type, final int position){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(ListScan.this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ListScan.this);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
final TextView set=(TextView)promptsView.findViewById(R.id.textView1);
  final      TextView q=(TextView)promptsView.findViewById(R.id.q);
        // set dialog message
        if (type.equals("Add")){
            q.setVisibility(View.INVISIBLE);
            set.setText("لطفا شماره سریال را وارد کنید ");
        }else {
            set.setText( "کالا با شماره سریال "     +Scan.get(position).getBarcode() + "حذف خواهد شد");
            q.setText("آیا مایل به حذف هستید؟");
            userInput.setVisibility(View.INVISIBLE);
        }
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if (type.equals("Add")){
                                InfoBarcode infoBarcode=new InfoBarcode();
                                infoBarcode.setBarcode(userInput.getText().toString());
                                Scan.add(infoBarcode);
                                SerialNumber.add(infoBarcode.getBarcode());
                                    setRecycler();}
                                else {

                                    if (Scan.get(position).getMessage()!=null){
                                        MyTaskDelete myTaskDelete=new MyTaskDelete();
                                        myTaskDelete.execute(Scan.get(position));
                                      HandelPosition  =position;

                                    }
                                    else {
                                        for (int i=0;i<SerialNumber.size();i++){
                                            if (SerialNumber.get(i)==Scan.get(position).getBarcode()){
                                                SerialNumber.remove(i);
                                            }
                                        }
                                        Scan.remove(position);
                                        setRecycler();
                                    }
                                }
                            }
                        })
                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    public  class MyTaskDelete extends AsyncTask<InfoBarcode ,Void ,String >{
    @Override
    protected String doInBackground(InfoBarcode... params) {
        HttpUrlConnection connection=new HttpUrlConnection();
        RequestPackage p=new RequestPackage();
        p.setUri("http://" + ip + "/api/asha/SDSO_Shipment");

        Response response=connection.DeleteDat(p,Shipment,params[0].getBarcode());
        if (AnswerToResponse.Answer2Response(response.getResponse())== "ذخیره شد"){
            JsonParse parse=new JsonParse();
          String message=  parse.FeedFinishMessage(response.getContent(),"Message");
            return "حذف شد";
        }
        else{
            return AnswerToResponse.Answer2Response(response.getResponse());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals( "حذف شد")){
            Scan.remove(HandelPosition);
            adapter.remove(HandelPosition);}
        Toast.makeText(ListScan.this,s,Toast.LENGTH_SHORT).show();
    }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //}
//public class m extends AsyncTask<Void,Void,List<InfoBarcode>>{
//
//    @Override
//    protected List<InfoBarcode> doInBackground(Void... params) {
//        return null;
//    }
//}
    public class MyTaskBarcode extends AsyncTask<String,String ,List<InfoBarcode>>{
        @Override
        protected List<InfoBarcode> doInBackground(String... params) {
            List<InfoBarcode>  barcodes=new ArrayList<>();
            RequestPackage requestPackage=new RequestPackage();
            requestPackage.setMethod("POST");
           // ArrayList<String > serial=new ArrayList<>();
          //  String serial[]=new String[SerialNumber.size()];
//            for (int i=0;i<SerialNumber.size();i++){
////                serial[i]=SerialNumber.get(i);
//                serial.add(SerialNumber.get(i));
//            }
            requestPackage.setParam("PartSerialCode", params[1]);
        //    requestPackage.setParamAuthorize("PartSerialCode",serial);
//            for (int j=0;j<SerialNumber.size();j++){
//            requestPackage.setParam("SerialNumber"+j,SerialNumber.get(j));}
            requestPackage.setUri(params[0]);
            HttpUrlConnection connection=new HttpUrlConnection();
            Response response= null;
            try {
                response = connection.sendData(requestPackage,Shipment,"Authorize",null);
                JsonParse parse=new JsonParse();
                barcodes=parse.FeedMessage(response.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            error= AnswerToResponse.Answer2Response(response.getResponse());

            return barcodes;
        }
        @Override
        protected void onPostExecute(List<InfoBarcode> s) {
            super.onPostExecute(s);
     //       if (s== "ذخیره شد"){
//                for (int i=0;i<Scan.size();i++){
//                    if (Scan.get(i).getMessage()==null){
//                        Scan.get(i).setAuthorize("Authorize");
//                    }
//                }
                 SerialNumber.clear();

                 adapter.swap(s);
      //      }
      //      Toast.makeText(ListScan.this,s,Toast.LENGTH_SHORT).show();
        }
    }
    public void setRecycler(){
 adapter=new RecyclerShowScanAdapter(ListScan.this, Scan, new RecyclerShowScanAdapter.OnItemClickListener() {
       @Override
       public void onItemClick(InfoBarcode item, int position) {
           Dialog("حذف","Subtract",position);
       }
   });
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        list_sale.setLayoutManager(ll);
        list_sale.setAdapter(adapter);
    }
}
