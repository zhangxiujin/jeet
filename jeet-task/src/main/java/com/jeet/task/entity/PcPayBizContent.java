package com.jeet.task.entity;

import lombok.Data;

/**
 * @author: zhangxiujin
 * @date: 2022/4/29 17:19
 * @descripton 支付传入的参数实体
 */
@Data
public class PcPayBizContent {

    /**
     * 必选, 订单号，每次保证不一样
     */
    private String out_trade_no;

    /**
     * 必填, 销售产品码，与支付宝签约的产品码名称。目前电脑支付场景下仅支持 FAST_INSTANT_TRADE_PAY
     */
    private String product_code;

    /**
     * 必填，订单总金额，单位为元，精确到小数点后两位，取值范围为 [0.01,100000000]。金额不能为0。
     */
    private Double total_amount;

    /**
     * 必填, 商品的标题/交易标题/订单标题/订单关键字等。 不可使用特殊字符，如 /,=,& 等。
     */
    private String subject;

}
