package org.example.domain;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ReceiptItemRequest {

    @NotBlank(message = "Item shortDescription is required.")
    private String shortDescription;

    @NotBlank(message = "Item price is required.")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Price must be a valid monetary amount.")
    private String price;

    // Getters and setters
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
