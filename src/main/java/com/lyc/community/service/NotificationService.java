package com.lyc.community.service;

import com.lyc.community.dto.NotificationDTO;
import com.lyc.community.dto.PageDTO;
import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.enums.NotificationStatusEnum;
import com.lyc.community.enums.NotificationTypeEnum;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import com.lyc.community.mapper.NotificationMapper;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-11 19:59
 **/
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PageDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {

        // List<Question> questions = questionMapper.listByUserId(userId,size*(page-1),size);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(size*(page-1),size));

        if(notifications==null||notifications.size()==0){
            return new PageDTO<NotificationDTO>();
        }

        example = new NotificationExample();
        example.createCriteria().andNotifierEqualTo(userId);
        int count = (int) notificationMapper.countByExample(example);
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>(count,page,size);

        // Set<Long> notifierIdsInSet = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
        // List<Long> notifierIdsInList = new ArrayList<>(notifierIdsInSet);
        // UserExample userExample = new UserExample();
        // userExample.createCriteria().andIdIn(notifierIdsInList);
        // List<User> users = userMapper.selectByExample(userExample);
        // Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<NotificationDTO> notificationDTOs = notifications.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTO.setOuterTitle(notification.getOuterName());
            return notificationDTO;
        }).collect(Collectors.toList());

        pageDTO.setData(notificationDTOs);
        return pageDTO;
    }

    public Long getUnreadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if(notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
