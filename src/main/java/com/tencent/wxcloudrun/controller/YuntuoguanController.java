package com.tencent.wxcloudrun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.Counselor;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserRelation;
import com.tencent.wxcloudrun.model.Visitor;
import com.tencent.wxcloudrun.service.*;
import com.tencent.wxcloudrun.utils.UserIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * counter控制器
 */
@RestController

public class YuntuoguanController {

  final CounselingService counselingService;
  final CounselorService counselorService;
  final OrderService orderService;
  final UserRelationService userRelationService;
  final UserService userService;
  final VisitorService visitorService;

  final Logger logger;
  String openIdStr = "X-WX-OPENID";

  public YuntuoguanController(@Autowired CounselingService counselingService
          , CounselorService counselorService, OrderService orderService, UserRelationService userRelationService,
                              UserService userService, VisitorService visitorService


  ) {
    this.counselingService = counselingService;
    this.counselorService = counselorService;
    this.orderService = orderService;
    this.userRelationService = userRelationService;
    this.userService = userService;
    this.visitorService = visitorService;
    this.logger = LoggerFactory.getLogger(YuntuoguanController.class);
  }



  /**
   * 获取咨询师信息
   *
   * @return API response json
   */
  @GetMapping(value = "/yuntuoguan/getCounselorInfo")
  ApiResponse getCounselorInfo(@RequestHeader HttpHeaders header, @RequestBody GetCounselorInfoRequest request)   {
    logger.info("/yuntuoguan/getCounselorInfo get request {}", request);
    Optional<Counselor> counselor = this.counselorService.getCounselorByCounselorId(request.getId());

    GetCounselorInfoResponse resp = new GetCounselorInfoResponse();
    try {
      resp.setInfo(counselor.get().ModelToDto());
    } catch (Exception e) {
      return ApiResponse.error("系统错误，请稍后再试");
    }
    return ApiResponse.ok(counselor);
  }

