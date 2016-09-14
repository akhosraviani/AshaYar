package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmine on 5/17/2016.
 */
public class DataSource {
    SQLiteOpenHelper ChSqLiteOpenHelper;
    SQLiteDatabase database;
    public DataSource(Context context){
        ChSqLiteOpenHelper=new CheckOpenHelper(context);
    }
    public void open(){
        database=ChSqLiteOpenHelper.getWritableDatabase();
    }
    public void close(){
        ChSqLiteOpenHelper.close();
    }
    public void insert(){
        ContentValues values=new ContentValues();
        values.put(CheckOpenHelper.COLUMN_PASS,"admin");
        values.put(CheckOpenHelper.COLUMN_USER,"admin");
        database.insert(CheckOpenHelper.TABLE_CHECK, CheckOpenHelper.COLUMN_IP, values);
    }
    public void supperInsert(){
        ContentValues values=new ContentValues();
        values.put(CheckOpenHelper.SUPPER_COLUMN_PASS,"Asha");
        values.put(CheckOpenHelper.SUPPER_COLUMN_USER,"1390");
        database.insert(CheckOpenHelper.TABLE_SUPPER_CHECK,null, values);
    }
    public String sellectIp(){
        String ip = null;
String columns[]={CheckOpenHelper.COLUMN_IP};
        Cursor cursor=database.query(CheckOpenHelper.TABLE_CHECK,columns,null,null,null,null,null);
//        database.rawQuery()
        if(cursor.getCount()>0)
            while(cursor.moveToNext())
            {
               ip=cursor.getString(cursor.getColumnIndex(CheckOpenHelper.COLUMN_IP));
            }
        return ip;
    }
    public List<String> sellectAll(){
        String id;
        Cursor cursor;
        List<String> result = new ArrayList<>();
        String columns[]={CheckOpenHelper.COLUMN_USER, CheckOpenHelper.COLUMN_PASS, CheckOpenHelper.COLUMN_ID};
       cursor =database.query(CheckOpenHelper.TABLE_CHECK,columns,null,null,null,null,null);
        if(cursor.getCount()>0)
            while(cursor.moveToNext())
            {
                String  pass=cursor.getString(cursor.getColumnIndex(CheckOpenHelper.COLUMN_PASS));
                try{
                result.add(pass);}catch (Exception e){
                    e.printStackTrace();
                }
               String user=cursor.getString(cursor.getColumnIndex(CheckOpenHelper.COLUMN_USER));
                result.add(user);
              id =cursor.getString(cursor.getColumnIndex(CheckOpenHelper.COLUMN_ID));
            }
        return result;
    }
    public List<String> supperSelectAll(){
        String id;
        List<String> result = new ArrayList<>();
        String columns[]={CheckOpenHelper.SUPPER_COLUMN_USER, CheckOpenHelper.SUPPER_COLUMN_PASS, CheckOpenHelper.SUPPER_COLUMN_ID};
        Cursor cursor=database.query(CheckOpenHelper.TABLE_SUPPER_CHECK,columns,null,null,null,null,null);
        if(cursor.getCount()>0)
            while(cursor.moveToNext())
            {
                String  pass=cursor.getString(cursor.getColumnIndex(CheckOpenHelper.SUPPER_COLUMN_PASS));
                try{
                    result.add(pass);}catch (Exception e){
                    e.printStackTrace();
                }
                String user=cursor.getString(cursor.getColumnIndex(CheckOpenHelper.SUPPER_COLUMN_USER));
                result.add(user);
                id =cursor.getString(cursor.getColumnIndex(CheckOpenHelper.SUPPER_COLUMN_ID));
            }
        return result;
    }
    public void UpdateIp(String ip){
        ContentValues values=new ContentValues();
        values.put(CheckOpenHelper.COLUMN_IP,ip);
        database.update(CheckOpenHelper.TABLE_CHECK,values, CheckOpenHelper.COLUMN_ID + "=?",new String[]{String.valueOf("1")});
    }
    public void UpdateUserPass(String pass,String user){
      ContentValues values=new ContentValues();
        values.put(CheckOpenHelper.COLUMN_PASS,pass);
        values.put(CheckOpenHelper.COLUMN_USER,user);
        database.update(CheckOpenHelper.TABLE_CHECK,values, CheckOpenHelper.COLUMN_ID + "=?",new String[]{String.valueOf("1")});
    }
    public void SupperUpdateUserPass(String pass,String user){
        ContentValues values=new ContentValues();
        values.put(CheckOpenHelper.SUPPER_COLUMN_PASS,pass);
        values.put(CheckOpenHelper.SUPPER_COLUMN_USER,user);
        database.update(CheckOpenHelper.TABLE_CHECK,values, CheckOpenHelper.COLUMN_ID + "=?",new String[]{String.valueOf("1")});
    }
}
