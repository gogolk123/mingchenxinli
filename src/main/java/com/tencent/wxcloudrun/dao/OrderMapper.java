package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface OrderMapper {

    Order getVisitorByOrderId(@Param("orderId") String orderId);

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(@Param("orderId") String orderId);

    List<Order> queryOrderListByUserId(@Param("userId") long userId, @Param("cursor") String cursor,@Param("count") Integer count );
    List<Order> queryOrderListByCounselorId(@Param("counselorId") String counselorId);

    void updateOrderStatus(@Param("orderId") String orderId, Integer status);
}