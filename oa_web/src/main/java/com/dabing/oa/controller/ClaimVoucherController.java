package com.dabing.oa.controller;

import com.dabing.oa.biz.ClaimVoucherBiz;
import com.dabing.oa.dto.ClaimVoucherInfo;
import com.dabing.oa.entity.DealRecord;
import com.dabing.oa.entity.Employee;
import com.dabing.oa.global.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Autowired
    private ClaimVoucherBiz claimVoucherBiz;

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("items", Constant.getItems());//常量类里的费用类型存入
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }

    @RequestMapping("/add")
    public String add(HttpSession session,ClaimVoucherInfo info){
        Employee employee=(Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.save(info.getClaimVoucher(), info.getItems());
        return "redirect:deal";
    }

    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItems(id));
        map.put("records",claimVoucherBiz.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/self")
    public String self(HttpSession session,Map<String,Object> map){
        Employee employee=(Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    @RequestMapping("/deal")
    public String deal(HttpSession session,Map<String,Object> map){
        Employee employee=(Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
        map.put("items",Constant.getItems());//处理类型
        ClaimVoucherInfo info=new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(id));
        info.setItems(claimVoucherBiz.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }

    @RequestMapping("/update")
    public String update(HttpSession session,ClaimVoucherInfo info){
        Employee employee=(Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(info.getClaimVoucher(), info.getItems());
        return "redirect:deal";
    }

    @RequestMapping("/submit")
    public String submit (int id){
        claimVoucherBiz.submit(id);
        return "redirect:deal";
    }

    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        /**打开的页面跟详情页面相似
         * 需要有报销单
         * 报销单条目
         * 处理流程记录
         * 生成新的一个记录，这时可以先把记录的报销单编号存入，记录的处理方式、
         * 处理结果、备注、时间都是从前台获取的，存入到map的record里面
         */
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItems(id));
        map.put("records",claimVoucherBiz.getRecords(id));
        DealRecord dealRecord=new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }

    @RequestMapping("/check")
    public String check(HttpSession session,DealRecord dealRecord){
        Employee employee=(Employee)session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);
        return "redirect:deal";
    }
}
