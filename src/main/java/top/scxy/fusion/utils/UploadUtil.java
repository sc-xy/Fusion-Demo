package top.scxy.fusion.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.util.Auth;
import top.scxy.fusion.constant.UtilConstant;

public class UploadUtil {
    /*
    * @Description: 获取七牛云认证
    * @Param: []
    * @return: com.qiniu.util.Auth
    * */
    public static Auth getAuth() {
        return Auth.create(UtilConstant.QiniuAccessKey, UtilConstant.QiniuSecretKey);
    }
    /*
    * @Description: 获取配置类
    * @Param: []
    * @return: com.qiniu.storage.Configuration
    * */
    public static com.qiniu.storage.Configuration getConfiguration() {
        return new com.qiniu.storage.Configuration(com.qiniu.storage.Region.region2());
    }
    /*
    * @Description: 获取七牛云上传token
    * @Param: []
    * @return: java.lang.String
    * */
    public static String getUploadToken(String bucketName) {
        Auth auth = getAuth();
        return auth.uploadToken(bucketName);
    }
    /*
    * @Description: 获取七牛云上传管理器
    * @Param: []
    * @return: com.qiniu.storage.UploadManager
    * */
    public static com.qiniu.storage.UploadManager getUploadManager() {
        return new com.qiniu.storage.UploadManager(getConfiguration());
    }
    /*
    * @Description: 获取七牛云下载URL
    * @Param: [fileName]
    * @return: java.lang.String
    * */
    public static String getDownloadUrl(String fileName) throws QiniuException {
        DownloadUrl downloadUrl = new DownloadUrl(UtilConstant.QiniuFileUrlPrefix, false, fileName);
        long expireInSeconds = 1800;//0.5小时，可以自定义链接过期时间
        long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
        Auth auth = getAuth();
        return downloadUrl.buildURL(auth, deadline);
    }
    /*
    * @Description: 删除文件
    * @Param: [fileName]
    * @return: void
    * */
    public static Integer deleteFileByFileName(String fileName) {
        Auth auth = getAuth();
        com.qiniu.storage.Configuration cfg = getConfiguration();
        com.qiniu.storage.BucketManager bucketManager = new com.qiniu.storage.BucketManager(auth, cfg);
        try {
            bucketManager.delete(UtilConstant.QiniuFileBucket, fileName);
            return 1;
        } catch (QiniuException e) {
            return 0;
        }
    }
}
