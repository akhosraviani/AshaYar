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
public class Change extends AppCompatActivity {
    DataSource source;
    List<String> admine;
    EditText UserAdmin,PassAdmin,Ip,RetryPassAdmin;
    Button ChangeAdmin,SetIp;
    CheckBox ShowPassAdmin;
    String ip;
    Toolbar toolbar;
    boolean flag=true;
    LinearLayout errorAdminPass,WrongPassAdmin,ErrorUserAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        source=new DataSource(Change.this);
        source.open();
        admine=source.sellectAll();
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
                if (check(UserAdmin, PassAdmin, RetryPassAdmin)){
                    source.UpdateUserPass(PassAdmin.getText().toString(), UserAdmin.getText().toString());
                    Toast.makeText(Change.this,"تغیرات شما با موفقیت انجام شد",Toast.LENGTH_SHORT).show();
                }
            }
        });
        SetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.UpdateIp(Ip.getText().toString());
                Toast.makeText(Change.this,"ادرس IP شما با موفقیت تنظیم شد",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void check() {
        ShowPassAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    PassAdmin.setTransformationMethod(null);
                    RetryPassAdmin.setTransformationMethod(null);
                    PassAdmin.setSelection(PassAdmin.getText().length());
                    RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
                    flag = false;
                } else {
                    PassAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    RetryPassAdmin.setTransformationMethod(new PasswordTransformationMethod());
                    PassAdmin.setSelection(PassAdmin.getText().length());
                    RetryPassAdmin.setSelection(RetryPassAdmin.getText().length());
                    flag = true;
                }
            }
        });
    }

    private void firstInitialize() {
        UserAdmin.setFocusable(true);
        UserAdmin.setText(admine.get(1));
        UserAdmin.setSelection(UserAdmin.getText().length());
        PassAdmin.setText(admine.get(0));
        PassAdmin.setSelection(PassAdmin.getText().length());
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
        ChangeAdmin=(Button)findViewById(R.id.changeadmin);
        SetIp=(Button)findViewById(R.id.setip);
        ShowPassAdmin=(CheckBox)findViewById(R.id.ShowPassAdmin);
        errorAdminPass=(LinearLayout)findViewById(R.id.errorAdminPass);
        WrongPassAdmin=(LinearLayout)findViewById(R.id.WrongPassAdmin);
        ErrorUserAdmin=(LinearLayout)findViewById(R.id.ErrorUserAdmin);
    }

    @Override
    protected void onStop() {
        super.onStop();
        source.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public boolean check(EditText user,EditText pass,EditText retry){
        errorAdminPass.setVisibility(View.GONE);
        WrongPassAdmin.setVisibility(View.GONE);
        ErrorUserAdmin.setVisibility(View.GONE);
        if (user.getText().toString().equals("")){
                ErrorUserAdmin.setVisibility(View.VISIBLE);
            return false;
        }
        if (pass.getText().toString().equals("")) {
            errorAdminPass.setVisibility(View.VISIBLE);
            return false;

        }
        if (pass.getText().toString().equals(retry.getText().toString()));
        else {
                WrongPassAdmin.setVisibility(View.VISIBLE);
            return false;
        }

        return true;


    }
}
