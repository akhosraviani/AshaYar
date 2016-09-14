package example.f.y.ashayar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import DB.DataSource;

/**
 * Created by jasmine on 5/18/2016.
 */
public class SupperChange extends AppCompatActivity {
    DataSource source;
    List<String> admine;
    List<String> supperAdmine;
    EditText UserAdmin,PassAdmin,Ip,RetryPassAdmin,UserSupperAdmin,PassSupperAdmin,RetryPassSupperAdmin;
    Button ChangeAdmin,ChangeSupperAdmin,SetIp;
    CheckBox ShowPassAdmin,ShowPassSupper;
    String ip;
    Toolbar toolbar;
    boolean flag=true;
    boolean Supperflag=true;
    LinearLayout ErrorUserAdmin,ErrorPassAdmin,WrongPassAdmin,SupperUser,PassSupper,WrongPassSupper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supper_change);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        source=new DataSource(SupperChange.this);
        source.open();
        admine=source.sellectAll();
        supperAdmine=source.supperSelectAll();
        ip=source.sellectIp();
        initialize();
        firstInitialize();
        check();
        addListener();

    }
    private void addListener() {
        Ip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Ip.setHint("");
                else
                    Ip.setHint("empty");
            }
        });
        ChangeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( check(UserAdmin,PassAdmin,RetryPassAdmin,"Admin")){
                    source.UpdateUserPass(PassAdmin.getText().toString(),UserAdmin.getText().toString());
                    Toast.makeText(SupperChange.this,"تغیرات شما با موفقیت انجام شد",Toast.LENGTH_SHORT).show();

                }
//                if (RetryPassAdmin.getText().toString().equals("")) {
//                    Toast.makeText(SupperChange.this, "fill retry!!!", Toast.LENGTH_SHORT).show();
//                    RetryPassAdmin.requestFocus();
//                    RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
//                } else if (RetryPassAdmin.getText().toString() != PassAdmin.getText().toString()) {
//                    Toast.makeText(SupperChange.this, "wrong", Toast.LENGTH_SHORT).show();
//                    RetryPassAdmin.requestFocus();
//
//                } else
//                    source.UpdateUserPass(PassAdmin.getText().toString(), UserAdmin.getText().toString());
            }
        });
        ChangeSupperAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check(UserSupperAdmin,PassSupperAdmin,RetryPassSupperAdmin,"Supper")){
                    source.SupperUpdateUserPass(PassSupperAdmin.getText().toString(),UserSupperAdmin.getText().toString());
                    Toast.makeText(SupperChange.this,"تغیرات شما با موفقیت انجام شد",Toast.LENGTH_SHORT).show();

                }
