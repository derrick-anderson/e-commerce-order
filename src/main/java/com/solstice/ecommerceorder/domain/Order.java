package com.solstice.ecommerceorder.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "EOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;
    private Long accountId;
    private LocalDate orderDate = LocalDate.now();
    private Long shippingAddressId;

    @Transient
    private BigDecimal totalPrice;

    @Transient
    private List<Line> lineItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long accountId, Long shippingAddressId) {
        this.accountId = accountId;
        this.shippingAddressId = shippingAddressId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public BigDecimal getTotalPrice() {
        return lineItems.stream()
                    .map(Line::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Line> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<Line> lineItems) {
        this.lineItems = lineItems;
    }
}
