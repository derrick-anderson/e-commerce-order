package com.solstice.ecommerceorder.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemId;
    private int quantity;
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Transient
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private Long shipmentId;
    private Long productId;
    private Long orderNumber;

    public Line() {
    }

    public Line(int quantity, BigDecimal unitPrice, Long productId, Long orderNumber) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productId = productId;
        this.orderNumber = orderNumber;
    }

    public Long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(Long lineItemId) {
        this.lineItemId = lineItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}
