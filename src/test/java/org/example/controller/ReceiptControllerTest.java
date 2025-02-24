package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.ReceiptItemRequest;
import org.example.domain.ReceiptRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testProcessReceiptEndpoint() throws Exception {
        // Build a valid ReceiptRequest
        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setRetailer("TestRetailer");
        receiptRequest.setTotal("10.00");
        receiptRequest.setPurchaseDate("2022-03-03");
        receiptRequest.setPurchaseTime("10:10");

        ReceiptItemRequest item = new ReceiptItemRequest();
        item.setShortDescription("Test Item");
        item.setPrice("5.00");

        receiptRequest.setItems(Arrays.asList(item));

        // Perform the POST request and verify that a response with an "id" is returned
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receiptRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testGetReceiptPointsEndpoint() throws Exception {
        // First, process a receipt to get its ID.
        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setRetailer("TestStore");
        receiptRequest.setTotal("20.00");
        receiptRequest.setPurchaseDate("2022-04-04");
        receiptRequest.setPurchaseTime("14:00");

        ReceiptItemRequest item = new ReceiptItemRequest();
        item.setShortDescription("Test Item");
        item.setPrice("5.00");

        receiptRequest.setItems(Arrays.asList(item));

        String response = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receiptRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Extract the generated receipt ID from the response
        String id = objectMapper.readTree(response).get("id").asText();

        // Retrieve the receipt points using the ID and verify the "points" field exists
        mockMvc.perform(get("/receipts/" + id + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists());
    }
}
