package org.example.domain;

public class ReceiptPointsResponse {
    private int points;

    public ReceiptPointsResponse() {
    }

    public ReceiptPointsResponse(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
