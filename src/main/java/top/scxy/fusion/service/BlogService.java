package top.scxy.fusion.service;

import top.scxy.fusion.entity.Blog;
import top.scxy.fusion.entity.PageResult;

import java.util.List;

public interface BlogService {
    /*
    * @Description: 添加博客，返回博客ID
    * @Param: [title, content, author_id]
    * @return: java.lang.Integer
    * */
    Integer addBlog(String title, String content, Integer author_id);
    /*
    * @Description: 通过用户ID查询博客列表
    * @Param: [id]
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    PageResult<Blog> getBlogsByUserId(Integer id, Integer pageNum);
    /*
    * @Description: 通过博客ID查询博客
    * @Param: [id]
    * @return: top.scxy.fusion.entity.Blog
    * */
    Blog getBlogById(Integer id);
    /*
    * @Description: 获取所有博客
    * @Param: []
    * @return: List<Blog>
    * */
    List<Blog> getAllBlog();
    /*
    * @Description: 更新博客，返回博客ID
    * @Param: [blog]
    * @return: java.lang.Integer
    * */
    Integer updateBlog(Blog blog);
    /*
    * @Description: 删除博客
    * @Param: [id]
    * */
    void deleteBlogById(Integer id);
    /*
    * @Description: 更新博客浏览量
    * @Param: [id]
    * */
    void updateBlogViewCount(Integer id);
    /*
    * @Description: 更新博客评论量
    * @Param: [id]
    * */
    void updateBlogCommentCount(Integer id);
    /*
    * @Description: 获取博客总数
    * @Param: []
    * @return: java.lang.Integer
    * */
    Integer getBlogCount();
    /*
    * @Description: 根据博客ID获取用户ID
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    Integer getUserIdByBlogId(Integer id);
    /*
    * @Description: 根据评论ID获取博客作者ID
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    Integer getUserIdByCommentId(Integer id);
    /*
    * @Description: 获取最热门的博客，前10
    * @Param: []
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    List<Blog> getHotBlogs();
    /*
    * @Description: 获取按时间排序的博客
    * @Param: [pageNum]
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    PageResult<Blog> getBlogsByTime(Integer pageNum);
    /*
    * @Description: 搜索博客
    * @Param: [pageNum, keyword]
    * @return: top.scxy.fusion.entity.PageResult<top.scxy.fusion.entity.Blog>
    * */
    PageResult<Blog> searchBlog(Integer pageNum, String keyword);
}
