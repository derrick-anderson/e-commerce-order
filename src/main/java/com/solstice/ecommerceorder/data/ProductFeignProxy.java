package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("e-commerce-product-service")
public interface ProductFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    String getProduct(@PathVariable("id") Long productId);
}
