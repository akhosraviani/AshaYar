package example.f.y.ashayar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.InfoBarcode;
import Model.InformationPerson;
import Model.Response;
import Model.ShipmentAuthorize;
import Parser.JsonParse;
import RecyclerView.RecyclerAdapterAuthorize;

/**
 * Created by jasmine on 6/21/2016.
 */
public class Information extends AppCompatActivity {
    Button registor,finish;
    String error,ip,code;
    Toolbar toolbar;
    TextView ID,name,weight,reception,carrier,cellphone,Code,Name,Phone,Plak,Wcar,Ecar;
    List<InformationPerson> informationPersons;
 //   List<ShipmentAuthorize> authorizes;
//    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
        setContentView(R.layout.design_information);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);}
        catch (Exception e) {
            e.getMessage();}
        Intent intent=getIntent();
         ip=intent.getStringExtra("ip");
       code =intent.getStringExtra("code");
        String urlShipment="http://"+ip+"/api/asha/sDSO_Shipment";
        String urlAuthorize="http://"+ip+"/api/asha/sDSO_ShipmentAuthorize";
initiation();
        Listener();
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(Information.this,ListScan.class);
//                startActivity(intent1);
////                Intent intent=new Intent(Information.this,ScanBarcode.class);
////                startActivity(intent);
//            }
//        });
        MyInformation myInformation=new MyInformation();
        myInformation.execute(urlShipment, code,urlAuthorize);
    }
    public void Listener(){
        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        Intent intent=new Intent(Information.this,ListScan.class);
                        intent.putExtra("ip",ip);
                        intent.putExtra("shipment", code);
                     //   intent.putParcelableArrayListExtra("contact", (ArrayList) authorizes);
                        startActivity(intent);
                    }catch (Exception e){
                        e.getMessage();
                    }

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
//           MyTaskFinish myTaskFinish=new MyTaskFinish();
//                myTaskFinish.execute();
            }
        });
    }
        public void initiation (){
            Code=(TextView)findViewById(R.id.code);
            Name=(TextView)findViewById(R.id.Name);
            Phone=(TextView)findViewById(R.id.phone);
            Plak=(TextView)findViewById(R.id.plak);
            Wcar=(TextView)findViewById(R.id.wcar);
            Ecar=(TextView)findViewById(R.id.ecar);
            Typeface typeface=Typeface.createFromAsset(getAssets(),"iranscanmobile.ttf");
            Code.setTypeface(typeface);
            Name.setTypeface(typeface);
            Phone.setTypeface(typeface);
            Plak.setTypeface(typeface);
            Wcar.setTypeface(typeface);
            Wcar.setTypeface(typeface);
            Ecar.setTypeface(typeface);
        registor=(Button)findViewById(R.id.registor);
//        recyclerView=(RecyclerView)findViewById(R.id.show_Authorize);
      //  ok=(Button)findViewById(R.id.ok);
        ID=(TextView)findViewById(R.id.ID);
        finish=(Button)findViewById(R.id.finish);
        name=(TextView)findViewById(R.id.Name);
      //  Loading=(TextView)findViewById(R.id.Bill);
        weight=(TextView)findViewById(R.id.Weight);
        reception=(TextView)findViewById(R.id.reception);
        carrier=(TextView)findViewById(R.id.pelak);
        cellphone=(TextView)findViewById(R.id.phone);
    }
    public class MyTaskFinish extends AsyncTask<Void,Void,String>{


        @Override
        protected String doInBackground(Void... params) {
            RequestPackage aPackage=new RequestPackage();
            aPackage.setUri("http://" + ip + "/api/asha/sDSO_Shipment/CompleteLoading");
            aPackage.setMethod("PUT");
            HttpUrlConnection connection=new HttpUrlConnection();
            try {
              Response response=connection.sendData(aPackage, code, "finish", null);
                if (response.getResponse()==200){
                    JsonParse parse=new JsonParse();
                    String Message=parse.FeedFinishMessage(response.getContent(),"Message");
                    return Message;
                }
                else {
                    return AnswerToResponse.Answer2Response(response.getResponse());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Information.this,s,Toast.LENGTH_SHORT).show();
finish();

        }
    }
//    public void SetRecycler(){
//        RecyclerAdapterAuthorize adapter=new RecyclerAdapterAuthorize(authorizes,Information.this,ip,code);
//        LinearLayoutManager ll=new LinearLayoutManager(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        ll.scrollToPosition(0);
//        try{
//            recyclerView.setLayoutManager(ll);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        recyclerView.setAdapter(adapter);
//    }
    class MyInformation extends AsyncTask<String,Void,Void>{


        @Override
        protected Void doInBackground(String... params) {

            RequestPackage aPackage = new RequestPackage();
            aPackage.setMethod("GET");
            aPackage.setUri(params[0]);
            aPackage.setParam("Shipment", params[1]);
            HttpUrlConnection connection = new HttpUrlConnection();
            Response response = new Response();
            response = connection.getData(aPackage, "details");
            if (response.getResponse() == 200 && !response.getContent().equals("[]")) {
                JsonParse parse = new JsonParse();
                informationPersons = parse.FeedDataPerson(response.getContent());
            } else {
                if (response.getResponse() == 200 && response.getContent().equals("[]")){
                    error="اطلاعاتی یافت نشد";
                }
                else
                error = AnswerToResponse.Answer2Response(response.getResponse());
                return  null;
            }
//            RequestPackage aPackage1=new RequestPackage();
//            aPackage1.setMethod("GET");
//            aPackage1.setUri(params[2]);
//            aPackage1.setParam("Shipment", params[1]);
//            response=connection.getData(aPackage1,"Authorize");
//            if (response.getResponse() == 200) {
//                JsonParse parse = new JsonParse();
//                //authorizes = parse.FeedDataAuthorize(response.getContent());
//            } else {
//                error = AnswerToResponse.Answer2Response(response.getResponse());
//                return  null;
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setData();
        }
    }
    public void setData(){
        if (informationPersons==null||informationPersons.size()==0){
            Toast.makeText(Information.this,error,Toast.LENGTH_SHORT).show();
        }else {
            try {
                InformationPerson informationPerson = informationPersons.get(0);
                if (informationPerson.getIDNumber()=="null")
                  ID.setText("");
                else
                    ID.setText(informationPerson.getIDNumber());
                if (informationPerson.getDriverName()=="null")
                name.setText("");
                else
                    name.setText(informationPerson.getDriverName());

                if (informationPerson.getTruckWeight()=="null")
                weight.setText("");
                else
                    weight.setText(informationPerson.getTruckWeight());
                if (informationPerson.getReceptionData()=="null")
                reception.setText("");
                else
                    reception.setText(informationPerson.getReceptionData());
                if (informationPerson.getCarrier()=="null")
                carrier.setText("");
                else
                    carrier.setText(informationPerson.getCarrier());
                if (informationPerson.getCallPhoneNumber()=="null")
                cellphone.setText("");
                else
                    cellphone.setText(informationPerson.getCallPhoneNumber());
          //      SetRecycler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    public void Dialog(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(Information.this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Information.this);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final TextView set=(TextView)promptsView.findViewById(R.id.textView1);
        final      TextView q=(TextView)promptsView.findViewById(R.id.q);

            set.setText( "آیا می خواهید پایان بارگیری صورت بگیرد؟");
//            q.setText("آیا مایل به حذف هستید؟");
            userInput.setVisibility(View.INVISIBLE);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("بله",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                MyTaskFinish myTaskFinish=new MyTaskFinish();
                                myTaskFinish.execute();
                            }
                        })
                .setNegativeButton("انصراف",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                                finish();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }@Override
     public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Dialog();

        }
        return super.onOptionsItemSelected(item);

    }
}
