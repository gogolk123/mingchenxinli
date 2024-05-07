package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.wxcloudrun.dao.CounselorMapper;

import com.tencent.wxcloudrun.service.impl.CounselorServiceImpl;
import com.tencent.wxcloudrun.utils.UserIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CounselorServiceImplTest {

    @Autowired
    private CounselorMapper counselorMapper;
    private CounselorServiceImpl counselorService;

    @BeforeEach
    void setUp() {
        counselorService = new CounselorServiceImpl(counselorMapper);
    }

    @Test
    void createCounselor() throws JsonProcessingException {
        Counselor counselor = new Counselor();
        // 设置counselor的属性
        // ...
        counselor.setCounselor_id("C" + UserIdGenerator.generateUserId());
        counselor.setEdu("test_edu");
        counselor.setName("test_name");
        counselor.setAddress("test_address");
        counselor.setEdu("test_edu");

        counselor.setEdu("test_edu");
        CounselorExtra extra = new CounselorExtra();
        counselor.setExtra(extra);
        extra.setGenre("test_genre");
        extra.setMotto("test_motto");
        List<String> scopes = new ArrayList<>();
        scopes.add("test_scope1");
        scopes.add("test_scope2");

        extra.setScope(scopes);
        List<String> experiences = new ArrayList<>();

        experiences.add("exper1");
        experiences.add("exper2");
        experiences.add("exper3");
        extra.setExperience(experiences);
        com.tencent.wxcloudrun.model.Counselor model = com.tencent.wxcloudrun.model.Counselor.DtoToModel(counselor);
        model.setUserId(UserIdGenerator.generateUserId());
//        counselorService.createCounselor(model);
    }

    @Test
    void getCounselorById() throws JsonProcessingException {
        String counselorId = "C1540734033821845455";
        Counselor expectedCounselor = new Counselor();
        // 从数据库中获取预期的Counselor对象
        // ...

//        com.tencent.wxcloudrun.model.Counselor actualCounselor = counselorService.getCounselorByCounselorId(counselorId).orElse(null);
//        assert actualCounselor != null;
//        Counselor dto = actualCounselor.ModelToDto();

    }

    @Test
    void updateCounselor() throws JsonProcessingException {
        Counselor counselor = new Counselor();
        // 设置counselor的属性
        // ...
        counselor.setCounselor_id("C" + UserIdGenerator.generateUserId());
        counselor.setEdu("test_edu");
        counselor.setName("test_name");
        counselor.setAddress("test_address");
        counselor.setEdu("test_edu");
        counselor.setEdu("test_edu");
        CounselorExtra extra = new CounselorExtra();
        extra.setGenre("test_genre");
        extra.setMotto("test_motto");
        List<String> scopes = new ArrayList<>();
        scopes.add("test_scope1");
        scopes.add("test_scope2");

        extra.setScope(scopes);        List<String> experiences = new ArrayList<>();
        experiences.add("exper1");
        experiences.add("exper2");
        experiences.add("exper3");
        extra.setExperience(experiences);
        com.tencent.wxcloudrun.model.Counselor model = com.tencent.wxcloudrun.model.Counselor.DtoToModel(counselor);
//        counselorService.updateCounselor(model);
    }

    @Test
    void deleteCounselor() {
        String counselorId = "testCounselorId";
//        counselorService.deleteCounselor(counselorId);
    }
}