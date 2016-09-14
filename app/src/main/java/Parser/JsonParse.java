package Parser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.InfoBarcode;
import Model.InformationPerson;
import Model.InformationPhiwm;
import Model.InformationRow;
import Model.Location;
import Model.LoginModel;
import Model.LotCode;
import Model.PartCode;
import Model.ShipMentModel;
import Model.ShipmentAuthorize;

/**
 * Created by jasmine on 5/7/2016.
 */
public class JsonParse {
    public String FeedDataCheck(String content){
        JSONArray array= null;
        String result=null;
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                result=object.getString("Status");
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return   result;
    }
    public LoginModel FeedDataCheckPerson(String s){
        LoginModel loginModel =new LoginModel();
        try {
            JSONArray array=new JSONArray(s);
                JSONObject object=array.getJSONObject(0);
                loginModel.setName(object.getString("Title"));
                loginModel.setPhoto(object.getString("PersonnelImage"));
                loginModel.setCode(object.getString("Code"));
        }catch (JSONException e) {
            e.printStackTrace();
        }return loginModel;
    }

    public Bitmap byte2Bitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public List<InformationPhiwm> FeedDataPhiwm(String content){
        JSONArray array= null;
        List<InformationPhiwm> result=new ArrayList<>();
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                InformationPhiwm IPIM=new InformationPhiwm();
                JSONObject object = array.getJSONObject(i);
                IPIM.setTitle(object.getString("Title"));
                IPIM.setCode(object.getString("Code"));
                IPIM.setInventoryCode(object.getString("InventoryCode"));
                IPIM.setInventoryTitle(object.getString("InventoryTitle"));
                    result.add(IPIM);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return   result;
    }
    public List<ShipmentAuthorize> FeedDataAuthorize(String content){
        JSONArray array= null;
        List<ShipmentAuthorize> result=new ArrayList<>();
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                ShipmentAuthorize IPIM=new ShipmentAuthorize();
                JSONObject object = array.getJSONObject(i);
                IPIM.setTitle(object.getString("Title"));
                IPIM.setCode(object.getString("Code"));
                IPIM.setProductCode(object.getString("ProductCode"));
                IPIM.setProductTitle(object.getString("ProductTitle"));
                result.add(IPIM);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return   result;
    }
    public List<InfoBarcode> FeedDataBarcode(String content){
        JSONArray array= null;
        List<InfoBarcode> result=new ArrayList<>();
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                InfoBarcode IPIM=new InfoBarcode();
                JSONObject object = array.getJSONObject(i);
                IPIM.setBarcode(object.getString("PartSerialCode"));
               // IPIM.setAuthorize(object.getString("ShipmentAuthorizeCode"));
                result.add(IPIM);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return   result;
    }
    public List<PartCode> FeedDataNewPart(String content){
        JSONArray array= null;
        List<PartCode> result=new ArrayList<>();
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                PartCode partCode=new PartCode();
                JSONObject object = array.getJSONObject(i);
                partCode.setTitle(object.getString("Title"));
                partCode.setUOM(object.getString("UnitOfMeasureCode"));
                result.add(partCode);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return   result;
    }

    public List<InformationRow> FeedDataDetails(String content){
        List<InformationRow> informationRows = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(content);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                InformationRow informationRow=new InformationRow();
                informationRow.setPartCode(object.getString("PartCode"));
                informationRow.setPartTitle(object.getString("PartTitle"));
                informationRow.setUOM(object.getString("UOM"));
                informationRow.setQty(object.getString("QTY"));
                informationRow.setUOMTitle(object.getString("UOMTitle"));
                informationRow.setSequence(object.getString("Sequence"));
                informationRows.add(informationRow);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return  informationRows;
    }
    public List<Location> FeedDataLocaion(String location){
        List<Location> locations = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(location);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                Location location1=new Location();
                location1.setCode(object.getString("Code"));
                location1.setTitle(object.getString("Title"));
                locations.add(location1);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return locations;
    }
    public List<LotCode> FeedDataLotCode(String LotCode){
        List<Model.LotCode> lotCodes = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(LotCode);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                Model.LotCode lotCode=new LotCode();
                lotCode.setCode(object.getString("Code"));
                lotCode.setTitle(object.getString("Title"));
                lotCodes.add(lotCode);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return lotCodes;
    }
    public List<ShipMentModel> FeedDataShipMent(String resualt){
        List<ShipMentModel> shipMents = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(resualt);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                ShipMentModel lotCode=new ShipMentModel();
                lotCode.setCode(object.getString("Code"));
                lotCode.setTitle(object.getString("Title"));
                shipMents.add(lotCode);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return shipMents;
    }
    public List<InformationPerson> FeedDataPerson(String resualt){
        List<InformationPerson> shipMents = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(resualt);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                InformationPerson lotCode=new InformationPerson();
                lotCode.setIDNumber(object.getString("IDNumber"));
                lotCode.setDriverName(object.getString("DriverName"));
                lotCode.setCallPhoneNumber(object.getString("CellPhoneNumber"));
                lotCode.setBillOfLoadingNo(object.getString("BillOfLadingNO"));
                lotCode.setTruckWeight(object.getString("TruckWeight"));
                lotCode.setReceptionData(object.getString("ReceptionDate"));
                lotCode.setCarrier(object.getString("CarrierNumber"));
                shipMents.add(lotCode);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return shipMents;
    }
    public List<InfoBarcode> FeedMessage(String resualt){
        List<InfoBarcode> InfoBarcodes = new ArrayList<>();
        JSONArray array= null;
        try {
            array = new JSONArray(resualt);
            for (int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                InfoBarcode barcode=new InfoBarcode();
                barcode.setBarcode(object.getString("ProductSerialCode"));
                barcode.setMessage(object.getString("Message"));
                InfoBarcodes.add(barcode);
            }} catch (JSONException e) {
            e.printStackTrace();
        }
        return InfoBarcodes;
    }
    public String FeedFinishMessage(String resualt,String Tag){
       String InfoBarcodes = null;
        JSONArray array= null;
        try {
            array = new JSONArray(resualt);
            JSONObject object = array.getJSONObject(0);
            return object.getString(Tag);
           } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<String> FeedSerialCode(String content){
List<String> Serialcode=new ArrayList<>();
        String InfoBarcodes = null;
        JSONArray array= null;
        try {
            array = new JSONArray(content);
            JSONObject object = array.getJSONObject(0);
            String Message= object.getString("Message");
            Serialcode.add(Message);
            String PartCode=object.getString("PartCode");
            Serialcode.add(PartCode);
            return Serialcode;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
