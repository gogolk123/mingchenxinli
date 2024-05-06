package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.dao.CounselingMapper;
import com.tencent.wxcloudrun.model.Counseling;
import com.tencent.wxcloudrun.service.impl.CounselingServiceImpl;
import com.tencent.wxcloudrun.utils.UserIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CounselingServiceImplTest {

    @Autowired
    private CounselingMapper counselingMapper;
    private CounselingServiceImpl counselingService;

    @BeforeEach
    void setUp() {
        counselingService = new CounselingServiceImpl(counselingMapper);
    }

    @Test
    void createCounseling() throws JsonProcessingException {
        Counseling counseling = new Counseling();
        // 设置counseling的属性
        // ...
        counseling.setCounselingId("C" + UserIdGenerator.generateUserId());
        counseling.setDuration(50);
        counseling.setCounselorId("C1540734033821845455");
        counseling.setFee(20000);
        counseling.setEffectStartDate(LocalDate.parse("2024-01-01").atStartOfDay());
        counseling.setEffectEndDate(LocalDate.parse("2029-01-01").atStartOfDay());
        counseling.setWay(1);
        CounselingExtra counselingExtra = new CounselingExtra();
        List<CounselingPeriodRule> rule = new ArrayList<>();
        counselingExtra.setRule(rule);
        CounselingPeriodRule rule1 =  new CounselingPeriodRule();
        rule1.setStartTime("09:30");
        rule1.setEndTime("12:00");
        rule1.setSeqSize(2);

        CounselingPeriodRule rule2 =  new CounselingPeriodRule();
        rule2.setStartTime("12:30");
        rule2.setEndTime("18:00");
        rule2.setSeqSize(3);
        CounselingPeriodRule rule3 =  new CounselingPeriodRule();
        rule3.setStartTime("18:00");
        rule3.setEndTime("21:00");
        rule3.setSeqSize(2);
        rule.add(rule1);
        rule.add(rule2);
        rule.add(rule3);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(counselingExtra);
        counseling.setExtra(json);
//        counselingService.createCounseling(counseling);
    }

    @Test
    void getCounselingByCounselorId() {
        String counselorId = "testCounselorId";
        LocalDateTime time = LocalDateTime.now();
//        Counseling expectedCounseling = new Counseling();
        // 从数据库中获取预期的Counseling对象
        // ...

//        Counseling actualCounseling = counselingService.getCounselingByCounselorId(counselorId, time).orElse(null);

//        assertEquals(expectedCounseling, actualCounseling);
    }

    @Test
    void updateCounseling() {
        Counseling counseling = new Counseling();
        // 设置counseling的属性
        // ...
//        counselingService.updateCounseling(counseling);
    }
}