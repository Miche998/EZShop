package it.polito.ezshop.model;

public class ProductImpl {

    private Long RFID;
    private String barCode;

    public ProductImpl(Long RFID, String barCode) {
        this.RFID = RFID;
        this.barCode = barCode;
    }

    public Long getRFID() {
        return RFID;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setRFID(Long RFID) {
        this.RFID = RFID;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
