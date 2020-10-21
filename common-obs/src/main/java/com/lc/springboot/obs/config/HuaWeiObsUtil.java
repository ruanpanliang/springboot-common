package com.lc.springboot.obs.config;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangc
 */
@Slf4j
@Component
public class HuaWeiObsUtil {

    @Autowired
    HuaWeiObsProperties huaWeiObsProperties;

    /**
     * 客户端操作对象
     */
    private ObsClient obsClient;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(huaWeiObsProperties.getEndPoint())) {
            log.warn("obs的endpoint信息没有做配置，obs功能不能使用哦!");
            return;
        }

        log.info("初始化obsClient类");
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(huaWeiObsProperties.getEndPoint());
        this.obsClient = new ObsClient(huaWeiObsProperties.getAk(), huaWeiObsProperties.getSk(), config);
    }

    /**
     * 列举桶
     *
     * @return
     */
    public boolean listBucket() {
        log.info("开始查询");
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);
        for (ObsBucket bucket : buckets) {
            log.info("BucketName:" + bucket.getBucketName());
            log.info("CreationDate:" + bucket.getCreationDate());
            log.info("Location:" + bucket.getLocation());
        }
        return true;
    }

    /**
     * 判断用户是否存在
     *
     * @param bucketName 桶名称
     * @return 存在返回true，不存在返回false
     */
    public boolean bucketExists(String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            return false;
        }

        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);
        for (ObsBucket bucket : buckets) {
            if (bucketName.equals(bucket.getBucketName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建桶
     *
     * @throws ObsException 异常
     */
    public void createBucket(String name) throws ObsException {
        log.info("开始创建桶");
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(name);
        obsClient.createBucket(obsBucket);
        log.info("Create bucket:" + name + " successfully!\n");
    }

    /**
     * 每次上传会做覆盖操作
     *
     * @param fileKey 文件key值，唯一依靠key值获取文件
     * @return 返回布尔值是否上传成功
     * @throws ObsException 异常
     */
    public boolean uploadFile(String fileKey, InputStream inputStream) throws ObsException {
        obsClient.putObject(huaWeiObsProperties.getBucketName(), fileKey, inputStream);
        return true;
    }

    /**
     * 删除文件
     *
     * @param fileKey 文件key
     * @return 返回删除是否成功
     * @throws ObsException 异常
     */
    public boolean deleteFile(String fileKey) throws ObsException {
        return obsClient.deleteObject(huaWeiObsProperties.getBucketName(), fileKey).isDeleteMarker();
    }

    /**
     * 批量删除文件
     *
     * @param fileKeys 文件key列表
     * @return 返回删除失败文件key 全部成功则返回空集合
     * @throws ObsException 异常
     */
    private List<String> deleteFiles(List<String> fileKeys) throws ObsException {
        DeleteObjectsRequest request = new DeleteObjectsRequest(huaWeiObsProperties.getBucketName());
        List<KeyAndVersion> toDelete = new ArrayList<KeyAndVersion>();
        List<String> errorList = new ArrayList<>();

        for (String fileKey : fileKeys) {
            toDelete.add(new KeyAndVersion(fileKey));
        }
        request.setKeyAndVersions(toDelete.toArray(new KeyAndVersion[toDelete.size()]));
        DeleteObjectsResult result = obsClient.deleteObjects(request);
        if (result.getErrorResults().isEmpty()) {
            return errorList;
        }
        for (DeleteObjectsResult.ErrorResult errorResult : result.getErrorResults()) {
            errorList.add(errorResult.getObjectKey());
        }
        return errorList;
    }

    /**
     * @param fileKey 文件key值
     * @return 返回文件流
     * @throws ObsException 异常
     */
    public InputStream downLoadFile(String fileKey) throws ObsException {
        ObsObject obsObject = obsClient.getObject(huaWeiObsProperties.getBucketName(), fileKey);
        return obsObject.getObjectContent();
    }


    /**
     * @param fileKeys        文件key值
     * @param fileInputStream 文件流
     * @return 返回修改是否成功
     * @throws ObsException 异常
     */
    public boolean updateFile(String fileKeys, FileInputStream fileInputStream) throws ObsException {
        if (!deleteFile(fileKeys)) {
            return false;
        }
        return uploadFile(fileKeys, fileInputStream);
    }

    public String readFileContent(String fileKeys) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader br;
        try {
            InputStream inputStream = downLoadFile(fileKeys);
            br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    /**
     * 追加文件内容
     *
     * @param fileKey
     * @param inputStream
     * @param position    其实位置（默认值是-1）
     * @return
     */
    public AppendObjectResult appendObject(String fileKey, InputStream inputStream, long position) {
        AppendObjectRequest request = new AppendObjectRequest();
        request.setBucketName(huaWeiObsProperties.getBucketName());
        request.setObjectKey(fileKey);
        request.setInput(inputStream);
        request.setPosition(position);
        return obsClient.appendObject(request);
    }

    /**
     * 获取桶存量的大小
     *
     * @return
     */
    public long getBucketStorageInfoSize() {
        BucketStorageInfo bucketStorageInfo = obsClient.getBucketStorageInfo(huaWeiObsProperties.getBucketName());
        return bucketStorageInfo.getSize();
    }

    /**
     * 获取剩余空间(单位：字节)
     */
    public long getLeftStorageInfoSize() {
        long bucketStorageInfoSize = getBucketStorageInfoSize();
        return huaWeiObsProperties.getObsTotalStorageSize() - bucketStorageInfoSize;
    }
}
