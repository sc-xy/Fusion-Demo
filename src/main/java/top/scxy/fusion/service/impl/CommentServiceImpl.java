package top.scxy.fusion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import top.scxy.fusion.entity.Comment;
import top.scxy.fusion.mapper.CommentMapper;
import top.scxy.fusion.service.CommentService;

import java.util.Date;
import java.util.List;

@Service
@Repository
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
    @Override
    public void deleteCommentByBlogId(Integer blogId) {
        commentMapper.deleteCommentByBlogId(blogId);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        commentMapper.deleteCommentById(commentId);
    }

    @Override
    public Integer addComment(Integer blogId, Integer userId, String content, Date createTime, Integer parentId) {
        Comment comment = new Comment();
        comment.setBlog_id(blogId);
        comment.setUser_id(userId);
        comment.setContent(content);
        comment.setCreate_time(createTime);
        comment.setParent_id(parentId);
        commentMapper.addComment(comment);
        return comment.getId();
    }

    @Override
    public List<Comment> getCommentByBlogId(Integer blogId) {
        return commentMapper.getCommentByBlogId(blogId);
    }

    @Override
    public List<Comment> getCommentByParentId(Integer parentId) {
        return commentMapper.getCommentByParentId(parentId);
    }

    @Override
    public void deleteCommentByParentId(Integer parentId) {
        commentMapper.deleteCommentByParentId(parentId);
    }

    @Override
    public List<Integer> getUserIdByBlogId(Integer blogId) {
        return commentMapper.getUserIdByBlogId(blogId);
    }

}
