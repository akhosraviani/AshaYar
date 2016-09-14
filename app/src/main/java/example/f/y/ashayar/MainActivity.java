package example.f.y.ashayar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Answer.AnswerToResponse;
import DB.DataSource;
import HttpConnection.HttpUrlConnection;
import HttpConnection.RequestPackage;
import Model.LoginModel;
import Model.Response;
import Parser.JsonParse;
public class MainActivity extends AppCompatActivity {
    EditText userName,passWord;
    String ip,ToastUser,login,user,pass;
    DataSource source;
    Button button;
    LoginModel loginModels;
    Toolbar toolbar;
    Animation alpha_anim ;
    boolean doubleBackToExitPressedOnce = false;
    TextView welcome,userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              if (isOnline()) {
            setContentView(R.layout.activity_main);
                  toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
                  setSupportActionBar(toolbar);
                  // Get the ActionBar here to configure the way it behaves.
                  final ActionBar ab = getSupportActionBar();
                  final DataSource source=new DataSource(MainActivity.this);
                  //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
                  ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
                  ab.setDisplayHomeAsUpEnabled(true);
                  ab.setDisplayShowCustomEnabled(true);
              } else {
            Intent intent=new Intent(MainActivity.this,CheckWifi.class);
            startActivity(intent);
        }
        if (getIntent().getBooleanExtra("EXIT",false)){
            Intent intent1=new Intent(Intent.ACTION_MAIN);
            intent1.addCategory(Intent.CATEGORY_HOME);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);        }
        setText();
        DB();
        initiation();
        Listener();
        userName.setSelection(userName.getText().length());
        passWord.setSelection(passWord.getText().length());
    }
    public void DB(){
        source=new DataSource(MainActivity.this);
        source.open();
        List<String> supper=source.supperSelectAll();
        List<String> admin=source.sellectAll();
        if (admin.size()==0&&supper.size()==0){
            source.insert();
            source.supperInsert();
        }
    }
public void initiation(){
    try {
        userPass=(TextView)findViewById(R.id.userpass);

        welcome=(TextView)findViewById(R.id.wlecome);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"iranscanmobile.ttf");
        welcome.setTypeface(typeface);
        userPass.setTypeface(typeface);
        alpha_anim= AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        button=(Button)findViewById(R.id.login);
        userName=(EditText)findViewById(R.id.username);
        passWord=(EditText)findViewById(R.id.pas);
    }catch (Exception e){
        e.printStackTrace();
    }
    }
    public void setText(){
        login="LOGIN";
        ToastUser="please enter username or password!";
        user="username:";
        pass="password:";
    }
    public void Listener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(alpha_anim);
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Do your work here
//                        button.setBackgroundColor(Color.RED);
//                        return true;
//                    case MotionEvent.ACTION_UP:
//
//                        button.setBackgroundColor(Color.BLUE);
//                        return true;
//                    default:
//                        return false;
//                }
                if (isOnline()){
                    if (userName.getText().toString().isEmpty()||passWord.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this,ToastUser,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if ((ip=source.sellectIp())!=null){
                            final String uri="http://" + ip + "/api/asha/Authenticate";
                            MyTask myTask=new MyTask();
                            myTask.execute(userName.getText().toString(),passWord.getText().toString(),uri);}
                        else {
                            Toast.makeText(MainActivity.this,"please set ip!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                   Intent intent=new Intent(MainActivity.this,CheckWifi.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_manu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
           Intent intent1=new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.action_settings:
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    class MyTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            RequestPackage p=new RequestPackage();
            p.setParam("user",params[0]);
            p.setParam("pass", params[1]);
            p.setUri(params[2]);
            p.setMethod("GET");
            Response response=new Response();
            response= HttpUrlConnection.getData(p, "check");
            if (response.getResponse()==200){
                JsonParse parser=new JsonParse();
              loginModels=parser.FeedDataCheckPerson(response.getContent());
                return "Valid";
            }
          else {
                return AnswerToResponse.Answer2Response(response.getResponse());
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Decide(s);
        }
    }
    public void Decide(String result){
        if (result.equals("Valid")){
            LoginModel loginModel=new LoginModel();
            Intent intent=new Intent(MainActivity.this,ShowDecide.class);

            intent.putExtra("ip", ip);
            intent.putExtra("image",loginModels.getPhoto());
            intent.putExtra("name",loginModels.getName());
            intent.putExtra("code", loginModels.getCode());
            intent.putExtra("userName",userName.getText().toString());
                startActivity(intent);
        }
        else {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        passWord.setText("");
        userName.setText("");

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isOnline()){
            userName.requestFocus();
            }
        else {
            Intent intent=new Intent(MainActivity.this,CheckWifi.class);
            startActivity(intent);
        }


    }
    protected  boolean isOnline(){
        try {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
