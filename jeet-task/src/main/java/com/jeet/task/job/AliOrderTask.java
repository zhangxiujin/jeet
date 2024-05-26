package com.jeet.task.job;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jeet.common.core.utils.AmountUtil;
import com.jeet.common.core.utils.DateUtil;
import com.jeet.common.redis.service.RedisService;
import com.jeet.task.constant.PayConstant;
import com.jeet.task.domain.AliPayOrder;
import com.jeet.task.mapper.AliPayOrderMapper;
import com.jeet.task.util.AliPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangxiujin
 * @date: 2022/5/1 17:01
 * @descripton 支付宝订单任务
 */
@Slf4j
@Component("aliOrderTask")
public class AliOrderTask {

    @Autowired
    private AliPayOrderMapper aliPayOrderMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 找出未支付订单信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void queryUnSuccessPayOrder() throws Exception {
        log.debug("执行aliOrderTask任务...");
        AliPayOrder aliPayOrder = new AliPayOrder();
        aliPayOrder.setPayStatus(PayConstant.PAY_STATUS.UN_PAY.getValue());
        aliPayOrder.setIfTradeClosed(PayConstant.TRADE_STATUS.NORMAL.getValue());  //交易状态是已关闭的就不查询订单是否支付成功了
        List<AliPayOrder> list = aliPayOrderMapper.select(aliPayOrder);
        for (AliPayOrder payMemberOrder : list) {
            AlipayTradeQueryResponse response =
                    AliPayUtil.queryOrder(payMemberOrder.getOutTradeNo(), "");
            if (response.isSuccess()) {
                String trade_status = response.getTradeStatus();
                if (trade_status.equals("TRADE_SUCCESS")) {
                    payMemberOrder.setPayStatus(PayConstant.PAY_STATUS.PAY_SUCCESS.getValue());
                    String trade_no = response.getTradeNo();
                    String buyer_id = response.getBuyerUserId();
                    String pay_channel = response.getFundBillList().get(0).getFundChannel();
                    String receipt_amount = response.getFundBillList().get(0).getAmount();
//                    String receipt_amount = response.getReceiptAmount();  //查询订单接口不会返回这个字段
                    String subject = response.getSubject();
//                    String body = response.getBody();  //由于body长度过长，占用数据库表空间，暂时不存
                    Date gmt_payment = response.getSendPayDate();  //本次交易打款给卖家的时间
                    Date success_time = DateUtil.getCurrentDate();

                    payMemberOrder.setTradeNo(trade_no);
                    payMemberOrder.setBuyerId(buyer_id);
                    payMemberOrder.setTradeStatus(trade_status);
                    payMemberOrder.setPayChannel(pay_channel);
                    payMemberOrder.setReceiptAmount(AmountUtil.yuan2Cent(receipt_amount));
                    payMemberOrder.setSubject(subject);
                    payMemberOrder.setGmtPayment(gmt_payment);
                    payMemberOrder.setSuccessTime(success_time);
                    updateOrderByOutTradeNo(payMemberOrder);
                    //更新余额
                    Long uid = payMemberOrder.getUid();
                    synchronized (String.valueOf(uid).intern()) {
                        //查询当前余额
                        Object balance = redisService.getCacheObject("balance:" + uid);
                        BigDecimal totalBalance = null;
                        Double amount = AmountUtil.cent2Yuan(String.valueOf(payMemberOrder.getTotalAmount()));
                        if(balance == null) {
                            totalBalance = new BigDecimal(amount.toString());
                        } else {
                            String _balance = String.valueOf(balance);
                            //total_amount 为充值金额 元
                            totalBalance = new BigDecimal(_balance).add(new BigDecimal(amount.toString()));
                        }
                        redisService.setCacheObject("balance:" + uid, totalBalance.toString());
                    }
                }
                if (trade_status.equals("TRADE_CLOSED")) {
                    AliPayOrder order = new AliPayOrder();
                    order.setOutTradeNo(payMemberOrder.getOutTradeNo());
                    order.setIfTradeClosed(PayConstant.TRADE_STATUS.CLOSED.getValue()); // 设置交易已关闭
                    updateOrderByOutTradeNo(order);
                }
            }
        }
    }

    /**
     * 支付成功后更新订单信息
     */
    private void updateOrderByOutTradeNo(AliPayOrder aliPayOrder) {
        Example example = new Example(AliPayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("outTradeNo", aliPayOrder.getOutTradeNo());
        aliPayOrderMapper.updateByExampleSelective(aliPayOrder, example);
    }

}
