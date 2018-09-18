package com.solstice.ecommerceorder.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("e-commerce-shipment-service")
public interface ShipmentFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/shipments/{shipmentId}")
    String getShipment(@PathVariable("shipmentId")Long shipmentId);
}
