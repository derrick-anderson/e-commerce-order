package com.solstice.ecommerceorder.service;

import com.solstice.ecommerceorder.data.LineRepository;
import com.solstice.ecommerceorder.data.ProductFeignProxy;
import com.solstice.ecommerceorder.data.ShipmentFeignProxy;
import com.solstice.ecommerceorder.domain.Line;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LineManagementServices {

    private LineRepository lineRepository;

    private ShipmentFeignProxy shipmentFeignProxy;

    private ProductFeignProxy productFeignProxy;

    private LineManagementServices(LineRepository lineRepository, ShipmentFeignProxy shipmentFeignProxy, ProductFeignProxy productFeignProxy){
        this.lineRepository = lineRepository;
        this.shipmentFeignProxy = shipmentFeignProxy;
        this.productFeignProxy = productFeignProxy;
    }

    public List<Line> getAllLinesForOrder(Long orderNumber) {
        return lineRepository.findAllByOrderNumber(orderNumber);
    }

    public Line getOneLineById(Long lineId) {

        return addProduct(lineRepository.findById(lineId).get());
    }

    public Line saveLine(Line lineToSave) {
        return lineRepository.save(lineToSave);
    }

    public Line addLineToOrder(Long orderNumber, Line lineToSave){
        return null;
    }

    public void deleteLine(Long lineId) {

        if(lineRepository.getOne(lineId) != null){
            lineRepository.deleteById(lineId);
        }else throw new EntityNotFoundException();
    }

    public Line updateLine(Long lineId, Line lineToUpdate) {
        Line updatedLine = lineRepository.findById(lineId).get();

        if (updatedLine != null) {
            if (! lineToUpdate.getUnitPrice().equals(BigDecimal.ZERO)) {
                updatedLine.setUnitPrice(lineToUpdate.getUnitPrice());
            }
            if (lineToUpdate.getQuantity() != 0) {
                updatedLine.setQuantity(lineToUpdate.getQuantity());
            }
            if (lineToUpdate.getProductId() != null) {
                updatedLine.setProductId(lineToUpdate.getProductId());
            }
            if (lineToUpdate.getShipmentId() != null) {
                updatedLine.setShipmentId(lineToUpdate.getShipmentId());
            }
            lineRepository.save(updatedLine);
            return updatedLine;
        } else throw new EntityNotFoundException();
    }

    public String getAllShipmentsForLines(List<Line> linesIn) {
        return "[" + linesIn.stream()
                .map(Line::getShipmentId)
                .filter(Objects::nonNull)
                .map(a -> shipmentFeignProxy.getShipment(a))
                .collect(Collectors.joining(", ")) + "]";
    }

    public List<Line> getAllLinesForShipment(Long shipmentId){
        return addProducts(lineRepository.findAllByShipmentId(shipmentId));
    }

    private Line addProduct(Line lineIn){
        if(lineIn.getProductId() != null) {
            lineIn.setProduct(productFeignProxy.getProduct(lineIn.getProductId()));
        }
        return lineIn;
    }

    private List<Line> addProducts(List<Line> linesIn){
        linesIn.forEach(
                a -> a.setProduct(productFeignProxy.getProduct(a.getProductId()))
                );
        return linesIn;

    }
}
