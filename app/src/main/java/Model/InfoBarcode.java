package Model;

import android.os.*;

/**
 * Created by jasmine on 6/29/2016.
 */
public class InfoBarcode {
    String Message;
    String barcode;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
