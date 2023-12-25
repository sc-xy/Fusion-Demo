package top.scxy.fusion.service;

import top.scxy.fusion.entity.ImgResult;

import java.io.InputStream;

public interface UploadService {
    /*
    * @Description: 上传图片到七牛云，参数为图片的输入流，文件名
    * @Param: [inputStream, fileName]
    * @return: top.scxy.fusion.entity.ImgResult
    * */
    ImgResult uploadImg(InputStream inputStream, String fileName);
}