//                if (RetryPassSupperAdmin.getText().toString().equals("")) {
//                    Toast.makeText(SupperChange.this, "fill retry!!!", Toast.LENGTH_SHORT).show();
//                    RetryPassSupperAdmin.requestFocus();
//                } else if (RetryPassSupperAdmin.getText().toString() != PassSupperAdmin.getText().toString()) {
//                    Toast.makeText(SupperChange.this, "wrong", Toast.LENGTH_SHORT).show();
//                    RetryPassSupperAdmin.requestFocus();
//                } else
//                    source.SupperUpdateUserPass(PassSupperAdmin.getText().toString(), UserSupperAdmin.getText().toString());
            }
        });
        SetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.UpdateIp(Ip.getText().toString());
                Toast.makeText(SupperChange.this,"ادرس IP شما با موفقیت تنظیم شد",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void check() {
        ShowPassSupper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Supperflag){
                PassSupperAdmin.setTransformationMethod(null);
                RetryPassSupperAdmin.setTransformationMethod(null);
                    PassSupperAdmin.setSelection(PassSupperAdmin.getText().length());
                    RetryPassSupperAdmin.setSelection(RetryPassSupperAdmin.getText().length());
                Supperflag=false;
                }
                else {
                    RetryPassSupperAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    PassSupperAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    PassSupperAdmin.setSelection(PassSupperAdmin.getText().length());
                    RetryPassSupperAdmin.setSelection(RetryPassSupperAdmin.getText().length());
                    Supperflag=true;
                }
            }
        });
        ShowPassAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                PassAdmin.setTransformationMethod(null);
                RetryPassAdmin.setTransformationMethod(null);
                    PassAdmin.setSelection(PassAdmin.getText().length());
                    RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
                    flag=false;}
                else {
                    PassAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    RetryPassAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    PassAdmin.setSelection(PassAdmin.getText().length());
                    RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
                    flag=true;
                }
            }
        });
    }

    private void firstInitialize() {
        UserAdmin.setText(admine.get(1));
        UserAdmin.setSelection(UserAdmin.getText().length());
        PassAdmin.setText(admine.get(0));
        PassAdmin.setSelection(PassAdmin.getText().length());
        UserSupperAdmin.setText(supperAdmine.get(0));
        UserSupperAdmin.setSelection(UserSupperAdmin.getText().length());
        PassSupperAdmin.setText(supperAdmine.get(1));
        PassSupperAdmin.setSelection(PassSupperAdmin.getText().length());
        RetryPassSupperAdmin.setSelection(RetryPassSupperAdmin.getText().length());
        RetryPassSupperAdmin.setText(supperAdmine.get(1));
        RetryPassAdmin.setText(admine.get(0));
        RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
        if (ip!=null)
            Ip.setText(ip);

    }

    private void initialize() {
        UserAdmin=(EditText)findViewById(R.id.userAdmin);
        PassAdmin=(EditText)findViewById(R.id.passAdmin);
        Ip=(EditText)findViewById(R.id.ip);
        Ip.setSelection(Ip.getText().length());
        RetryPassAdmin=(EditText)findViewById(R.id.retryPassadmin);
        RetryPassSupperAdmin=(EditText)findViewById(R.id.retryPassSupperAdmin);
        UserSupperAdmin=(EditText)findViewById(R.id.UserSupperAdmin);
        PassSupperAdmin=(EditText)findViewById(R.id.passSupperAdmin);
        ChangeAdmin=(Button)findViewById(R.id.changeadmin);
        ChangeSupperAdmin=(Button)findViewById(R.id.chamgeSupperAdmin);
        SetIp=(Button)findViewById(R.id.setip);
        ShowPassAdmin=(CheckBox)findViewById(R.id.ShowPassAdmin);
        ShowPassSupper=(CheckBox)findViewById(R.id.ShowSupperPassAdmin);
        ErrorPassAdmin=(LinearLayout)findViewById(R.id.PassAdmin);
        ErrorUserAdmin=(LinearLayout)findViewById(R.id.UserAdmin);
        WrongPassAdmin=(LinearLayout)findViewById(R.id.WrongPassAdmin);
        WrongPassSupper=(LinearLayout)findViewById(R.id.WrongPassSupper);
        SupperUser=(LinearLayout)findViewById(R.id.SupperUser);
        PassSupper=(LinearLayout)findViewById(R.id.PassSupper);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        source.close();
    }
    public boolean check(EditText user,EditText pass,EditText retry,String choose){
        SupperUser.setVisibility(View.GONE);
        PassSupper.setVisibility(View.GONE);
        ErrorPassAdmin.setVisibility(View.GONE);
        WrongPassSupper.setVisibility(View.GONE);
        WrongPassAdmin.setVisibility(View.GONE);
        ErrorUserAdmin.setVisibility(View.GONE);
        if (user.getText().toString().equals("")){
            if (choose=="Supper")
                SupperUser.setVisibility(View.VISIBLE);
            else
               ErrorUserAdmin.setVisibility(View.VISIBLE);
         return false;
        }
        if (pass.getText().toString().equals("")) {
            if (choose == "Supper")
                PassSupper.setVisibility(View.VISIBLE);
            else
                ErrorPassAdmin.setVisibility(View.VISIBLE);
            return false;

        }
        if (pass.getText().toString().equals(retry.getText().toString()));
        else {
            if (choose == "Supper")
                WrongPassSupper.setVisibility(View.VISIBLE);
            else
                WrongPassAdmin.setVisibility(View.VISIBLE);
            return false;
        }

        return true;


    }
}
