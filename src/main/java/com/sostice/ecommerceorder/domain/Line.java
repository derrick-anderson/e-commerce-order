package com.sostice.ecommerceorder.domain;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Line {

    private BigDecimal totalPrice = BigDecimal.ZERO;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
