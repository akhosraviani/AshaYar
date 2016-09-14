package example.f.y.ashayar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.InformationPhiwm;
import Model.Response;
import Parser.JsonParse;
import RecyclerView.RecyclerViewAdapterPhiwm;

/**
 * Created by jasmine on 5/7/2016.
 */
public class ShowPhiwm extends AppCompatActivity implements SearchView.OnQueryTextListener{
    String Ip,error,type,username;
    Intent intent;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<InformationPhiwm> mModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_phiwm_recycler);
        Intent intent=getIntent();
        username=intent.getStringExtra("userName");
        intent=getIntent();
        Ip=intent.getStringExtra("ip");
        type=intent.getStringExtra("Type");
        String uri="http://"+Ip+"/api/asha/PhysicalWarehousing/GetAllOpen";
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
       setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        recyclerView=(RecyclerView)findViewById(R.id.phiwm_recycler);
        MyTaskPhiwm myTaskPhiwm =new MyTaskPhiwm();
        myTaskPhiwm.execute(uri);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
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
        final List<InformationPhiwm> filteredModelList = filter(mModels, newText);

        RecyclerViewAdapterPhiwm adapter=new RecyclerViewAdapterPhiwm(ShowPhiwm.this,filteredModelList,Ip,type,username);
        recyclerView.setAdapter(adapter);
        return false;
    }
    private List<InformationPhiwm> filter(List<InformationPhiwm> models, String query) {
        query = query.toLowerCase();

        final List<InformationPhiwm> filteredModelList = new ArrayList<>();
        for (InformationPhiwm model : models) {
            final String code = model.getCode().toLowerCase();
            final String title=model.getInventoryCode().toLowerCase();
            if (code.contains(query)) {
                filteredModelList.add(model);
            }else {
                if (title.contains(query))
                    filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    class  MyTaskPhiwm extends AsyncTask<String,Void,List<InformationPhiwm>>{

      @Override
      protected List<InformationPhiwm> doInBackground(String... params) {
          RequestPackage p=new RequestPackage();
          p.setUri(params[0]);
          p.setMethod("GET");
          List<InformationPhiwm> test=new ArrayList<>();
          Response response=new Response();
          response= HttpUrlConnection.getData(p, "partcode");
          if (response.getResponse()==200){
          JsonParse parse=new JsonParse();
          return parse.FeedDataPhiwm(response.getContent());}
          else {
              error= AnswerToResponse.Answer2Response(response.getResponse());
              return null;
          }
      }

      @Override
      protected void onPostExecute(List<InformationPhiwm> informationPhiwms) {
          super.onPostExecute(informationPhiwms);
          Display(informationPhiwms);

      }
  }
    public void Display(List<InformationPhiwm> list){
        if (list.size()==0||list==null){
            Toast.makeText(ShowPhiwm.this,error,Toast.LENGTH_SHORT).show();
        }else {
        RecyclerViewAdapterPhiwm adapter=new RecyclerViewAdapterPhiwm(ShowPhiwm.this,list,Ip,type,username);
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(adapter);

        mModels=list;}
    }
}