  @GetMapping(value = "/yuntuoguan/queryVisitorInfoList")
  ApiResponse queryVisitorInfoList(@RequestHeader HttpHeaders header) {
    //获取openID
    //openId获取用户id
    //若没有则插入
    //使用用户id查询来访者信息
    //返回
    logger.info("/yuntuoguan/queryVisitorInfoList get header {}", header);
    String openId = header.getFirst(openIdStr);
    Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(openId,NumOutIdType.ONE.getValue());
    long userId;
    if (!userRelation.isPresent()) {
      //存在
      userId = userRelation.get().getUserId();
    }
    else {
      User user = new User();
      userId = UserIdGenerator.generateUserId();
      user.setUserId(userId);
      user.setExtra("");
      this.userService.createUser(user);
    }
    List<Visitor> visitorModelList = this.visitorService.queryVisitorListByUserId(userId);
    List<com.tencent.wxcloudrun.dto.Visitor> visitorList = new ArrayList<>();

    for (Visitor visitor: visitorModelList) {
      visitorList.add(visitor.ModelToDto());
    }
    QueryVisitorInfoListResponse resp = new QueryVisitorInfoListResponse();
    resp.setInfo_list(visitorList);
    logger.info("/yuntuoguan/queryVisitorInfoList post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }




  /**
   * 增加咨询者
   *
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/addVisitorInfo")
  ApiResponse create(@RequestHeader HttpHeaders header, @RequestBody AddVisitorInfoRequest request) {
    logger.info("/yuntuoguan/queryVisitorInfoList get header {}", header);
    String openId = header.getFirst(openIdStr);
    Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(openId,NumOutIdType.ONE.getValue());
    long userId;
    if (!userRelation.isPresent()) {
      //存在
      userId = userRelation.get().getUserId();
    }
    else {
      User user = new User();
      userId = UserIdGenerator.generateUserId();
      user.setUserId(userId);
      user.setExtra("");
      this.userService.createUser(user);
    }
    Visitor visitor = request.getInfo().dtoToModel();
    visitor.setUserId(userId);
    String visitorId = "V" + UserIdGenerator.generateUserId();
    visitor.setVisitorId(visitorId);
    this.visitorService.createVisitor(visitor);
    AddVisitorInfoResponse resp = new AddVisitorInfoResponse();
    resp.setVisitor_id(visitorId);
    logger.info("/yuntuoguan/addVisitorInfo post response, response: {}", resp);
    return ApiResponse.ok(resp);

  }


  /**
   * 更新咨询者
   *
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/updateVisitorInfo")
  ApiResponse update(@RequestHeader HttpHeaders header, @RequestBody AddVisitorInfoRequest request) {
    logger.info("/yuntuoguan/updateVisitorInfo post request, request: {}", request);


    Visitor visitor = request.getInfo().dtoToModel();
    Optional<Visitor> visitorDto = this.visitorService.getVisitorByVisitorId(visitor.getVisitorId());
    if (!visitorDto.isPresent()){
      return ApiResponse.error("该访问者不存在，无法更新");
    }
    this.visitorService.updateVisitor(visitor);
    AddVisitorInfoResponse resp = new AddVisitorInfoResponse();
    resp.setVisitor_id(visitor.getVisitorId());
    logger.info("/yuntuoguan/addVisitorInfo post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }

  /**
   * 获取可预定时间
   *
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @GetMapping(value = "/yuntuoguan/queryAvailableTime")
  ApiResponse get(@RequestHeader HttpHeaders header, @RequestBody QueryAvailableTimeRequest request) {




    return ApiResponse.ok();
  }


  /**
   * 预定
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/createOrder")
  ApiResponse create(@RequestHeader HttpHeaders header, @RequestBody CreateOrderRequest request) {
    logger.info("/yuntuoguan/createOrder post request, request: {}", request);

    //获取openID
    //openId获取用户id
    //查看咨询师订单
    //查看咨询师咨询信息
    //根据规则构建可预订信息
    //创建订单
    //调用微信支付
    //返回相关信息

    return ApiResponse.ok();
  }







/**
 * 查询订单记录
 * @param request {@link CounterRequest}
 * @return API response json
 */
@GetMapping(value = "/yuntuoguan/queryOrderList")
ApiResponse get(@RequestHeader HttpHeaders header, @RequestBody QueryOrderListRequest request) {
  logger.info("/yuntuoguan/queryOrderList post request, request: {}", request);
  String openId = header.getFirst(openIdStr);
  Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(openId,NumOutIdType.ONE.getValue());
  long userId;
  if (!userRelation.isPresent()) {
    //存在
    userId = userRelation.get().getUserId();
  }
  else {
    User user = new User();
    userId = UserIdGenerator.generateUserId();
    user.setUserId(userId);
    user.setExtra("");
    this.userService.createUser(user);
  }
  List<com.tencent.wxcloudrun.model.Order> orderModelList = this.orderService.queryOrderListByUserId(userId, request.getCursor(), request.getCount());
  List<com.tencent.wxcloudrun.dto.Order> orderDtolList = new ArrayList<>();

  for (com.tencent.wxcloudrun.model.Order order: orderModelList) {
    orderDtolList.add(order.ModelToDto());
  }

  AddVisitorInfoResponse resp = new AddVisitorInfoResponse();


  logger.info("/yuntuoguan/queryOrderList post response, response: {}", resp);
  return ApiResponse.ok(resp);


}


  /**
   * 支付回调
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @GetMapping(value = "/yuntuoguan/wechatPaySuccessCallback")
  ApiResponse create(@RequestHeader HttpHeaders header, @RequestBody WechatPaymentCallbackRequest request) {
    logger.info("/yuntuoguan/queryOrderList post request, request: {}", request);

    //获取openID
    //openId获取用户id
    //查看咨询师订单

    return ApiResponse.ok();
  }



}