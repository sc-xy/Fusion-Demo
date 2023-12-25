package top.scxy.fusion.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.scxy.fusion.entity.Comment;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface CommentService {
    /*
    * @Description: 根据博客id删除评论
    * @Param: [blogId]
    * @return: void
    * */
    void deleteCommentByBlogId(Integer blogId);
    /*
    * @Description: 根据评论id删除评论
    * @Param: [commentId]
    * @return: void
    * */
    void deleteCommentById(Integer commentId);
    /*
    * @Description: 添加评论，返回主键
    * @Param: [blogId, userId, content, createTime, parentId]
    * @return: java.lang.Integer
    * */
    Integer addComment(Integer blogId, Integer userId, String content, Date createTime, Integer parentId);
    /*
    * @Description: 根据博客id返回评论列表
    * @Param: [blogId]
    * @return: java.util.List<top.scxy.fusion.entity.Comment>
    * */
    List<Comment> getCommentByBlogId(Integer blogId);
    /*
    * @Description: 根据上级评论id返回评论列表
    * @Param: [parentId]
    * @return: java.util.List<top.scxy.fusion.entity.Comment>
    * */
    List<Comment> getCommentByParentId(Integer parentId);
    /*
    * @Description: 根据上级评论id删除评论
    * @Param: [parentId]
    * @return: void
    * */
    void deleteCommentByParentId(Integer parentId);
    /*
    * @Description: 根据博客id返回所有评论用户id
    * @Param: [blogId]
    * @return: java.util.List<java.lang.Integer>
    * */
    List<Integer> getUserIdByBlogId(Integer blogId);
}
