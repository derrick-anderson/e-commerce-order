package com.solstice.ecommerceorder.data;

import com.solstice.ecommerceorder.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    List<Line> findAllByOrderNumber(Long orderNumber);

    List<Line> findAllByShipmentId(Long shipmentId);
}
