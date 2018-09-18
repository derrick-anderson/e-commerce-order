package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "e-commerce-account")
@Component
public interface AccountFeignProxy {

    @GetMapping("/accounts/{accountId}")
    String getAccount(@PathVariable(name = "accountId") Long accountId);

    @GetMapping("/accounts/{accountId}/addresses/{addressId}")
    String getAddress(@PathVariable(name = "accountId") Long accountId, @PathVariable(name = "addressId") Long addressId);
}
