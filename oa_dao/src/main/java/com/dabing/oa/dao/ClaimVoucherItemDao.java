package com.dabing.oa.dao;

import com.dabing.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

//报销单条目
import java.util.List;
@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);
    void update(ClaimVoucherItem claimVoucherItem);
    void delete(int id);
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
