package com.xujiangjun.archetype.web.support;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 阿里云OSS上传辅助工具
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class OSSHelper {

    private static final String  accessKeyId     = "";
    private static final String  accessKeySecret = "";
    private static final String  endpoint        = "http://oss-cn-hangzhou.aliyuncs.com";

    public static boolean fileUpload(String bucketName, String filename, MultipartFile file) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(file.getBytes().length);
            PutObjectResult result = ossClient.putObject(bucketName, filename, file.getInputStream(), meta);
            if(result != null){
                log.debug("文件上传返回结果：" + JSONObject.fromObject(result));
                return true;
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            ossClient.shutdown();
        }
        return false;
    }

    public static boolean fileUpload(String bucketName, String filename, InputStream inputStream) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult result = ossClient.putObject(bucketName, filename, inputStream);
            if(result != null){
                log.debug("文件上传返回结果：" + JSONObject.fromObject(result));
                return true;
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            ossClient.shutdown();
        }
        return false;
    }

    /**
     * 校验文件类型是否合法
     * @param file 上传文件
     * @param fileTypes 允许上传的文件类型
     * @return 校验结果：true 校验通过；false 校验不通过
     */
    public static boolean checkFile(MultipartFile file, String fileTypes){
        if(file == null || file.isEmpty()){
            log.warn("上传文件为空");
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        if(index < 0){
            throw new RuntimeException("没有检索到文件的后缀，允许上传文件的后缀：" + fileTypes);
        }
        String suffix = originalFilename.substring(index);
        if(!StringUtils.contains(suffix, fileTypes)){
            throw new RuntimeException("允许上传文件的后缀：" + fileTypes);
        }
//        if(file.getSize() > limitFileSize){
//            throw new RuntimeException("上传文件大小超过规定的限制，文件大小：" + file.getSize());
//        }
        return true;
    }
}
