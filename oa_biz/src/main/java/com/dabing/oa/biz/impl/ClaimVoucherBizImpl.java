package com.dabing.oa.biz.impl;

import com.dabing.oa.biz.ClaimVoucherBiz;
import com.dabing.oa.dao.ClaimVoucherDao;
import com.dabing.oa.dao.ClaimVoucherItemDao;
import com.dabing.oa.dao.DealRecordDao;
import com.dabing.oa.dao.EmployeeDao;
import com.dabing.oa.entity.ClaimVoucher;
import com.dabing.oa.entity.ClaimVoucherItem;
import com.dabing.oa.entity.DealRecord;
import com.dabing.oa.entity.Employee;
import com.dabing.oa.global.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {

    @Resource(name = "claimVoucherDao")
    private ClaimVoucherDao claimVoucherDao;
    @Resource(name = "claimVoucherItemDao")
    private ClaimVoucherItemDao claimVoucherItemDao;

    @Resource(name = "dealRecordDao")
    private DealRecordDao dealRecordDao;
    @Resource(name="employeeDao")
    private EmployeeDao employeeDao;

    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    @Override
    public void submit(int id) {
        /**根据编号获取对应的报销单
         * 需要获取对应部门的部门经理：待处理人
         * 将状态改成已提交状态
         * 将提交后的报销单存回数据库
         */
        /**进行记录的保存：
         * 1.声明一个新的记录
         * 处理方式：提交
         * 处理人：该员工
         * 报销单编号：该id
         * 处理结果：状态改为已提交
         * 处理时间：当前系统时间
         * 备注：无
         */
        ClaimVoucher claimVoucher=claimVoucherDao.select(id);
        Employee employee=employeeDao.select(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_SUBMIT);
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Constant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);

        DealRecord dealRecord=new DealRecord();
        dealRecord.setDealWay(Constant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Constant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);

    }

    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_CREATED);
        //更新报销单信息
        claimVoucherDao.update(claimVoucher);
        //更新条目信息：将旧条目从数据库中取出来进行对比
        List<ClaimVoucherItem> olds=claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for(ClaimVoucherItem old:olds){
            boolean isHave=false;
            for(ClaimVoucherItem item:items){
                if(item.getId()==old.getId()){
                    isHave=true;
                    break;
                }
            }
            if(!isHave){
                //数据库里有表单没有的条目，说明修改的时候把这个条目删除了
                claimVoucherItemDao.delete(old.getId());
            }
            for(ClaimVoucherItem item:items){
                item.setClaimVoucherId(claimVoucher.getId());//为item设置报销单id，否则修改数据时新增item会发生空指针异常
                if(item.getId()!=null&&item.getId()>0){//说明数据库里面有这个条目了，要更新它的内容
                    claimVoucherItemDao.update(item);
                }else {//否则是编辑的时候加入了一个新的条目
                    claimVoucherItemDao.insert(item);
                }
            }
        }
    }


    @Override
    public void deal(DealRecord dealRecord) {
        /**修改报销单状态
         * 获取报销单：通过记录可以获取对应的编号，从而获取对应的报销单
         * 获取审核的人的职位：如果是总经理那肯定不用复审了
         * 修改记录的处理结果和时间
         * 然后更新该报销单
         * 插入这次处理的记录
         */

        ClaimVoucher claimVoucher=claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee=employeeDao.select(dealRecord.getDealSn());
        //经过运行发现前端给后端传参时多了个逗号,用split()分割
        String dealWay1=dealRecord.getDealWay().split(",")[1];

        if(dealWay1.equals(Constant.DEAL_PASS)){
            //如果处理方式是通过，则还需要判断是否需要复审
            if(claimVoucher.getTotalAmount()<=Constant.LIMIT_CHECK || employee.getPost().equals(Constant.POST_GM)){
                //小于5000和总经理不需要复审
                claimVoucher.setStatus(Constant.CLAIMVOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Constant.POST_CASHIER).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIMVOUCHER_APPROVED);


            }else{
                //需要复审
                claimVoucher.setStatus(Constant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Constant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Constant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealWay1.equals(Constant.DEAL_BACK)){
            //处理方式是打回
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_BACK);
        }else if(dealWay1.equals(Constant.DEAL_REJECT)){
            //处理方式是拒绝
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_TERMINATED);
        }else if(dealWay1.equals(Constant.DEAL_PAID)){
            //处理方式是打款
            claimVoucher.setStatus(Constant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Constant.CLAIMVOUCHER_PAID);
        }
        dealRecord.setDealTime(new Date());
        dealRecord.setDealWay(dealWay1);
        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);

    }

    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Constant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());//把报销单编号填进去
            claimVoucherItemDao.insert(item);
        }
    }

    @Override
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }
}
