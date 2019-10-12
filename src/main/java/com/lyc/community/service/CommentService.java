package com.lyc.community.service;

import com.lyc.community.dto.CommentDTO;
import com.lyc.community.enums.CommentTypeEnum;
import com.lyc.community.enums.NotificationStatusEnum;
import com.lyc.community.enums.NotificationTypeEnum;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import com.lyc.community.mapper.*;
import com.lyc.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-06 21:39
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentMapperExtension commentMapperExtension;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionMapperExtension questionMapperExtension;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentor){
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else {
                commentMapper.insert(comment);
                commentMapperExtension.increaseCommentCount(comment.getParentId());

                Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
                if (question == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                } else {
                    createNotification(comment, dbComment.getCommentator(), question.getTitle(), commentor.getName(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
                }
            }
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
                questionMapperExtension.increaseCommentCount(question.getId());

                createNotification(comment, question.getCreator(), question.getTitle(), commentor.getName(),  NotificationTypeEnum.REPLY_QUESTION, question.getId());
            }
        }
    }

    public void createNotification(Comment comment, Long receiver, String questionTitle, String notifierName, NotificationTypeEnum notificationType, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        // 评论给的评论的id
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setOuterName(questionTitle);
        notification.setNotifierName(notifierName);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum commentType) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().
                andParentIdEqualTo(id).
                andTypeEqualTo(commentType.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }

        Set<Long> temp = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> commentators = temp.stream().collect(Collectors.toList());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(commentators);
        List<User> temp2 = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = temp2.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> CommentDTOs = comments.stream().map(
                comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    BeanUtils.copyProperties(comment, commentDTO);
                    return commentDTO;
                }
        ).collect(Collectors.toList());

        return CommentDTOs;
    }
}
