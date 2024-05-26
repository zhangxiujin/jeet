package com.jeet.task.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author: zhangxiujin
 * @date: 2022/4/29 16:19
 * @descripton
 */
@Setter
@Getter
@Component
@RefreshScope
public class AliPayConfig {

    /**
     * 阿里支付appId
     */
    @Value("${alipay.appId}")
    private String appId;

    /**
     * 应用私钥
     */
    @Value("${alipay.privateKey}")
    private String privateKey;

    /**
     * 证书存放路径
     */
    @Value("${alipay.certRootPath}")
    private String certRootPath;

    /**
     * 应用公钥证书文件名
     */
    @Value("${alipay.appCertPublicKeyFileName}")
    private String appCertPublicKeyFileName;

    /**
     * 支付宝公钥证书文件名
     */
    @Value("${alipay.alipayCertPublicKeyFileName}")
    private String alipayCertPublicKeyFileName;

    /**
     * 支付宝根证书文件名
     */
    @Value("${alipay.alipayRootCertFileName}")
    private String alipayRootCertFileName;

    /**
     * 电脑支付产品码
     */
    @Value("${alipay.pc_product_code}")
    private String pc_product_code;

    /**
     * 手机网站支付产品码
     */
    @Value("${alipay.mobile_web_product_code}")
    private String mobile_web_product_code;

    /**
     * 支付成功后的回调地址
     */
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    /**
     * 支付的商品价格 (单位：分)
     */
//    @Value("${alipay.totalAmount}")
//    private Integer totalAmount;

    /**
     * 支付的商品名称
     */
    @Value("${alipay.productName}")
    private String productName;


}
