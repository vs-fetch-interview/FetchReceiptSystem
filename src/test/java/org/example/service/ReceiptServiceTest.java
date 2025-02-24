package org.example.service;
import org.example.model.Item;
import org.example.model.Receipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptServiceTest {

    private ReceiptService receiptService = new ReceiptService();

    @Test
    public void testCalculatePoints() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setTotal("35.35");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");

        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice("6.49");

        Item item2 = new Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice("12.25");

        receipt.setItems(Arrays.asList(item1, item2));

        int points = receiptService.calculatePoints(receipt);
        // Check that some points were calculated.
        assertTrue(points > 0, "Calculated points should be greater than 0");
    }

    @Test
    public void testProcessAndRetrieveReceipt() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Walmart");
        receipt.setTotal("100.00");
        receipt.setPurchaseDate("2022-02-02");
        receipt.setPurchaseTime("15:30");
        receipt.setItems(Arrays.asList());

        String id = receiptService.processReceipt(receipt);
        assertNotNull(id, "Receipt ID should not be null");

        Receipt storedReceipt = receiptService.getReceipt(id);
        assertNotNull(storedReceipt, "Stored receipt should not be null");
        assertEquals("Walmart", storedReceipt.getRetailer(), "Retailer should match");
    }

    @Test
    public void testFromGithub() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setTotal("35.35");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        Item item1 = new Item();
        item1.setPrice("6.49");
        item1.setShortDescription("Mountain Dew 12PK");
        Item item2 = new Item();
        item2.setPrice("12.25");
        item2.setShortDescription("Emils Cheese Pizza");
        Item item3 = new Item();
        item3.setPrice("1.26");
        item3.setShortDescription("Knorr Creamy Chicken");
        Item item4 = new Item();
        item4.setPrice("3.35");
        item4.setShortDescription("Doritos Nacho Cheese");
        Item item5 = new Item();
        item5.setPrice("12.00");
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        receipt.setItems(List.of(item1, item2, item3, item4, item5));
        int points = receiptService.calculatePoints(receipt);
        Assertions.assertEquals(points, 28);
    }
}
