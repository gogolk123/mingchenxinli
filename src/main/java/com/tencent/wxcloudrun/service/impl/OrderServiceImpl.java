package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.OrderMapper;
import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;

  @Autowired
  public OrderServiceImpl(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  public Optional<Order> getOrderByOrderId(String orderId) {
    return Optional.ofNullable(orderMapper.getVisitorByOrderId(orderId));
  }

  @Override
  public void createOrder(Order order) {
    orderMapper.createOrder(order);
  }

  @Override
  public void updateOrder(Order order) {
    orderMapper.updateOrder(order);
  }

  @Override
  public void updateOrderStatus(String orderId, Integer status) {
    orderMapper.updateOrderStatus(orderId, status);
  }

  @Override
  public void deleteOrder(String orderId) {
    orderMapper.deleteOrder(orderId);
  }

  @Override
  public List<Order> queryOrderListByUserId(BigInteger userId) {
    return orderMapper.queryOrderListByUserId(userId);
  }

  @Override
  public List<Order> queryOrderListByCounselorId(String counselorId) {
    return orderMapper.queryOrderListByCounselorId(counselorId);
  }
}