package top.scxy.fusion.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.scxy.fusion.entity.Blog;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    /*
    * @Description: 添加博客，返回博客ID
    * @Param: [blog]
    * */
    @Insert("insert into blog(title, content, author_id, view_count, comment_count, create_time, update_time) values(#{title}, #{content}, #{author_id}, #{view_count}, #{comment_count}, #{create_time}, #{update_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertBlog(Blog blog);
    /*
    * @Description: 通过用户ID查询博客列表
    * @Param: [id, pageNum, pageSize]
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    @Select("select id, title, update_time, substring(content, 1, 15) as view_content from blog where author_id = " +
            "#{id} order by update_time desc limit #{pageStart}, #{pageSize}")
    java.util.List<Blog> selectBlogsByUserId(Integer id, Integer pageStart, Integer pageSize);
    /*
    * @Description: 根据用户ID查询博客数量
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    @Select("select count(*) from blog where author_id = #{id}")
    Integer selectBlogCountByUserId(Integer id);
    /*
    * @Description: 根据博客ID查询博客
    * @Param: [id]
    * @return: top.scxy.fusion.entity.Blog
    * */
    @Select("select * from blog where id = #{id}")
    Blog selectBlogById(Integer id);
    /*
    * @Description: 更新博客，返回博客ID
    * @Param: [blog]
    * @return: java.lang.Integer
    * */
    @Insert("update blog set title = #{title}, content = #{content}, update_time = #{update_time} where id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer updateBlog(Blog blog);
    /*
    * @Description: 删除博客
    * @Param: [id]
    * */
    @Insert("delete from blog where id = #{id}")
    void deleteBlogById(Integer id);
    /*
    * @@Description: 更新博客浏览量
    * @Param: [id]
    * */
    // TODO 使用Redis缓存，MQ异步更新
    @Insert("update blog set view_count = view_count + 1 where id = #{id}")
    void updateBlogViewCount(Integer id);
    /*
    * @Description: 更新博客评论量
    * @Param: [id]
    * */
    // TODO 使用Redis缓存，MQ异步更新
    @Insert("update blog set comment_count = comment_count + 1 where id = #{id}")
    void updateBlogCommentCount(Integer id);
    /*
    * @Description: 获取所有博客
    * @Param: []
    * @return: List<BLog>
    * */
    @Select("select * from blog")
    List<Blog> getAllBlogs();
    /*
    * @Description: 根据博客ID查询用户ID
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    @Select("select author_id from blog where id = #{id}")
    Integer getUserIdByBlogId(Integer id);
    /*
    * @Description: 获取博客总数
    * @Param: []
    * @return: java.lang.Integer
    * */
    @Select("select count(*) from blog")
    Integer getBlogCount();
    /*
    * @Description: 根据评论ID获取博客作者ID
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    @Select("select author_id from blog where id = (select blog_id from comment where id = #{id})")
    Integer getUserIdByCommentId(Integer id);
    /*
    * @Description: 获取最热门的博客，前10
    * @Param: []
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    @Select("select id, title, substring(content, 0, 15) as view_content, create_time, update_time, view_count, comment_count from blog " +
            "order by view_count + comment_count desc limit 10")
    List<Blog> getHotBlogs();
    /*
    * @Description: 获取按时间排序的博客
    * @Param: []
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    @Select("select id, title, substring(content, 1, 15) as view_content, create_time, update_time, view_count, comment_count from blog " +
            "order by update_time desc limit #{pageStart}, #{pageSize}")
    List<Blog> getNewBlogs(Integer pageStart, Integer pageSize);
    /*
    * @Description: 搜索博客
    * @Param: [keyword, pageStart, pageSize]
    * @return: java.util.List<top.scxy.fusion.entity.Blog>
    * */
    @Select("select id, title, substring(content, 1, 15) as view_content, create_time, update_time, view_count, comment_count from blog " +
            "where title like concat('%', #{keyword}, '%') or content like concat('%', #{keyword}, '%') limit #{pageStart}, #{pageSize}")
    List<Blog> searchBlog(String keyword, Integer pageStart, Integer pageSize);
    /*
    * @Description: 搜索博客数量
    * @Param: [keyword]
    * @return: java.lang.Integer
    * */
    @Select("select count(*) from blog where title like concat('%', #{keyword}, '%') or content like concat('%', #{keyword}, '%')")
    Integer searchBlogCount(String keyword);
}
