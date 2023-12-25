package top.scxy.fusion.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.scxy.fusion.entity.Comment;
import top.scxy.fusion.entity.UserInfo;
import top.scxy.fusion.service.BlogService;
import top.scxy.fusion.service.CommentService;
import top.scxy.fusion.service.UserService;

import java.util.*;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final BlogService blogService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public CommentController(CommentService commentService, BlogService blogService, UserService userService) {
        this.commentService = commentService;
        this.blogService = blogService;
        this.userService = userService;
    }
    /*
    * @Description: 添加评论
    * @Param: [blogId, session, content, parentId]
    * @return: java.lang.String
    * */
    @PostMapping("/comment")
    @ResponseBody
    public String addComment(@RequestParam("blogId") Integer blogId, HttpSession session, @RequestParam("content") String content,
                             @RequestParam("parentId") Integer parentId) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "请先登录";
        }
        commentService.addComment(blogId, userId, content, new Date(), parentId);
        return "评论成功";
    }
    /*
    * @Description: 删除评论
    * @Param: [commentId, session]
    * @return: java.lang.String
    * */
    @DeleteMapping("/comment")
    @ResponseBody
    public String deleteComment(@RequestParam("commentId") Integer commentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "请先登录";
        }
        if (!Objects.equals(blogService.getUserIdByCommentId(commentId), userId)) {
            return "无权限";
        }
        commentService.deleteCommentById(commentId);
        return "删除成功";
    }
    /*
    * @Description: 获取评论列表
    * @Param: [blogId]
    * @return: java.util.List<top.scxy.fusion.entity.Comment>
    * */
    @GetMapping("/comments")
    @ResponseBody
    public List<Comment> getComment(@RequestParam("blogId") Integer blogId) {
        // 获取评论列表，并对每个评论的用户信息进行填充
        List<Comment> comments = commentService.getCommentByBlogId(blogId);
        List<Integer> userIds = commentService.getUserIdByBlogId(blogId);
        Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIds(userIds);
        for(Comment comment: comments) {
            comment.setUser_nick_name(userInfoMap.get(comment.getUser_id()).getNickname());
            comment.setUser_avatar(userInfoMap.get(comment.getUser_id()).getAvatar());
//            if(comment.getParent_id() != -1)  {
//                comment.setParent_nick_name(userInfoMap.get(comment.getParent_id()).getNickname());
//            }
        }
        // 处理父评论，parent_id为-1的是父评论
        for (Comment comment : comments) {
            if (comment.getParent_id() == -1) {
                // 获取父评论的所有子评论
                List<Comment> reply_comments = comments.stream().filter(c -> Objects.equals(c.getParent_id(), comment.getId()))
                        .toList();
                List<Comment> commentList = new ArrayList<>();
                for(Comment comment1: reply_comments) {
                    commentList.add(comment1);
                    resolveChildComment(comments, comment1.getId(), commentList);
                }
                comment.setReply_comments(commentList);
            }
        }
        return comments.stream().filter(c -> Objects.equals(c.getParent_id(), -1)).toList();
    }
    public void resolveChildComment(List<Comment> comments, Integer id, List<Comment> commentList) {
        List<Comment> reply_comments = comments.stream().filter(c -> Objects.equals(c.getParent_id(), id)).toList();
        if (reply_comments.size()>0) {
            for (Comment comment: reply_comments) {
                commentList.add(comment);
                resolveChildComment(comments, comment.getId(), commentList);
            }
        }
    }
    /*
    * @Description: 判断是否有权限删除评论
    * @Param: [blogId, session]
    * @return: java.lang.String
    * */
    @GetMapping("/commentAuth")
    @ResponseBody
    public String commentAuth(@RequestParam("blogId") Integer blogId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "请先登录";
        }
        if (!Objects.equals(blogService.getUserIdByBlogId(blogId), userId)) {
            return "无权限";
        }
        return "有权限";
    }
}
