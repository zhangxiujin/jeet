package com.jeet.task.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jeet.common.core.utils.ResourceUtil;
import com.jeet.common.core.utils.SpringUtils;
import com.jeet.task.config.AliPayConfig;
import com.jeet.task.entity.MobileWebPayBizContent;
import com.jeet.task.entity.PcPayBizContent;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: zhangxiujin
 * @date: 2022/4/29 15:57
 * @descripton 阿里支付工具
 */
public class AliPayUtil {

    public static AlipayClient alipayClient = null;

    /**
     * 获取微信支付配置
     * @return
     */
    public static AliPayConfig getAliPayConfig() {
        return (AliPayConfig) SpringUtils.getBean("aliPayConfig");
    }

    // 初始化 DefaultAlipayClient
    static {
        AliPayConfig aliPayConfig = getAliPayConfig();
        CertAlipayRequest certParams = new CertAlipayRequest();
        certParams.setServerUrl("https://openapi.alipay.com/gateway.do");
        //请更换为您的AppId
        certParams.setAppId(aliPayConfig.getAppId());
        //请更换为您的PKCS8格式的应用私钥
        certParams.setPrivateKey(aliPayConfig.getPrivateKey());
        //请更换为您使用的字符集编码，推荐采用utf-8
        certParams.setCharset("utf-8");
        certParams.setFormat("json");
        certParams.setSignType("RSA2");
        //请更换为您的应用公钥证书文件路径
        certParams.setCertPath(aliPayConfig.getCertRootPath() + "/" + aliPayConfig.getAppCertPublicKeyFileName());
        //请更换您的支付宝公钥证书文件路径
        certParams.setAlipayPublicCertPath(aliPayConfig.getCertRootPath() + "/" + aliPayConfig.getAlipayCertPublicKeyFileName());
        //更换为支付宝根证书文件路径
        certParams.setRootCertPath(aliPayConfig.getCertRootPath() + "/" + aliPayConfig.getAlipayRootCertFileName());
        //创建AlipayClient实例
        try {
            alipayClient = new DefaultAlipayClient(certParams);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 手机网站支付下单
     */
    public static void sendMobileWebOrder(
            HttpServletResponse response,
            String outTradeNo,
            String subject,
            Double totalAmount,
            String returnUrl) throws AlipayApiException {
        AliPayConfig aliPayConfig = getAliPayConfig();
        MobileWebPayBizContent mobileWebPayBizContent = new MobileWebPayBizContent();
        mobileWebPayBizContent.setOut_trade_no(outTradeNo);
        mobileWebPayBizContent.setProduct_code(aliPayConfig.getMobile_web_product_code());
        mobileWebPayBizContent.setSubject(subject);
        mobileWebPayBizContent.setTotal_amount(totalAmount);
        AlipayTradeWapPayRequest mobileWebPayRequest =
                getMobileWebPayRequest(returnUrl, aliPayConfig.getNotifyUrl(), JSON.toJSONString(mobileWebPayBizContent));
        try {
            String form = alipayClient.pageExecute(mobileWebPayRequest).getBody();  //调用SDK生成表单
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write(form); //直接将完整的表单html输出到页面
            response.getWriter().flush();
        }  catch (AlipayApiException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * PC支付下单
     */
    public static void sendOrder(
            HttpServletResponse response,
            String outTradeNo,
            String subject,
            Double totalAmount,
            String returnUrl) throws AlipayApiException {
        AliPayConfig aliPayConfig = getAliPayConfig();
        PcPayBizContent pcPayBizContent = new PcPayBizContent();
        pcPayBizContent.setOut_trade_no(outTradeNo);
        pcPayBizContent.setProduct_code(aliPayConfig.getPc_product_code());
        pcPayBizContent.setSubject(subject);
        pcPayBizContent.setTotal_amount(totalAmount);
        AlipayTradePagePayRequest payRequest =
                getPayRequest(returnUrl, aliPayConfig.getNotifyUrl(), JSON.toJSONString(pcPayBizContent));
        try  {
            String form = alipayClient.pageExecute(payRequest).getBody();  //调用SDK生成表单
            response.setContentType( "text/html;charset=UTF-8");
            response.getWriter().write(form); //直接将完整的表单html输出到页面
            response.getWriter().flush();
        }  catch (AlipayApiException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 统一收单线下交易查询-PC
     * @param out_trade_no
     * @param trade_no
     * @return
     * @throws AlipayApiException
     */
    public static AlipayTradeQueryResponse queryOrder(String out_trade_no, String trade_no) throws AlipayApiException {
        AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
        alipayTradeQueryRequest.setBizContent("{"  +
                "    \"out_trade_no\":\"" + out_trade_no + "\","  +
                "    \"trade_no\":\"" + trade_no + "\","  +
                "    \"query_options\":[\"fund_bill_list\"]" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.certificateExecute(alipayTradeQueryRequest);
        return response;
    }

    /**
     * 获取支付宝PC支付请求对象
     * @return
     */
    private static AlipayTradePagePayRequest getPayRequest(String returnUrl, String notifyUrl, String bizContent) {
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
        alipayTradePagePayRequest.setBizModel(alipayTradePagePayModel);
        alipayTradePagePayRequest.setReturnUrl(returnUrl);
        alipayTradePagePayRequest.setNotifyUrl(notifyUrl); //在公共参数中设置回跳和通知地址
        alipayTradePagePayRequest.setBizContent(bizContent); //填充业务参数
        return alipayTradePagePayRequest;
    }

    /**
     * 获取支付宝手机网站支付请求对象
     * @return
     */
    private static AlipayTradeWapPayRequest getMobileWebPayRequest(String returnUrl, String notifyUrl, String bizContent) {
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        alipayTradeWapPayRequest.setReturnUrl(returnUrl);
        alipayTradeWapPayRequest.setNotifyUrl(notifyUrl);
        alipayTradeWapPayRequest.setBizContent(bizContent);
        return alipayTradeWapPayRequest;
    }

    private static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    private static String getClasspath() {
        return ResourceUtil.getClassPath(AliPayUtil.class);
    }

}
