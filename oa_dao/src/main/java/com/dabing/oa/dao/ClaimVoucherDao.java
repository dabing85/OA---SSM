package com.dabing.oa.dao;

import com.dabing.oa.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;
//报销单接口
@Repository("claimVoucherDao")
public interface ClaimVoucherDao {

    void insert(ClaimVoucher claimVoucher);
    void update(ClaimVoucher claimVoucher);
    void delete(int id);
    ClaimVoucher select(int id);
    List<ClaimVoucher> selectByCreateSn(String csn);//查询这个人创建的报销单
    List<ClaimVoucher> selectByNextDealSn(String ndsn);//查询这个人需要处理的报销单
}

