package top.scxy.fusion.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.scxy.fusion.entity.Comment;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    /*
    * @Description: 根据博客id删除评论
    * @Param: [blogId]
    * @return: void
    * */
    @Delete("delete from comment where blog_id = #{blogId}")
    void deleteCommentByBlogId(Integer blogId);
    /*
    * @Description: 根据评论id删除评论
    * @Param: [commentId]
    * @return: void
    * */
    @Delete("delete from comment where id = #{commentId}")
    void deleteCommentById(Integer commentId);
    /*
    * @Description: 添加评论，返回主键
    * @Param: [blogId, userId, content, createTime, parentId]
    * @return: void
    * */
    @Insert("insert into comment(blog_id, user_id, content, create_time, parent_id) values" +
            "(#{blog_id}, #{user_id}, #{content}, #{create_time}, #{parent_id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addComment(Comment comment);
    /*
    * @Description: 根据博客id返回评论列表
    * @Param: [blogId]
    * @return: java.util.List<top.scxy.fusion.entity.Comment>
    * */
    @Select("select * from comment where blog_id = #{blog_id}")
    List<Comment> getCommentByBlogId(Integer blogId);
    /*
    * @Description: 根据上级评论id返回评论列表
    * @Param: [parentId]
    * @return: java.util.List<top.scxy.fusion.entity.Comment>
    * */
    @Select("select * from comment where parent_id = #{parent_id}")
    List<Comment> getCommentByParentId(Integer parentId);
    /*
    * @Description: 根据上级评论id删除评论
    * @Param: [parentId]
    * @return: void
    * */
    @Delete("delete from comment where parent_id = #{parent_id}")
    void deleteCommentByParentId(Integer parentId);
    /*
    * @Description: 根据博客id返回所有评论用户id
    * @Param: [blogId]
    * @return: java.util.List<java.lang.Integer>
    * */
    @Select("select user_id from comment where blog_id = #{blog_id}")
    List<Integer> getUserIdByBlogId(Integer blogId);
}
