package com.gome.threshold.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gome.threshold.domain.OrderState;
@Repository("orderStateDAO")
public interface OrderStateDAO {
	List<OrderState> searchOrderStateListByType(OrderState orderState);
}
