package com.maurice.community.service;

import com.maurice.community.dto.CommentCreateDTO;
import com.maurice.community.dto.CommentDTO;
import com.maurice.community.dto.QuestionDTO;
import com.maurice.community.entity.*;
import com.maurice.community.enums.CommentTypeEnum;
import com.maurice.community.exception.CustomizeErrorCode;
import com.maurice.community.exception.CustomizeException;
import com.maurice.community.mapper.CommentMapper;
import com.maurice.community.mapper.QuestionMapper;
import com.maurice.community.mapper.QuestionMapperEx;
import com.maurice.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Maurice
 * @Date: 2019/8/20
 * @Description:
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionMapperEx questionMapperEx;
    @Autowired
    private UserMapper userMapper;

    public List<CommentDTO> getCommentList(QuestionDTO questionDTO) {
        // 获取问题下的所有一级回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(questionDTO.getId())
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0){
            return new ArrayList<>();
        }

        // 获取回复问题的用户id列表
        Set<String> userIds = comments.stream().map(comment -> comment.getAccessId()).collect(Collectors.toSet());
        List<String> userAccessIds = new ArrayList<>();
        userAccessIds.addAll(userIds);

        // 获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccessIdIn(userAccessIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccessId(), user ->user));

        // 把用户对象放入CommentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getAccessId()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    @Transactional
    public void insert(CommentCreateDTO commentDTO) {
        if (commentDTO.getParentId() == null || commentDTO.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (commentDTO.getType() == null || !CommentTypeEnum.isExist(commentDTO.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setAccessId(commentDTO.getAccessId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        if (commentDTO.getType() == CommentTypeEnum.COMMENT.getType()) {
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andParentIdEqualTo(comment.getParentId());
            List<Comment> dbComment = commentMapper.selectByExample(commentExample);
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
        } else if (commentDTO.getType().equals(CommentTypeEnum.QUESTION.getType())) {
            Question dbQuestion = questionMapper.selectByPrimaryKey(commentDTO.getParentId());
            if (dbQuestion == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            dbQuestion.setCommentCount(1);
            questionMapperEx.incComment(dbQuestion);
        }
        commentMapper.insert(comment);
    }
}
