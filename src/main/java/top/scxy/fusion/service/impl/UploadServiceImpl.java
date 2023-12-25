package top.scxy.fusion.service.impl;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.ImgResult;
import top.scxy.fusion.service.UploadService;
import top.scxy.fusion.utils.UploadUtil;

import java.io.InputStream;

@Service
@Transactional
public class UploadServiceImpl implements UploadService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ImgResult uploadImg(InputStream inputStream, String fileName) {
        ImgResult imgResult = new ImgResult();
        UploadManager uploadManager = UploadUtil.getUploadManager();
        logger.info("upload img: " + fileName);
        String upToken = UploadUtil.getUploadToken(UtilConstant.QiniuBucket);
        try {
            uploadManager.put(inputStream, fileName, upToken, null, null);
            imgResult.setSuccess(1);
            imgResult.setUrl(UtilConstant.ImgUrlPrefix + fileName);
            logger.info("upload img success:" + imgResult.getUrl());
        } catch (Exception e) {
            imgResult.setSuccess(0);
            imgResult.setMessage("error");
            imgResult.setMessage(e.getMessage());
            logger.error("upload img fail");
        }
        return imgResult;
    }
}
