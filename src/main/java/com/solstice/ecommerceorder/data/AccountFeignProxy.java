package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "e-commerce-account-service", fallback=AccountFeignFallback.class)
public interface AccountFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountId}")
    String getAccount(@PathVariable(value = "accountId") Long accountId);

    @GetMapping("/accounts/{accountId}/addresses/{addressId}")
    String getAddress(@PathVariable("accountId") Long accountId, @PathVariable(name = "addressId") Long addressId);
}

class AccountFeignFallback implements AccountFeignProxy{

    @Override
    public String getAccount(Long accountId){
        return "{ \"error\" : \"Service Unavailable, please try again later\"}";
    }

    @Override
    public String getAddress(Long accountId, Long addressId){
        return "{ \"error\" : \"Service Unavailable, please try again later\"}";
    }
}