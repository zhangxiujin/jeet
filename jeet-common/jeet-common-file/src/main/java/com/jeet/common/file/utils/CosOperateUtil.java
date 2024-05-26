package com.jeet.common.file.utils;

import com.jeet.common.file.config.CosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangxiujin
 * @date: 2022/4/26 15:08
 * @descripton 腾讯云cos操作工具
 */
public class CosOperateUtil {

    public static COSClient cosClient = null;

    static {

    }

    /**
     * 初始化 CosClient
     *
     * clientConfig相关配置如下：
     * region	构造函数或 set 方法;	存储桶所在的地域，COS 地域的简称请参见 地域和访问域名 文档;	Region
     * httpProtocol	set 方法	;请求所使用的协议，默认使用 HTTPS 协议与 COS 交互;	HttpProtocol
     * signExpired	set 方法	;请求签名的有效时间，单位：秒，默认为3600s;	long
     * connectionTimeout	set 方法;	连接 COS 服务的超时时间，单位：毫秒，默认为30000ms;	int
     * socketTimeout	set 方法;	客户端读取数据的超时时间，单位：毫秒，默认为30000ms;	int
     * httpProxyIp	set 方法;	代理服务器的 IP;	String
     * httpProxyPort	set 方法;	代理服务器的端口;	int
     *
     * 默认请求重试策略:
     * 使用 SDK 生成的 cosClient 发起的请求，默认已经对响应错误的请求进行了重试。重试规则如下：
     *
     * 重试的次数：默认值为 3，可以通过 clientConfig.setMaxErrorRetry 进行设置。
     * 如果设置为 0，则所有类型的错误请求都不进行重试。
     * 重试的错误类型：客户端异常中所有的报 IOException 的错误。服务端异常中状态码为 500, 502, 503 504 的错误。
     */
    private static COSClient getCosClient() {
        if(cosClient == null) {
            // 1 初始化用户身份信息（secretId, secretKey）。
            // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
            String secretId = CosConfig.secretId;
            String secretKey = CosConfig.secretKey;
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(CosConfig.cosRegion);
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            // 从 5.6.54 版本开始，默认使用了 https
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
            return cosClient;
        }
        return cosClient;
    }

    /**
     * 创建存储桶
     */
    public static void createBucket(String bucketName) {
        String bucket = bucketName; //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try{
            Bucket bucketResult = getCosClient().createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
    }

    /**
     * 查询存储桶列表
     */
    public static List<Bucket> queryBucketList() {
        return getCosClient().listBuckets();
    }

    /**
     * 上传对象
     *
     * 将本地文件或者已知长度的输入流内容上传到 COS，适用于20M以下图片类小文件上传，最大支持上传不超过5GB文件。5GB以上的文件必须使用分块上传或高级 API 接口上传。
     * 说明：
     * 高级 API 接口在 com.qcloud.cos.transfer.* 子包下。
     *
     * 若本地文件大部分在20M以上，建议您参考使用高级 API 接口进行上传。
     * 若 COS 上已存在同样 Key 的对象，上传时则会覆盖旧的对象。
     *
     * @param localFilePath 本地文件存放地址
     *
     */
    public static void uploadToCos(String localFilePath, String cosPath, String cosFileName) {
        // 指定要上传的文件
        File localFile = new File(localFilePath);
        // 指定文件将要存放的存储桶
        String bucketName = CosConfig.bucketName;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = cosPath + "/" + cosFileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        COSClient cosClient = getCosClient();
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//        cosClient.shutdown();
    }

    /**
     * 删除cos对象
     *
     * @param objectKey 对象键，例如 folder/object.png
     */
    public static void deleteFile(String objectKey) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = CosConfig.bucketName;
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        String key = objectKey;
        COSClient cosClient = getCosClient();
        cosClient.deleteObject(bucketName, key);
//        cosClient.shutdown();
    }

    /**
     * 批量删除cos对象
     */
    public static void batchDeleteFile(List<String> keys) {
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = CosConfig.bucketName;

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        for (String key : keys) {
            // 传入要删除的文件名
            keyList.add(new DeleteObjectsRequest.KeyVersion(key));
        }
        deleteObjectsRequest.setKeys(keyList);
        COSClient cosClient = null;
        try {
            cosClient = getCosClient();
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) {
            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
//            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
//            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
            throw new RuntimeException(mde);
        } catch (CosServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (CosClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        finally {
//            if (cosClient != null) {
//                cosClient.shutdown();
//            }
//        }
    }

}
