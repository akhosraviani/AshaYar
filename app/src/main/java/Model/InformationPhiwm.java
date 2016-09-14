package Model;

/**
 * Created by jasmine on 5/1/2016.
 */
public class InformationPhiwm {
    String code;
String InventoryCode;
String title;
String InventoryTitle;

    public String getInventoryTitle() {
        return InventoryTitle;
    }

    public void setInventoryTitle(String inventoryTitle) {
        InventoryTitle = inventoryTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInventoryCode() {
        return InventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        InventoryCode = inventoryCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
