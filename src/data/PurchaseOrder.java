package data;

import java.util.Date;

public class PurchaseOrder extends BaseItem {
    private String PurchaseRequisitionId;
    private String supplierId;
    private String purchaseManagerId;
    private Status poStatus;
    private Date createdDate;
    private double totalAmount;

    // Constructor
    public PurchaseOrder(String PurchaseRequisitionId, String supplierId, String purchaseManagerId, Status poStatus,
            Date createdDate, double totalAmount) {
        this.PurchaseRequisitionId = PurchaseRequisitionId;
        this.supplierId = supplierId;
        this.purchaseManagerId = purchaseManagerId;
        this.poStatus = poStatus;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
    }

    public PurchaseOrder(String PurchaseRequisitionId, String supplierId, String purchaseManagerId, Status poStatus,
            Date createdDate, double totalAmount, String Id) {
        this.PurchaseRequisitionId = PurchaseRequisitionId;
        this.supplierId = supplierId;
        this.purchaseManagerId = purchaseManagerId;
        this.poStatus = poStatus;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.Id = Id;
    }

    // Getters and Setters
    public String getPurchaseRequisitionId() {
        return PurchaseRequisitionId;
    }

    public void setPurchaseRequisitionId(String PurchaseRequisitionId) {
        this.PurchaseRequisitionId = PurchaseRequisitionId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPurchaseManagerId() {
        return purchaseManagerId;
    }

    public void setPurchaseManagerId(String purchaseManagerId) {
        this.purchaseManagerId = purchaseManagerId;
    }

    public Status getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(Status poStatus) {
        this.poStatus = poStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public void printLog(){
        System.out.println("Purchase order loaded.");
    }
}
