package top.scxy.fusion.service;

import top.scxy.fusion.entity.File;
import top.scxy.fusion.entity.PageResult;

public interface FileService {
    /*
    * @Description: 上传文件
    * @Param: [file]
    * @return: java.lang.Integer
    * */
    Integer uploadFile(File file);
    /*
    * @Description: 删除文件
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    Integer deleteFile(Integer id);
    /*
    * @Description: 根据文件id获取文件
    * @Param: [id]
    * @return: top.scxy.fusion.entity.File
    * */
    File getFileById(Integer id);
    /*
    * @Description: 获取用户文件列表
    * @Param: [id, pageNum]
    * @return: PageResult<top.scxy.fusion.entity.File>
    * */
    PageResult<File> getFileListByUserId(Integer id, Integer pageNum);
}
