package com.tencent.wxcloudrun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.dto.Order;
import com.tencent.wxcloudrun.dto.Visitor;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.model.Counselor;
import com.tencent.wxcloudrun.service.*;
import com.tencent.wxcloudrun.utils.DateUtil;
import com.tencent.wxcloudrun.utils.UserIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    LocalDateTime now = LocalDateTime.now();
    if (!counselor.isPresent()) {
        return ApiResponse.error("该咨询师不存在");
    }

    Optional<Counseling> counseling= this.counselingService.getCounselingByCounselorId(counselor.get().getCounselorId(), now);
    if (!counseling.isPresent()) {
        return ApiResponse.error("该咨询师没有咨询信息");
    }
    GetCounselorInfoResponse resp = new GetCounselorInfoResponse();
    try {
      com.tencent.wxcloudrun.dto.Counselor counselorDto = counselor.get().ModelToDto();
      counselorDto.setFee(counseling.get().getFee());
      counselorDto.setDuration(counseling.get().getDuration());
      counselorDto.setFee(counseling.get().getFee());
      resp.setInfo(counselorDto);

    } catch (Exception e) {
      return ApiResponse.error("很抱歉，系统错误，请稍后再试");
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
    userId = getUserId(userRelation);
    List<com.tencent.wxcloudrun.model.Visitor> visitorModelList = this.visitorService.queryVisitorListByUserId(userId);
    List<com.tencent.wxcloudrun.dto.Visitor> visitorList = new ArrayList<>();

    for (com.tencent.wxcloudrun.model.Visitor visitor: visitorModelList) {
      visitorList.add(visitor.ModelToDto());
    }
    QueryVisitorInfoListResponse resp = new QueryVisitorInfoListResponse();
    resp.setInfo_list(visitorList);
    logger.info("/yuntuoguan/queryVisitorInfoList post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }

  private long getUserId(Optional<UserRelation> userRelation) {
    long userId;
    if (userRelation.isPresent()) {
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
    return userId;
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
    if (userRelation.isPresent()) {
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
    Visitor visitor = request.getInfo();
    com.tencent.wxcloudrun.model.Visitor visitorModel = visitor.dtoToModel();
    visitorModel.setUserId(userId);
    String visitorId = "V" + UserIdGenerator.generateUserId();
    visitorModel.setVisitorId(visitorId);
    this.visitorService.createVisitor(visitorModel);
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

    Visitor visitor = request.getInfo();
    com.tencent.wxcloudrun.model.Visitor visitorModel = visitor.dtoToModel();

    Optional<com.tencent.wxcloudrun.model.Visitor> visitorDto = this.visitorService.getVisitorByVisitorId(visitorModel.getVisitorId());
    if (!visitorDto.isPresent()){
      return ApiResponse.error("该访问者不存在，无法更新");
    }
    this.visitorService.updateVisitor(visitorModel);
    AddVisitorInfoResponse resp = new AddVisitorInfoResponse();
    resp.setVisitor_id(visitor.getVisitor_id());
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
  ApiResponse queryAvailableTime(@RequestHeader HttpHeaders header, @RequestBody QueryAvailableTimeRequest request) {
    logger.info("/yuntuoguan/queryAvailableTime get request, request: {}", request);
    QueryAvailableTimeResponse resp = new QueryAvailableTimeResponse();
    try {
      DayPeriod dayPeriod = DayPeriod.getAvailablePeriod(request.getStart_date(), request.getCounselor_id(), counselingService, orderService);
      HashMap<String, DayPeriod> calendar = new HashMap<>();
      calendar.put(dayPeriod.getDate(), dayPeriod);
      resp.setCalendar(calendar);
    } catch (Exception e) {
      return ApiResponse.error("很抱歉，系统错误，请稍后再试");
    }
    logger.info("/yuntuoguan/queryAvailableTime get response, response: {}", resp);
    return ApiResponse.ok(resp);
  }








/**
 * 查询订单记录
 * @param request {@link CounterRequest}
 * @return API response json
 */
@GetMapping(value = "/yuntuoguan/queryOrderList")
ApiResponse queryOrderList(@RequestHeader HttpHeaders header, @RequestBody QueryOrderListRequest request) {
  logger.info("/yuntuoguan/queryOrderList get request, request: {}", request);
  String openId = header.getFirst(openIdStr);
  Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(openId,NumOutIdType.ONE.getValue());
  long userId;
  if (userRelation.isPresent()) {
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
  List<com.tencent.wxcloudrun.model.Visitor> visitorList = this.visitorService.queryVisitorListByUserId(userId);
  Map<String, com.tencent.wxcloudrun.model.Visitor> map = new HashMap<>();
  for (com.tencent.wxcloudrun.model.Visitor entity : visitorList) {
    if (entity.getVisitorId() != null) {
      map.put(entity.getVisitorId(), entity);
    }
  }
  List<com.tencent.wxcloudrun.dto.Order> orderDtolList = new ArrayList<>();

  for (com.tencent.wxcloudrun.model.Order order: orderModelList) {
    Order orderDto = new Order();
    orderDto.setOrder_base(order.ModelToOrderBase());
    orderDto.setCounselor_id(order.getCounselorId());
    orderDto.setVisitor(map.get(order.getVisitorId()).getName());
    orderDtolList.add(orderDto);

  }
  //获取访问者信息

  QueryOrderListResponse resp = new QueryOrderListResponse();
  resp.setOrder_list(orderDtolList);
  resp.setNext_cursor(orderDtolList.get(orderDtolList.size()-1).getOrder_base().getPeriod_key());
  logger.info("/yuntuoguan/queryOrderList get response, response: {}", resp);
  return ApiResponse.ok(resp);


}





  /**
   * 预定
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/createOrder")
  ApiResponse createOrder(@RequestHeader HttpHeaders header, @RequestBody CreateOrderRequest request) {
    logger.info("/yuntuoguan/createOrder post request, request: {}", request);
    //获取openID
    //openId获取用户id
    CreateOrderResponse resp = new CreateOrderResponse();
    String openId = header.getFirst(openIdStr);
    Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(openId,NumOutIdType.ONE.getValue());
    long userId = getUserId(userRelation);
    Optional<com.tencent.wxcloudrun.model.Order> orderDto = this.orderService.queryOrderByCounselorIdAndPeriodKey(request.getCounselor_id(), request.getUnit_period_key());
    if (orderDto.isPresent()){
      return ApiResponse.error("很抱歉，该预定时间已被预定，请重新预定");
    }
    Optional<Counseling> counseling = counselingService.getCounselingByCounselorId(request.getCounselor_id(), DateUtil.dateToTime(request.getUnit_period_key().split("_")[0]));

    //创建订单
    com.tencent.wxcloudrun.model.Order order = new com.tencent.wxcloudrun.model.Order();
    order.setCounselorId(request.getCounselor_id());
    order.setVisitorId(request.getVisitor_id());
    order.setUnitPeriodKey(request.getUnit_period_key());
    String orderId = "O" + UserIdGenerator.generateUserId();
    order.setOrderId(orderId);
    order.setUserId(userId);
    order.setStatus(1);
    order.setCreateTime(LocalDateTime.now());
    order.setUpdateTime(LocalDateTime.now());
    order.setWay(counseling.get().getWay());
    order.setFee(counseling.get().getFee());
    order.setBizDate(LocalDateTime.parse(request.getUnit_period_key().split("_")[0] + DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    try {
      this.orderService.createOrder(order);
    } catch (Exception e) {
      return ApiResponse.error("很抱歉，创建订单失败，请稍后再试");
    }
    resp.setOrder_id(order.getOrderId());
    //调用微信支付
    //http调用微信支付
    //返回支付信息
    //返回订单信息

    logger.info("/yuntuoguan/createOrder post response, response: {}", resp);






    //返回相关信息
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