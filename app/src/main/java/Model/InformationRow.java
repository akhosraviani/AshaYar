package Model;

/**
 * Created by jasmine on 4/21/2016.
 */
public class InformationRow {
    String PartCode,PartTitle,Qty,UOM,Sequence,UOMTitle;

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }

    public String getPartCode() {
        return PartCode;
    }

    public void setPartCode(String partCode) {
        PartCode = partCode;
    }

    public String getPartTitle() {
        return PartTitle;
    }

    public void setPartTitle(String partTitle) {
        PartTitle = partTitle;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }
}
