package HttpConnection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jasmine on 4/8/2016.
 */
public class RequestPackage {
   private String uri;
   private String Method;
    private Map<String ,String > params=new HashMap<>();
    private ArrayList<String> paramsAuthorize=new ArrayList<String>();

    public Object [] getParamsAuthorize() {
        return paramsAuthorize.toArray();
    }

    public void setParamsAuthorize(ArrayList<String> paramsAuthorize) {
        this.paramsAuthorize = paramsAuthorize;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }
    public void setParamAuthorize(String key,ArrayList<String> value){
        paramsAuthorize=value;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public void setParam(String key,String value){
        params.put(key,value);
    }
    public String getParam(String key){
        return params.get(key);
    }
    public String getEncodeParams(){
        StringBuilder sb=new StringBuilder();
        for (String key:params.keySet()){
            String value=null;

            try {
                value= URLEncoder.encode(params.get(key),"UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            if (sb.length()>0){
//                sb.append("&");
//
//            }
            sb.append("/"+ value);
        }
        return sb.toString();
    }


}
