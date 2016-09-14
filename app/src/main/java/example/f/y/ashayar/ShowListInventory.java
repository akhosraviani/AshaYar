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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Answer.AnswerToResponse;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.InformationRow;
import Model.Response;
import Parser.JsonParse;
import RecyclerView.RecycleViewAdapter;

/**
 * Created by jasmine on 5/8/2016.
 */
public class ShowListInventory extends AppCompatActivity implements SearchView.OnQueryTextListener{
    String ip,inventorycode,phiwm,title;
    RecyclerView recyclerView;
    List<InformationRow> rows;
    TextView textviewPhiwm;
    FloatingActionButton fab;
    String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.show_list_inventory);
            Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_entry_data);
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            ab.setDisplayShowTitleEnabled(false);
            ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
            ab.setDisplayShowTitleEnabled(false);
        }catch (Exception e){
            e.getMessage();
        }

        Intent intent=getIntent();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        textviewPhiwm=(TextView)findViewById(R.id.phiwm);
        ip=intent.getStringExtra("ip");
        phiwm=intent.getStringExtra("partcode");
        title=intent.getStringExtra("Title");
        inventorycode=intent.getStringExtra("InventoryCode");
        textviewPhiwm.setText(title);
        recyclerView=(RecyclerView)findViewById(R.id.Inventory);
        addListener();
        MyTaskDetails details=new MyTaskDetails();
        String uri="http://" + ip + "/api/asha/PhysicalWarehousing";
        details.execute(phiwm,uri,"details");
    }

    private void addListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowListInventory.this,NewPartMenu.class);
                intent.putExtra("ip",ip);
                intent.putExtra("partcode",phiwm);
                intent.putExtra("InventoryCode",inventorycode);
                intent.putExtra("Title",title);
      //          finish();
                startActivityForResult(intent, 101);
            }
        });
    }

    private List<InformationRow> filter(List<InformationRow> models, String query) {
        query = query.toLowerCase();
        final List<InformationRow> filteredModelList = new ArrayList<>();
        for (InformationRow model : models) {
            final String code = model.getPartCode().toLowerCase();
            final String title=model.getPartTitle().toLowerCase();
            if (code.contains(query)) {
                filteredModelList.add(model);
            }else {
                if (title.contains(query))
                    filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
        final List<InformationRow> filteredModelList = filter(rows, newText);
        setRecycler(filteredModelList);
        return false;
    }

    public class   MyTaskDetails extends AsyncTask<String,Void,List<InformationRow>>{
      @Override
      protected List<InformationRow> doInBackground(String... params) {
          RequestPackage p=new RequestPackage();
          p.setUri(params[1]);
          p.setParam("partcode", params[0]);
          p.setMethod("GET");
          HttpUrlConnection connection=new HttpUrlConnection();
          Response response=new Response();
          response=connection.getData(p, "details");          if (response.getResponse()==200){
          JsonParse parse=new JsonParse();
          List<InformationRow> list=parse.FeedDataDetails(response.getContent());
          return list;}
          else {
              error= AnswerToResponse.Answer2Response(response.getResponse());
              return null;
          }
      }

      @Override
      protected void onPostExecute(List<InformationRow> informationRows) {
          super.onPostExecute(informationRows);
          Display(informationRows);
      }
  }

    private void Display(List<InformationRow> informationRows) {
        if (informationRows==null||informationRows.size()==0){
            Toast.makeText(ShowListInventory.this,error,Toast.LENGTH_SHORT).show();
        }else {
      setRecycler(informationRows);
        rows=informationRows;}
    }
    public void setRecycler(List<InformationRow> informationRows){
        RecycleViewAdapter recycleViewAdapter=new RecycleViewAdapter(ShowListInventory.this, informationRows, new
                RecycleViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(InformationRow item,int position) {
                        Intent intent=new Intent(ShowListInventory.this, DataEntryMenu.class);
                        intent.putExtra("partcode",phiwm);
                        intent.putExtra("Title",title);
                        intent.putExtra("sequence",item.getSequence());
                        intent.putExtra("partcode",item.getPartCode());
                        intent.putExtra("parttitle",item.getPartTitle());
                        intent.putExtra("UOM",item.getUOM());
                        intent.putExtra("Qty",item.getQty());
                        intent.putExtra("UOMTitle",item.getUOMTitle());
                        intent.putExtra("ip",ip);
                        intent.putExtra("InventoryCode",inventorycode);
                        intent.putExtra("position",position);
                        startActivityForResult(intent, 102);
                    }
                });
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(recycleViewAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode==101){
                InformationRow informationRow=new InformationRow();
                if (data.getStringExtra("sequence")!=""){
                    int lengh=rows.size();
                    informationRow.setSequence(lengh + 1 + "");
            }
                else {
                    informationRow.setSequence(data.getStringExtra("sequence"));
                }
                informationRow.setUOM(data.getStringExtra("UOM"));
                informationRow.setPartTitle(data.getStringExtra("partTitle"));
                informationRow.setPartCode(data.getStringExtra("partcode"));
                informationRow.setQty(data.getStringExtra("QTY"));

                rows.add(informationRow);
                setRecycler(rows);
            }
        else {
                if (resultCode==102){
                    int position=data.getIntExtra("position",0);
                    String qty=data.getStringExtra("QTY");
                    rows.get(position).setQty(qty);
                    setRecycler(rows);
                }
            }
    }
}