package org.example.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class ReceiptRequest {

    @NotBlank(message = "Retailer is required.")
    private String retailer;

    @NotBlank(message = "Purchase date is required.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Purchase date must be in the format yyyy-MM-dd")
    private String purchaseDate;

    @NotBlank(message = "Purchase time is required.")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Purchase time must be in the format HH:mm")
    private String purchaseTime;

    @NotEmpty(message = "At least one item is required.")
    @Valid
    private List<ReceiptItemRequest> items;

    @NotBlank(message = "Total is required.")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Total must be a valid monetary amount.")
    private String total;

    // Getters and setters
    public String getRetailer() {
        return retailer;
    }
    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }
    public String getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public String getPurchaseTime() {
        return purchaseTime;
    }
    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public List<ReceiptItemRequest> getItems() {
        return items;
    }
    public void setItems(List<ReceiptItemRequest> items) {
        this.items = items;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
}
