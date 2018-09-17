package com.sostice.ecommerceorder.data;

import com.sostice.ecommerceorder.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    List<Line> findAllByOrderNumber(long orderNumber);
}
