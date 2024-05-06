package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionException;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

  /*
  用户Id查询订单,一般用于访客视角
   */
  List<Order> queryOrderListByUserId(long userId, String cursor, Integer count);
  /*
  咨询师查询订单
   */
  List<Order> queryOrderListByCounselorId(String counselorId, LocalDateTime start, LocalDateTime end);
  Optional<Order> queryOrderByCounselorIdAndPeriodKey( String counselorId, String unitPeriodKey);

  Optional<Order> getOrderByOrderId(String orderId);
  void createOrder(Order order) throws SqlSessionException;
  void updateOrderStatus(String orderId, Integer status);
  void updateOrder(Order order);
  void deleteOrder(String orderId);
}
