package Model;

/**
 * Created by jasmine on 6/22/2016.
 */
public class InformationPerson {
   String IDNumber,DriverName,CallPhoneNumber,BillOfLoadingNo,TruckWeight,ReceptionData,Carrier;

    public String getCarrier() {
        return Carrier;
    }

    public void setCarrier(String carrier) {
        Carrier = carrier;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getCallPhoneNumber() {
        return CallPhoneNumber;
    }

    public void setCallPhoneNumber(String callPhoneNumber) {
        CallPhoneNumber = callPhoneNumber;
    }

    public String getBillOfLoadingNo() {
        return BillOfLoadingNo;
    }

    public void setBillOfLoadingNo(String billOfLoadingNo) {
        BillOfLoadingNo = billOfLoadingNo;
    }

    public String getTruckWeight() {
        return TruckWeight;
    }

    public void setTruckWeight(String truckWeight) {
        TruckWeight = truckWeight;
    }

    public String getReceptionData() {
        return ReceptionData;
    }

    public void setReceptionData(String receptionData) {
        ReceptionData = receptionData;
    }
}
