package com.tencent.wxcloudrun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.WxApiResponse;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.dto.Order;
import com.tencent.wxcloudrun.dto.Visitor;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.model.Counselor;
import com.tencent.wxcloudrun.service.*;
import com.tencent.wxcloudrun.utils.DateUtil;
import com.tencent.wxcloudrun.utils.OkHttpClient;
import com.tencent.wxcloudrun.utils.UserIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
  String ipStr = "X-Forwarded-For";
  String systemErrorMsg = "抱歉，系统打瞌睡了，请稍后再试一试或联系咨询师";

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
  ApiResponse getCounselorInfo(@RequestHeader HttpHeaders header, @ModelAttribute GetCounselorInfoRequest request)   {
    logger.info("/yuntuoguan/getCounselorInfo get request {}", request);
    GetCounselorInfoResponse resp = new GetCounselorInfoResponse();
    try {
      Optional<Counselor> counselor = this.counselorService.getCounselorByCounselorId(request.getId());
    LocalDateTime now = LocalDateTime.now();
    if (!counselor.isPresent()) {
        return ApiResponse.error("该咨询师不存在");
    }
    Optional<Counseling> counseling= this.counselingService.getCounselingByCounselorId(counselor.get().getCounselorId(), now);
    if (!counseling.isPresent()) {
        return ApiResponse.error("该咨询师没有咨询信息");
    }
      com.tencent.wxcloudrun.dto.Counselor counselorDto = counselor.get().ModelToDto();
      counselorDto.setFee(counseling.get().getFee());
      counselorDto.setDuration(counseling.get().getDuration());
      counselorDto.setFee(counseling.get().getFee());
      List<CounselingWay> ways = new ArrayList<>();
      CounselingWay way = new CounselingWay();
      way.setWay(counseling.get().getWay());
      way.setWay_str("线上视频");
      ways.add(way);
      counselorDto.setWay_list(ways);
      resp.setInfo(counselorDto);
    } catch (Exception e) {
      logger.error("/yuntuoguan/getCounselorInfo post fail,err:{}",e.getMessage());
      return ApiResponse.error(systemErrorMsg);
    }
    logger.info("/yuntuoguan/getCounselorInfo post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }

  @GetMapping(value = "/yuntuoguan/queryVisitorInfoList")
  ApiResponse queryVisitorInfoList(@RequestHeader HttpHeaders header) {
    //获取openID
    //openId获取用户id
    //若没有则插入
    //使用用户id查询来访者信息
    //返回
    logger.info("/yuntuoguan/queryVisitorInfoList get header {}", header);
    QueryVisitorInfoListResponse resp = new QueryVisitorInfoListResponse();

    String openId = header.getFirst(openIdStr);
    try {
      long userId;
      userId = getUserId(openId,NumOutIdType.ONE);
      List<com.tencent.wxcloudrun.model.Visitor> visitorModelList = this.visitorService.queryVisitorListByUserId(userId);
      List<com.tencent.wxcloudrun.dto.Visitor> visitorList = new ArrayList<>();

      for (com.tencent.wxcloudrun.model.Visitor visitor : visitorModelList) {
        visitorList.add(visitor.ModelToDto());
      }
      resp.setInfo_list(visitorList);
    } catch (Exception e) {
      logger.error("/yuntuoguan/queryVisitorInfoList post fail,err:{}",e.getMessage());
      return ApiResponse.error(systemErrorMsg);
    }
    logger.info("/yuntuoguan/queryVisitorInfoList post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }

  private long getUserId(String outId, NumOutIdType outIdType) {
    Optional<UserRelation> userRelation = this.userRelationService.getUserRelationByOutId(outId, outIdType.getValue());
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
      UserRelation newRelation = new UserRelation();
      newRelation.setRelationId("R" + UserIdGenerator.generateUserId());
      newRelation.setUserId(userId);
      newRelation.setOutId(outId);
      newRelation.setOutIdType(outIdType.getValue());
      this.userRelationService.createUserRelation(newRelation);
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
  ApiResponse addVisitorInfo(@RequestHeader HttpHeaders header, @RequestBody AddVisitorInfoRequest request) {
    logger.info("/yuntuoguan/addVisitorInfo get header {}", header);
    AddVisitorInfoResponse resp = new AddVisitorInfoResponse();
    try {
      String openId = header.getFirst(openIdStr);
      long userId = getUserId(openId,NumOutIdType.ONE);

      Visitor visitor = request.getInfo();
      com.tencent.wxcloudrun.model.Visitor visitorModel = visitor.dtoToModel();
      visitorModel.setUserId(userId);
      String visitorId = "V" + UserIdGenerator.generateUserId();
      visitorModel.setVisitorId(visitorId);
      this.visitorService.createVisitor(visitorModel);
      resp.setVisitor_id(visitorId);
    } catch (Exception e) {
      logger.error("/yuntuoguan/addVisitorInfo post fail,err:{}",e.getMessage());
      return ApiResponse.error(systemErrorMsg);
    }
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
    AddVisitorInfoResponse resp = new AddVisitorInfoResponse();
    try {
      Visitor visitor = request.getInfo();
      com.tencent.wxcloudrun.model.Visitor visitorModel = visitor.dtoToModel();

      Optional<com.tencent.wxcloudrun.model.Visitor> visitorDto = this.visitorService.getVisitorByVisitorId(visitorModel.getVisitorId());
      if (!visitorDto.isPresent()) {
        return ApiResponse.error("该访问者不存在，无法更新");
      }
      this.visitorService.updateVisitor(visitorModel);
      resp.setVisitor_id(visitor.getVisitor_id());
    }catch (Exception e) {
      logger.error("/yuntuoguan/updateVisitorInfo post fail,err: {}", e.getMessage());
      return ApiResponse.error(systemErrorMsg);
    }
    logger.info("/yuntuoguan/updateVisitorInfo post response, response: {}", resp);
    return ApiResponse.ok(resp);
  }

  /**
   * 获取可预约时间
   *
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @GetMapping(value = "/yuntuoguan/queryAvailableTime")
  ApiResponse queryAvailableTime(@RequestHeader HttpHeaders header, @ModelAttribute QueryAvailableTimeRequest request) {
    logger.info("/yuntuoguan/queryAvailableTime get request, request: {}", request);
    QueryAvailableTimeResponse resp = new QueryAvailableTimeResponse();
    try {
      DayPeriod dayPeriod = DayPeriod.getAvailablePeriod(request.getStart_date(), request.getCounselor_id(), counselingService, orderService);
      HashMap<String, DayPeriod> calendar = new HashMap<>();
      calendar.put(dayPeriod.getDate(), dayPeriod);
      resp.setCalendar(calendar);
    } catch (Exception e) {
        logger.error("/yuntuoguan/queryAvailableTime post fail,err: {}", e.getMessage());
        return ApiResponse.error(systemErrorMsg);
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
ApiResponse queryOrderList(@RequestHeader HttpHeaders header, @ModelAttribute QueryOrderListRequest request) {
  logger.info("/yuntuoguan/queryOrderList get request, request: {}", request);
  QueryOrderListResponse resp = new QueryOrderListResponse();
  try {
    String openId = header.getFirst(openIdStr);
    long userId = getUserId(openId,NumOutIdType.ONE);


    List<com.tencent.wxcloudrun.model.Order> orderModelList = this.orderService.queryOrderListByUserId(userId, request.getCursor(), request.getCount());
    List<com.tencent.wxcloudrun.model.Visitor> visitorList = this.visitorService.queryVisitorListByUserId(userId);
    Map<String, com.tencent.wxcloudrun.model.Visitor> map = new HashMap<>();
    for (com.tencent.wxcloudrun.model.Visitor entity : visitorList) {
      if (entity.getVisitorId() != null) {
        map.put(entity.getVisitorId(), entity);
      }
    }
    List<com.tencent.wxcloudrun.dto.Order> orderDtolList = new ArrayList<>();

    for (com.tencent.wxcloudrun.model.Order order : orderModelList) {
      Order orderDto = new Order();
      orderDto.setOrder_base(order.ModelToOrderBase());
      orderDto.setCounselor_id(order.getCounselorId());
      orderDto.setVisitor(map.get(order.getVisitorId()).getName());
      orderDtolList.add(orderDto);

    }
    //获取访问者信息
    resp.setOrder_list(orderDtolList);
    if (!orderDtolList.isEmpty()) {
      resp.setNext_cursor(orderDtolList.get(orderDtolList.size() - 1).getOrder_base().getPeriod_key());
    }
  }catch (Exception e) {
    logger.error("/yuntuoguan/queryOrderList post fail,err: {}", e.getMessage());
    return ApiResponse.error(systemErrorMsg);
  }
  logger.info("/yuntuoguan/queryOrderList get response, response: {}", resp);
  return ApiResponse.ok(resp);


}





  /**
   * 预约
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/createOrder")
  ApiResponse createOrder(@RequestHeader HttpHeaders header, @RequestBody CreateOrderRequest request) {
    logger.info("/yuntuoguan/createOrder post request, request: {}", request);
    //获取openID
    //openId获取用户id
    CreateOrderResponse resp = new CreateOrderResponse();
    try {
      String openId = header.getFirst(openIdStr);
      long userId = getUserId(openId,NumOutIdType.ONE);
      Optional<com.tencent.wxcloudrun.model.Order> orderDto = this.orderService.queryOrderByCounselorIdAndPeriodKey(request.getCounselor_id(), request.getUnit_period_key());
      if (orderDto.isPresent()) {
        return ApiResponse.error("很抱歉，该预约时间已被预约，请重新预约");
      }
      Optional<Counseling> counseling = counselingService.getCounselingByCounselorId(request.getCounselor_id(), DateUtil.dateToTime(request.getUnit_period_key().split("_")[0]));
      if (!counseling.isPresent()) {
        return ApiResponse.error("很抱歉，咨询师信息错误，暂无法预约");
      }
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
    order.setDuration(counseling.get().getDuration());

      order.setFee(counseling.get().getFee());
    order.setBizDate(DateUtil.dateToTime(request.getUnit_period_key().split("_")[0]));
      this.orderService.createOrder(order);

    resp.setOrder_id(order.getOrderId());
    //调用微信支付
    //http调用微信支付
    //返回支付信息
    //返回订单信息
    PaymentRequest paymentReq = new PaymentRequest(openId, orderId, header.getFirst(ipStr), order.getFee());
    String url = "http://api.weixin.qq.com/_/pay/unifiedOrder";
    /*
      paymentReq转换为json字符串
     */
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(paymentReq);
      String response = OkHttpClient.syncPost(url, json);
      ObjectMapper objectMapper2 = new ObjectMapper();
      WechatPaymentResponse wechatPaymentResponse = objectMapper2.readValue(response, WechatPaymentResponse.class);
      resp.setOrder_id(orderId);
      RequestPaymentRequest requestPaymentRequest = getRequestPaymentRequest(wechatPaymentResponse);
      resp.setRequestPaymentRequest(requestPaymentRequest);
    } catch (Exception e) {
      logger.error("/yuntuoguan/createOrder post fail,err: {}", e.getMessage());
      return ApiResponse.error(systemErrorMsg);
    }
    logger.info("/yuntuoguan/createOrder post response, response: {}", resp);
    //返回相关信息
    return ApiResponse.ok(resp);
  }

  private static RequestPaymentRequest getRequestPaymentRequest(WechatPaymentResponse wechatPaymentResponse) {
    Payment payment = wechatPaymentResponse.getRespdata().getPayment();

    RequestPaymentRequest requestPaymentRequest = new RequestPaymentRequest();
    requestPaymentRequest.setApp_id(payment.getAppId());
    requestPaymentRequest.setTimestamp(payment.getTimeStamp());
    requestPaymentRequest.setNonce_str(payment.getNonceStr());
    requestPaymentRequest.setPacakge(payment.getPackageName());
    requestPaymentRequest.setSign_type(payment.getSignType());
    requestPaymentRequest.setPay_sigin(payment.getPaySign());
    return requestPaymentRequest;
  }

  /**
   * 支付回调
   * @param request {@link CounterRequest}
   * @return API response json
   */
  @PostMapping(value = "/yuntuoguan/wechatPaySuccessCallback")
  WxApiResponse create(@RequestHeader HttpHeaders header, @RequestBody WechatPaymentCallbackRequest request) {
    logger.info("/yuntuoguan/wechatPaySuccessCallback post request, request: {}", request);
    //获取openID
    //openId获取用户id
    //查看咨询师订单
    try {
      if (Objects.equals(request.getReturnCode(), "SUCCESS")) {
        this.orderService.updateOrderStatus(request.getOutTradeNo(), 2);
      } else {
        this.orderService.updateOrderStatus(request.getOutTradeNo(), -1);
      }
    }catch (Exception e){
      logger.error("/yuntuoguan/wechatPaySuccessCallback post fail,err: {}", e.getMessage());
      return WxApiResponse.error("更新失败");
    }
    logger.info("/yuntuoguan/wechatPaySuccessCallback post request, request: {}", request);
    return WxApiResponse.ok();
  }




}