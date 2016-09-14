package HttpConnection;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import Model.Response;

public class HttpUrlConnection {
    static public Response getData(RequestPackage p,String type) {
        int connection = 0;
        Response response = new Response();
        BufferedReader reader = null;
        String uri = p.getUri();
        StringBuilder sb = null;
        if (type=="Serial"){
            uri=uri+p.getEncodeParams()+"/"+"PartCodes";
        }

        if (type=="details"||type=="SerialDetails"){
            if (type=="details")
            uri=uri+p.getEncodeParams()+"/" +"Details";
            else
            if (type=="SerialDetails"){
                uri=uri+p.getEncodeParams()+"/"+"SerialDetails";
            }
        }
        else {
        if (p.getMethod().equals("GET")) {
            uri = uri + p.getEncodeParams();
        }}
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());
            JSONObject json = new JSONObject(p.getParams());
            if (p.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(json.toString());
                writer.flush();
            }
            sb = new StringBuilder();
          connection=con.getResponseCode();

            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            response.setContent(sb.toString());
            response.setResponse(connection);


        } catch (IOException e) {
            e.printStackTrace();
            response.setContent(sb.toString());
            response.setResponse(connection);
            return response;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return response;

    }
@TargetApi(Build.VERSION_CODES.KITKAT)
public Response sendData(RequestPackage p,String phiwm,String REQUEST,String Authorize) throws JSONException {
    Response response=new Response();
    int code = 0;
    JSONObject json = null;
    JSONArray Array;
    BufferedReader reader = null;
    String uri = p.getUri();
    StringBuilder sb = null;
if (REQUEST.equals("DataEntry")){
        uri = uri + "/"+ phiwm+"/" +p.getParam("Sequence");}
    else {
    uri=uri+"/"+phiwm;
}
        //  HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // con.setRequestMethod("PUT");
//    if (REQUEST.equals("Authorize")){
//        json=new JSONObject();
//        try {
//            Object[] tttt = p.getParamsAuthorize();
//            Array=new JSONArray(Arrays.asList(tttt));
//            json.put("PartSerialCode",Array);
//        }catch (Exception e){
//            e.getMessage();
//        }
//
// //       json.put("ShipmentAuthorizeCode",Authorize);
//    }else{
//
//        json= new JSONObject(p.getParams());}

        json= new JSONObject(p.getParams());
        try {
            String output;
            String input = json.toString();
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (p.getMethod()=="PUT"){
            connection.setRequestMethod("PUT");}
            else
                connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Accept",
                    "application/json");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(input.getBytes("UTF_8").length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
                wr.writeBytes(input);
                wr.flush();
            sb = new StringBuilder();
            code=connection.getResponseCode();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            response.setContent(sb.toString());
            response.setResponse(code);

            wr.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContent(sb.toString());
            response.setResponse(code);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return response;

    }
    public Response DeleteDat(RequestPackage p,String ShipmentCode,String SerialCode){
        int code = 0;
        BufferedReader reader = null;
        StringBuilder sb = null;
Response response=new Response();
        String uri=p.getUri()+"/"+ShipmentCode+"/"+SerialCode;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept",
                    "application/json");
            connection.setRequestProperty("Content-Language", "en-US");
//            connection.setUseCaches(false);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream ());
//            sb = new StringBuilder();
            code=connection.getResponseCode();
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            response.setContent(sb.toString());
            response.setResponse(code);
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContent(sb.toString());
            response.setResponse(code);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
return response;
    }
}
