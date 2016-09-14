package example.f.y.ashayar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.InfoBarcode;
import Model.InformationRow;
import Model.Response;
import Model.ShipMentModel;
import Parser.JsonParse;
import RecyclerView.RecyclerAdapterSheet;

/**
 * Created by jasmine on 6/21/2016.
 */
public class ShipMent extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView recyclerView;
    Toolbar toolbar;
    String ip;
    List<ShipMentModel> models;
    String error;
    String contain;;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_floatingbutton);
        recyclerView=(RecyclerView)findViewById(R.id.sheetment);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        fab=(FloatingActionButton)findViewById(R.id.scan);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        Intent intent=getIntent();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ShipMent.this);
                intentIntegrator.initiateScan();
            }
        });
        ip=intent.getStringExtra("ip");
        String url="http://" + ip +"/api/asha/SDSO_Shipment/GetAllLoading";
        MySheetMent ment=new MySheetMent();
        ment.execute(url);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.shipment_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals(null)){
            return false;
        }
        final List<ShipMentModel> filteredModelList = filter(models, newText);
        setRecycler(filteredModelList);
        return false;
    }
    private List<ShipMentModel> filter(List<ShipMentModel> models, String query) {
        query = query.toLowerCase();
        final List<ShipMentModel> filteredModelList = new ArrayList<>();
        for (ShipMentModel model : models) {
            final String code = model.getCode().toLowerCase();
            if (code.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    public class MySheetMent extends AsyncTask<String,Void,List<ShipMentModel>>{
        @Override
        protected List<ShipMentModel> doInBackground(String... params) {
            RequestPackage requestPackage=new RequestPackage();
            requestPackage.setUri(params[0]);
            requestPackage.setMethod("GET");
            HttpUrlConnection connection=new HttpUrlConnection();
            Response response=new Response();
            response= HttpUrlConnection.getData(requestPackage, "check");
            if (response.getResponse()==200){
            JsonParse parse=new JsonParse();
          models =parse.FeedDataShipMent(response.getContent());
                return models;
            }else {
                error= AnswerToResponse.Answer2Response(response.getResponse());
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<ShipMentModel> s) {
            super.onPostExecute(s);
            if (s==null){
                Toast.makeText(ShipMent.this,error,Toast.LENGTH_SHORT).show();
            }
            else {setRecycler(s);}
        }
    }
    public void setRecycler(List<ShipMentModel> sheetments){
        RecyclerAdapterSheet adapter=new RecyclerAdapterSheet(ShipMent.this,sheetments,ip);
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (data!=null){
            contain= intentResult.getContents();
            MyShipment shipment=new MyShipment();
            shipment.execute(contain);
        }
    }
public class MyShipment extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... params) {
        RequestPackage aPackage=new RequestPackage();
        aPackage.setMethod("GET");
        aPackage.setUri( "http://" + ip + "/api/asha/SDSO_Shipment/CheckLoading");
        HttpUrlConnection connection=new HttpUrlConnection();
        aPackage.setParam("shipment",params[0]);
        Response response = connection.getData(aPackage,"shipment");
        JsonParse parse=new JsonParse();
        if (response.getResponse()!=200||response.getResponse()!=404){
            if (response.getResponse()==200){
        String result=parse.FeedFinishMessage(response.getContent(),"Status");
        return result;}
            else {
                return "Invalid";
            }
        }
        else
        return AnswerToResponse.Answer2Response(response.getResponse());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s.equals("Invalid")||s.equals("Valid")){
            if (s.equals("Valid")){
                Intent intent=new Intent(ShipMent.this,Information.class);
                intent.putExtra("code",contain);
                intent.putExtra("ip",ip);
                startActivity(intent);
            }
            else {
                Toast.makeText(ShipMent.this," شماره حمل یافت نشد",Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(ShipMent.this,s,Toast.LENGTH_SHORT).show();

    }
}

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        String url="http://" + ip +"/api/asha/SDSO_Shipment/GetAllLoading";
        MySheetMent ment=new MySheetMent();
        ment.execute(url);
        super.onResume();
    }
}
