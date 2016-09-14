package example.f.y.ashayar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import DB.DataSource;

/**
 * Created by jasmine on 4/30/2016.
 */
public class Login extends AppCompatActivity {
    Toolbar toolbar;
    Button login,change,setIp;
DataSource source;
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_ip);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        login=(Button)findViewById(R.id.login);
        source=new DataSource(Login.this);
        pass=(EditText)findViewById(R.id.pass);
        user=(EditText)findViewById(R.id.user);
        addListener();
  // enable overriding the default toolbar layout
   //     ab.setDisplayShowTitleEnabled(false);

    }

    private void addListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.open();
                List<String> decide=source.sellectAll();
                if (user.getText().toString().equals(decide.get(1))&& pass.getText().toString().equals(decide.get(0))){
                    source.close();
                    Intent intent=new Intent(Login.this,Change.class);
                    startActivity(intent);

//                    setIp.setVisibility(View.VISIBLE);
//                    iptext.setVisibility(View.VISIBLE);
                }else {
                    List<String> supperDecide=source.supperSelectAll();
                    if (user.getText().toString().equals(supperDecide.get(0))&& pass.getText().toString().equals(supperDecide.get(1))){
                        source.close();
                        Intent intent=new Intent(Login.this,SupperChange.class);
                        startActivity(intent);
//                    setIp.setVisibility(View.VISIBLE);
//                    iptext.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(Login.this,"your username or password is wrong!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        user.setText("");
        pass.setText("");
        user.requestFocus();

    }
}
