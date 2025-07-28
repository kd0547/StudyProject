package com.example.boardexample.category;

public enum OrderStatus {
    PREPARING("Preparing"),               // 상품 준비중
    SHIPPING_PENDING("Shipping Pending"), // 상품 배송 대기
    SHIPPING("Shipping"),                 // 상품 배송중
    DELIVERED("Delivered"),               // 상품 배송 완료
    REFUND("Refund");                     // 환불

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
