package com.dabing.oa.biz;

import com.dabing.oa.entity.ClaimVoucher;
import com.dabing.oa.entity.ClaimVoucherItem;
import com.dabing.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);
    ClaimVoucher get(int id);
    List<ClaimVoucherItem> getItems(int cvid);
    List<DealRecord> getRecords(int cvid);

    List<ClaimVoucher> getForSelf(String sn);//获取个人的把报销单
    List<ClaimVoucher> getForDeal(String sn);//获取需要处理的报销单

    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    void submit(int id);//把编号对应的报销单进行提交

    void deal(DealRecord dealRecord);
}
