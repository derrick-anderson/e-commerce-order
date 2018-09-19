package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "e-commerce-shipment-service", fallback=ShipmentFeignFallback.class)
@Component
public interface ShipmentFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/shipments/{shipmentId}")
    String getShipment(@PathVariable("shipmentId")Long shipmentId);
}

class ShipmentFeignFallback implements ShipmentFeignProxy{

    @Override
    public String getShipment(Long shipmentId){
        return "{ \"error\" : \"Service Unavailable, please try again later\"}";
    }
}