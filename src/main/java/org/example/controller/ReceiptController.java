package org.example.controller;

import org.example.domain.ProcessReceiptResponse;
import org.example.domain.ReceiptItemRequest;
import org.example.domain.ReceiptPointsResponse;
import org.example.domain.ReceiptRequest;
import org.example.model.Item;
import org.example.model.Receipt;
import org.example.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    /**
     * POST endpoint to process a receipt.
     * Accepts a JSON body mapped to a ReceiptRequest with validation.
     */
    @PostMapping("/receipts/process")
    public ResponseEntity<ProcessReceiptResponse> processReceipt(@Valid @RequestBody ReceiptRequest receiptRequest) {
        Receipt receipt = mapReceiptRequestToReceipt(receiptRequest);
        String id = receiptService.processReceipt(receipt);
        ProcessReceiptResponse response = new ProcessReceiptResponse(id);
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint to retrieve points for a processed receipt.
     */
    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<ReceiptPointsResponse> getReceiptPoints(@PathVariable String id) {
        Receipt receipt = receiptService.getReceipt(id);
        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }
        int points = receiptService.calculatePoints(receipt);
        ReceiptPointsResponse response = new ReceiptPointsResponse(points);
        return ResponseEntity.ok(response);
    }

    /**
     * Maps a validated ReceiptRequest into a Receipt model.
     */
    private Receipt mapReceiptRequestToReceipt(ReceiptRequest request) {
        Receipt receipt = new Receipt();
        receipt.setRetailer(request.getRetailer());
        receipt.setPurchaseDate(request.getPurchaseDate());
        receipt.setPurchaseTime(request.getPurchaseTime());
        receipt.setTotal(request.getTotal());
        List<Item> items = request.getItems().stream().map(this::mapItemRequestToItem).collect(Collectors.toList());
        receipt.setItems(items);
        return receipt;
    }

    /**
     * Maps a ReceiptItemRequest into an Item model.
     */
    private Item mapItemRequestToItem(ReceiptItemRequest itemRequest) {
        Item item = new Item();
        item.setShortDescription(itemRequest.getShortDescription());
        item.setPrice(itemRequest.getPrice());
        return item;
    }
}
