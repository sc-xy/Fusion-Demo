package top.scxy.fusion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.Blog;
import top.scxy.fusion.entity.PageResult;
import top.scxy.fusion.entity.ResultData;
import top.scxy.fusion.mapper.BlogMapper;
import top.scxy.fusion.service.BlogService;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    private BlogMapper blogMapper;
    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }
    @Override
    public Integer addBlog(String title, String content, Integer author_id) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setAuthor_id(author_id);
        blog.setView_count(0);
        blog.setComment_count(0);
        blog.setCreate_time(new java.util.Date());
        blog.setUpdate_time(new java.util.Date());
        blogMapper.insertBlog(blog);
        return blog.getId();
    }
    @Override
    public PageResult<Blog> getBlogsByUserId(Integer id, Integer pageNum) {
        Integer totalCount = blogMapper.selectBlogCountByUserId(id);
        List<Blog> blogs = blogMapper.selectBlogsByUserId(id, (pageNum - 1) * UtilConstant.PageSize, UtilConstant.PageSize);
        return new PageResult<>(pageNum, UtilConstant.PageSize, totalCount, blogs);
    }
    @Override
    public Blog getBlogById(Integer id) {
        return blogMapper.selectBlogById(id);
    }
    @Override
    public List<Blog> getAllBlog(){
        return blogMapper.getAllBlogs();
    }
    @Override
    public Integer updateBlog(Blog blog) {
        blog.setUpdate_time(new java.util.Date());
        return blogMapper.updateBlog(blog);
    }
    @Override
    public void deleteBlogById(Integer id) {
        blogMapper.deleteBlogById(id);
    }
    @Override
    public void updateBlogViewCount(Integer id) {
        blogMapper.updateBlogViewCount(id);
    }
    @Override
    public void updateBlogCommentCount(Integer id) {
        blogMapper.updateBlogCommentCount(id);
    }
    @Override
    public Integer getBlogCount() {
        return blogMapper.getBlogCount();
    }
    @Override
    public Integer getUserIdByBlogId(Integer id) {
        return blogMapper.getUserIdByBlogId(id);
    }

    @Override
    public Integer getUserIdByCommentId(Integer id) {
        return blogMapper.getUserIdByCommentId(id);
    }
    @Override
    public List<Blog> getHotBlogs() {
        return blogMapper.getHotBlogs();
    }
    @Override
    public PageResult<Blog> getBlogsByTime(Integer pageNum) {
        Integer totalCount = blogMapper.getBlogCount();
        List<Blog> blogs = blogMapper.getNewBlogs((pageNum - 1) * UtilConstant.PageSize, UtilConstant.PageSize);
        return new PageResult<>(pageNum, UtilConstant.PageSize, totalCount, blogs);
    }
    @Override
    public PageResult<Blog> searchBlog(Integer pageNum, String keyword){
        Integer totalCount = blogMapper.searchBlogCount(keyword);
        List<Blog> blogs = blogMapper.searchBlog(keyword, (pageNum - 1) * UtilConstant.PageSize, UtilConstant.PageSize);
        return new PageResult<>(pageNum, UtilConstant.PageSize, totalCount, blogs);
    }

}
