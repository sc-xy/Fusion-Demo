package top.scxy.fusion.constant;

public interface UtilConstant {
    // 密码加密盐
    String BCryptSalt = "$2a$10$BEnZ3bP6oPOHl3CYS3gmwuZ/$x$y$f$u$s$i$o$n$";
    // Redis中存储的验证码前缀
    String EmailVerifyCodePrefix = "emailVerifyCode.";
    // Redis中存储的忘记密码验证码前缀
    String EmailForgetPasswordPrefix = "emailForgetPassword.";
    // Redis中存储的验证码过期时间
    Integer EmailVerifyCodeExpireTime = 5;
    // 邮件标题
    String EmailSubject = "Fusion验证码";
    // 邮件发送者
    String EmailFrom = "Fusion";
    // 图片上传路径
    String ImgUrlPrefix = "http://img.example.top/";
    // 图片上传路径前缀
    String ImgNamePrefix = "fusion_";
    // 分页大小
    Integer PageSize = 10;
    // Redis中存储的用户信息前缀，后跟用户ID
    String UserInfoPrefix = "fusion.userInfo.";
    // Redis中存储的用户ID前缀，后跟用户名
    String UserIdPrefix = "fusion.userId.";
    // 七牛云图片bucket名称
    String QiniuBucket = "tr-blog-img";
    // 七牛云文件bucket名称
    String QiniuFileBucket = "fusion-file";
    // 七牛云文件下载路径前缀
    String QiniuFileUrlPrefix = "file.example.top";
    String QiniuAccessKey = "EXAMPLEACCESSKEY";
    String QiniuSecretKey = "EXAMPLESECRETKEY";
}
