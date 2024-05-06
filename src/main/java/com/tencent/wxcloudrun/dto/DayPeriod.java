package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Counseling;
import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.service.CounselingService;
import com.tencent.wxcloudrun.service.OrderService;
import com.tencent.wxcloudrun.utils.DateUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class DayPeriod {
    private String date; // Date
    private List<TimePeriod> time_period_list; // Time period list

    public static DayPeriod getAvailablePeriod(String time, String counselorId, CounselingService counselingService, OrderService orderService) throws Exception{
        DayPeriod dayPeriod= new DayPeriod();
        dayPeriod.setDate(time);
        dayPeriod.setTime_period_list(new ArrayList<>());
        LocalDateTime t = DateUtil.dateToTime(time);
        Optional<Counseling> counseling = counselingService.getCounselingByCounselorId(counselorId, t);
        if (!counseling.isPresent()){
           throw new Exception();
        }
        List<Order> orderList =  orderService.queryOrderListByCounselorId(counselorId, t, t);
        Map<String,Boolean> haveOrder = new HashMap<>();
        for (Order order : orderList){
            haveOrder.put(order.getUnitPeriodKey(), Boolean.TRUE);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            CounselingExtra counselorExtra = mapper.readValue(counseling.get().getExtra(), CounselingExtra.class);
            for (CounselingPeriodRule rule:counselorExtra.getRule()){
                TimePeriod timePeriod = new TimePeriod();
                timePeriod.setStart_time(rule.getStartTime());
                timePeriod.setEnd_time(rule.getEndTime());
                timePeriod.setUnit_period_list(new ArrayList<>());
                String periodName =  DateUtil.TimeToPeriodName(rule.getStartTime());
                for ( int i=1;i<=rule.getSeqSize();i++) {
                    UnitPeriod unitPeriod = new UnitPeriod();

                    unitPeriod.setPeriod(periodName);
                    unitPeriod.setSeq(String.valueOf(i));
                    unitPeriod.setName(unitPeriod.getPeriod() + unitPeriod.getSeq());
                    String[] l = {
                            time,
                            rule.getStartTime(),
                            rule.getEndTime(),
                            String.valueOf(i),
                    };
                    unitPeriod.setKey(String.join("_", l));
                    unitPeriod.setIs_available(true);
                    if (haveOrder.get(unitPeriod.getKey()) != null && haveOrder.get(unitPeriod.getKey())) {
                        unitPeriod.setIs_available(false);
                    }
                    timePeriod.getUnit_period_list().add(unitPeriod);
                }
                dayPeriod.getTime_period_list().add(timePeriod);
            }

        } catch (Exception e ){
            throw e;
        }
        return dayPeriod;
    }
}
