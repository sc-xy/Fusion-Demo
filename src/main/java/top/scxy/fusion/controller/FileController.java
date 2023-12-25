package top.scxy.fusion.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.File;
import top.scxy.fusion.service.AsyncService;
import top.scxy.fusion.service.FileService;
import top.scxy.fusion.service.UserService;
import top.scxy.fusion.utils.UploadUtil;

@Controller
public class FileController {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final FileService fileService;
    private final UserService userService;
    private final AsyncService asyncService;
    @Autowired
    public FileController(FileService fileService, UserService userService, AsyncService asyncService) {
        this.fileService = fileService;
        this.userService = userService;
        this.asyncService = asyncService;
    }
    /*
    * @Description: 文件管理页面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/files")
    public String files() {
        return "files";
    }
    /*
    * @Description: 上传文件，同时添加文件信息
    * @Param: [file]
    * @return: java.lang.String
    * */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file")MultipartFile file, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            logger.error("upload file fail: user not login");
            return "fail";
        }
        File fileInfo = new File();
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setAuthor_id(userId);
        fileInfo.setCreate_time(new java.util.Date());
        Integer result = fileService.uploadFile(fileInfo);
        if (result == null) {
            logger.error("upload file fail: insert file info fail");
            return "fail";
        }
        String fileName = userService.getUsernameById(userId) + "/" + file.getOriginalFilename();
        UploadManager uploadManager = UploadUtil.getUploadManager();
        String fileToken = UploadUtil.getUploadToken(UtilConstant.QiniuFileBucket);
        try {
            uploadManager.put(file.getInputStream(), fileName, fileToken, null, null);
            logger.info("upload file success");
        } catch (Exception e) {
            logger.error("upload file fail"+ e.getMessage());
            return "fail";
        }
        return "success";
    }
    /*
    * @Description: 下载文件，返回文件下载URL
    * @Param: [id, session]
    * @return: java.lang.String
    * */
    @GetMapping("/download")
    @ResponseBody
    public String downloadFile(@RequestParam("fileId") Integer id, HttpSession session) throws QiniuException {
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = userService.getUsernameById(userId);
        String fileName = userName + "/" + fileService.getFileById(id).getName();
        return UploadUtil.getDownloadUrl(fileName);
    }
    @GetMapping("/download/{id}")
    @ResponseBody
    public String downloadFile(@RequestParam("fileId") Integer id, @PathVariable("id") Integer userId,
                               HttpSession session) throws QiniuException {
        String userName = userService.getUsernameById(userId);
        String fileName = userName + "/" + fileService.getFileById(id).getName();
        return UploadUtil.getDownloadUrl(fileName);
    }
    /*
    * @Description: 删除文件
    * @Param: [id, session]
    * @return: java.lang.String
    * */
    @DeleteMapping("/file")
    @ResponseBody
    public String deleteFile(@RequestParam("fileId") Integer id, HttpSession session) {
        logger.info("delete file: " + id);
        File file = fileService.getFileById(id);
        if (file == null) {
            logger.error("delete file fail: file not exist");
            return "fail";
        }
        Integer userId = (Integer) session.getAttribute("userId");
        if (!file.getAuthor_id().equals(userId)){
            logger.error("delete file fail: user not match");
            return "fail";
        }
        fileService.deleteFile(id);
        String fileName = file.getName();
        String userName = userService.getUsernameById(file.getAuthor_id());
        String key = userName + "/" + fileName;
        asyncService.deleteFileFromQiniu(key);
        return "success";
    }
    /*
    * @Description: 获取用户文件列表
    * @Param: [session, pageNum]
    * @return: top.scxy.fusion.entity.PageResult<top.scxy.fusion.entity.File>
    * */
    @GetMapping("/fileList")
    @ResponseBody
    public top.scxy.fusion.entity.PageResult<File> getFileList(HttpSession session, @RequestParam("pageNum") Integer pageNum) {
        Integer userId = (Integer) session.getAttribute("userId");
        return fileService.getFileListByUserId(userId, pageNum);
    }
    @GetMapping("/fileList/{id}")
    @ResponseBody
    public top.scxy.fusion.entity.PageResult<File> getFileList(@PathVariable("id") Integer userId,
                                                               @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        return fileService.getFileListByUserId(userId, pageNum);
    }
}
