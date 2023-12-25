package top.scxy.fusion.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.scxy.fusion.annotation.IgnoreResponseAdvice;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.Blog;
import top.scxy.fusion.entity.ImgResult;
import top.scxy.fusion.entity.PageResult;
import top.scxy.fusion.exception.BlogSaveException;
import top.scxy.fusion.service.BlogService;
import top.scxy.fusion.service.UploadService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BlogController {
    private final UploadService uploadService;
    private final BlogService blogService;
    @Autowired
    public BlogController(UploadService uploadService, BlogService blogService) {
        this.uploadService = uploadService;
        this.blogService = blogService;
    }
    /*
    * @Description: 博客编辑界面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/blogEdit")
    public String blogEdit() {
        return "blogEdit";
    }
    /*
    * @Description: 添加博客
    * @Param: [title, content, session]
    * @return: java.lang.String
    * */
    @PostMapping("/blog")
    @ResponseBody
    public String addBlog(@RequestParam("title") String title, @RequestParam("content") String content,
                          HttpSession session) throws BlogSaveException {
        Integer userId = (Integer) session.getAttribute("userId");
        Integer blogId = blogService.addBlog(title, content, userId);
        if (blogId != null) {
            return  "添加成功";
        }
        throw new BlogSaveException("保存失败");
    }
    /*
    * @Description: 博客首页页面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/blogIndex")
    public String blogIndex() {
        return "blogIndex";
    }
    /*
    * @Description: 博客搜索页面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/searchBlog")
    public String blogSearch() {
        return "searchBlog";
    }
    /*
    * @Description: 博客详情界面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/blog")
    public String blogDetail() {
        return "blog";
    }
    /*
    * @Description: 博客列表界面
    * @Param: []
    * @return: java.lang.String
    * */
    @GetMapping("/blogs")
    public String blogList() {
        return "blogs";
    }
    /*
    * @Description: 上传图片
    * @Param: [file, session]
    * @Return:
    * */
    @PostMapping("/uploadImg")
    @ResponseBody
    @IgnoreResponseAdvice
    public ImgResult uploadImg(@RequestParam("editormd-image-file") MultipartFile file, HttpSession session) throws IOException {
        String type = file.getContentType();
        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(java.time.LocalDate.now());
        String fileName = null;
        if (type != null) {
            fileName = UtilConstant.ImgNamePrefix + date + session.getAttribute("userId") +
                    "." + type.substring(type.lastIndexOf("/") + 1);
        }
        return uploadService.uploadImg(file.getInputStream(), fileName);
    }
    /*
    * @Description: 获取用户博客列表
    * @Param: [pageNum, session]
    * @return:
    * */
    @GetMapping("/blogList")
    @ResponseBody
    public PageResult<Blog> getBlogsByUserId(@RequestParam(defaultValue = "1", value = "pageNum")Integer pageNum ,
                                           HttpSession session) {
        return blogService.getBlogsByUserId((Integer) session.getAttribute("userId"), pageNum);
    }
    /*
    * @Description: 删除博客
    * @Param: [id]
    * @return: java.lang.String
    * */
    @DeleteMapping("/blog")
    @ResponseBody
    public String deleteBlog(@RequestParam("id") Integer id) {
        blogService.deleteBlogById(id);
        return "删除成功";
    }
    /*
    * @Description: 返回博客详情
    * @Param: [id]
    * @return: top.scxy.fusion.entity.Blog
    * */
    @GetMapping("/blogDetail")
    @ResponseBody
    public Blog getBlogById(@RequestParam("id") Integer id) {
        return blogService.getBlogById(id);
    }
    /*
    * @Description: 更新博客
    * @Param: [blog, session]
    * @return: java.lang.String
    * */
    @PutMapping("/blog")
    @ResponseBody
    public String updateBlog(@RequestParam("id") Integer id, @RequestParam("title") String title,
                             @RequestParam("content") String content, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "no permission";
        }
        else if (blogService.getUserIdByBlogId(id).equals(userId)) {
            Blog blog = new Blog();
            blog.setId(id);
            blog.setTitle(title);
            blog.setContent(content);
            blog.setAuthor_id(userId);
            blogService.updateBlog(blog);
            return "更新成功";
        }
        return "no permission";
    }
    /*
    * @Description: 获取博客列表，分为最新的和最热的，按照时间和浏览评论数排序，最热的只取前10个
    * @Param: [pageNum, type]
    * @return: top.scxy.fusion.entity.PageResult<top.scxy.fusion.entity.Blog>
    * */
    @GetMapping("/blogListByType")
    @ResponseBody
    public PageResult<Blog> getBlogListByType(@RequestParam(defaultValue = "1", value = "pageNum")Integer pageNum,
                                              @RequestParam("type") String type) {
        return null;
    }
    /*
    * @Description: 获取博客总数
    * @Param: []
    * @return: java.lang.Integer
    * */
    @GetMapping("/blogCount")
    @ResponseBody
    public Integer getBlogCount() {
        return blogService.getBlogCount();
    }
    /*
    * @Description: 获取最热门的博客，前10
    * @Param: []
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    @GetMapping("/hotBlog")
    @ResponseBody
    public List<Blog> getHotBlog() {
        return blogService.getHotBlogs();
    }
    /*
    * @Description: 获取按时间排序的博客
    * @Param: [pageNum]
    * @return: top.scxy.fusion.entity.PageResult<top.scxy.fusion.entity.Blog>
    * */
    @GetMapping("/blogListByTime")
    @ResponseBody
    public PageResult<Blog> getBlogListByTime(@RequestParam(defaultValue = "1", value = "pageNum")Integer pageNum) {
        return blogService.getBlogsByTime(pageNum);
    }
    /*
    * @Description: 搜索博客列表
    * @Param: [pageNum, keyword]
    * @return: top.scxy.fusion.entity.PageResult<top.scxy.fusion.entity.Blog>
    * */
    @GetMapping("/searchBlogList")
    @ResponseBody
    public PageResult<Blog> getSearchBlogList(@RequestParam(defaultValue = "1", value = "pageNum")Integer pageNum,
                                              @RequestParam("searchText") String keyword) {
        return blogService.searchBlog(pageNum, keyword);
    }

}
