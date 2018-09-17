package com.solstice.ecommerceorder.service;

import com.solstice.ecommerceorder.data.LineRepository;
import com.solstice.ecommerceorder.domain.Line;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class LineManagementServices {

    private LineRepository lineRepository;

    private LineManagementServices(LineRepository lineRepository){
        this.lineRepository = lineRepository;
    }

    public List<Line> getAllLinesForOrder(Long orderNumber) {
        return lineRepository.findAllByOrderNumber(orderNumber);
    }

    public Line getOneLineById(Long lineId) {
        return lineRepository.getOne(lineId);
    }

    public Line saveLine(Line lineToSave) {
        return lineRepository.save(lineToSave);
    }

    public void deleteLine(Long lineId) {

        if(lineRepository.getOne(lineId) != null){
            lineRepository.deleteById(lineId);
        }else throw new EntityNotFoundException();
    }

    public Line updateLine(Long lineId, Line lineToUpdate) {
        Line updatedLine = getOneLineById(lineId);

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
}
