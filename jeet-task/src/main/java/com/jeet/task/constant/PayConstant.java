package com.jeet.task.constant;

/**
 * @author: zhangxiujin
 * @date: 2022/4/11 19:28
 * @descripton 支付相关常量配置
 */
public class PayConstant {

    public enum PAY_GOODS {
        MEMBER,
        AI
    }

    public enum PAY_STATUS {
        UN_PAY(0), PAY_SUCCESS(1), PAY_FAIL(-1);

        private Integer statusVal;

        PAY_STATUS(Integer statusVal) {
            this.statusVal = statusVal;
        }

        public Integer getValue() {
            return this.statusVal;
        }
    }

    public enum PAY_CHANNEL {
        PC_WECHAT, PC_ALIPAY, MOBILE_WECHAT, MOBILE_ALIPAY
    }

    public enum TRADE_STATUS {
        NORMAL(0), CLOSED(1);

        private Integer statusVal;

        TRADE_STATUS(Integer statusVal) {
            this.statusVal = statusVal;
        }

        public Integer getValue() {
            return this.statusVal;
        }

    }

}
