package Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jasmine on 6/29/2016.
 */
public class ShipmentAuthorize implements Parcelable {
    String Code;
    String Title;
    String ProductTitle;
    String ProductCode;

    public ShipmentAuthorize() {
    }

    protected ShipmentAuthorize(Parcel in) {
        Code = in.readString();
        Title = in.readString();
        ProductTitle = in.readString();
        ProductCode = in.readString();
    }

    public static final Creator<ShipmentAuthorize> CREATOR = new Creator<ShipmentAuthorize>() {
        @Override
        public ShipmentAuthorize createFromParcel(Parcel in) {
            return new ShipmentAuthorize(in);
        }

        @Override
        public ShipmentAuthorize[] newArray(int size) {
            return new ShipmentAuthorize[size];
        }
    };

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Code);
        dest.writeString(Title);
        dest.writeString(ProductTitle);
        dest.writeString(ProductCode);
    }
}
