package com.sostice.ecommerceorder.service;

import com.sostice.ecommerceorder.data.LineRepository;
import com.sostice.ecommerceorder.domain.Line;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineManagementServices {

    private LineRepository lineRepository;

    private LineManagementServices(LineRepository lineRepository){
        this.lineRepository = lineRepository;
    }

    public List<Line> getAllLinesForOrder(Long orderNumber) {
        return lineRepository.findAllByOrderId(orderNumber);
    }

    public Line getOneLineById(Long lineId) {
        return lineRepository.getOne(lineId);
    }

    public Line saveLine(Line lineToSave) {
        return lineRepository.save(lineToSave);
    }
}