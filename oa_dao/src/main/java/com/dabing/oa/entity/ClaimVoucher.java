package com.dabing.oa.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//报销单
public class ClaimVoucher {
    private Integer id;
    private String cause;
    private String createSn;//创建人：关联employee对象
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;//创建时间

    private String nextDealSn;//待处理人：关联employee对象
    private Double totalAmount;//总金额
    private String status;//状态

    private Employee creator;
    private Employee dealer;

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCreateSn() {
        return createSn;
    }

    public void setCreateSn(String createSn) {
        this.createSn = createSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNextDealSn() {
        return nextDealSn;
    }

    public void setNextDealSn(String nextDealSn) {
        this.nextDealSn = nextDealSn;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
