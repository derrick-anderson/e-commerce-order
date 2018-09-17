package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "e-commerce-account")
@Component
public interface AccountFeignProxy {

    @GetMapping("/accounts/{accountId}")
    String checkAccountExists(@PathVariable(name = "accountId") Long accountId);

}
