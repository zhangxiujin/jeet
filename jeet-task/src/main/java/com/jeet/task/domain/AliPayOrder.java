package com.jeet.task.domain;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author: zhangxiujin
 * @date: 2022/4/30 18:27
 * @descripton 支付宝下单信息实体
 */
@Data
public class AliPayOrder {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 支付人id（系统用户userId）
     */
    private Long uid;

    /**
     * 创建交易的次数 (创建交易是指，用户扫描支付宝二维码后即算创建交易)
     */
    private Integer tradeCount;

    /**
     * 交易是否关闭 1是，0否
     */
    private Integer ifTradeClosed;

    /**
     * 支付宝交易凭证号
     */
    private String tradeNo;

    /**
     * 支付宝应用appId
     */
    private String appId;

    /**
     * 商家订单号
     */
    private String outTradeNo;

    /**
     * 商家业务号
     */
    private String outBizNo;

    /**
     * 买家支付宝账号 ID
     */
    private String buyerId;

    /**
     * 卖家支付宝账号 ID
     */
    private String sellerId;

    /**
     * 支付的商品名称 (会员:member)
     */
    private String payGoods;

    /**
     * 下单时间（点击网页支付宝付款图标）
     */
    private Date orderTime;

    /**
     * 支付状态(0未支付，1成功, -1失败)
     */
    private Integer payStatus;

    /**
     * 支付渠道 (余额宝，余额，红包等), 对应回调fundChannel字段
     * 具体渠道代码说明：
     * COUPON 支付宝红包
     * ALIPAYACCOUNT 支付宝余额
     * POINT 集分宝
     * DISCOUNT 折扣券
     * PCARD 预付卡
     * FINANCEACCOUNT 余额宝
     * MCARD 商家储值卡
     * MDISCOUNT 商户优惠券
     * MCOUPON 商户红包
     * PCREDIT 蚂蚁花呗
     */
    private String payChannel;

    /**
     * 支付方式（pc_alipay, mobile_alipay）
     */
    private String payType;

    /**
     * 交易状态(对应支付宝回调字段trade_status);
     * WAIT_BUYER_PAY 交易创建，等待买家付款
     * TRADE_CLOSED 未付款交易超时关闭，或支付完成后全额退款
     * TRADE_SUCCESS 交易支付成功
     * TRADE_FINISHED 交易结束，不可退款
     */
    private String tradeStatus;

    /**
     * 订单金额。本次交易需要支付订单金额, 单位为分
     */
    private Integer totalAmount;

    /**
     * 实收金额。商家在交易中实际收到的款项, 单位为分
     */
    private Integer receiptAmount;

    /**
     * 订单标题/商品标题，最大长度256
     */
    private String subject;

    /**
     * 商品描述，最大长度400
     */
    private String body;

    /**
     * 交易创建时间
     */
    private Date gmtCreate;

    /**
     * 交易付款时间
     */
    private Date gmtPayment;

    /**
     * 交易成功时间
     */
    private Date successTime;
}
