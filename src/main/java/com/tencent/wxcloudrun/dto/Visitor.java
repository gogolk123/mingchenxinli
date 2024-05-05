package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class Visitor {
    private String visitor_id; // ID
    private String name; // Name
    private String gender; // Gender
    private String born; // Birth date
    private String phone; // Phone number
    private boolean is_first_visit; // Is first visit

    public com.tencent.wxcloudrun.model.Visitor dtoToModel() {
        com.tencent.wxcloudrun.model.Visitor visitor = new com.tencent.wxcloudrun.model.Visitor();
        visitor.setVisitorId(this.visitor_id);
        visitor.setName(this.name);
        visitor.setGender(this.gender);
        visitor.setBorn(this.born);
        visitor.setPhone(this.phone);
        visitor.setIsFirstVisit(this.is_first_visit);
        return visitor;
    };
}




