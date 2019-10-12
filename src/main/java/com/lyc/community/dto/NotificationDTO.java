package com.lyc.community.dto;

import com.lyc.community.model.Notification;
import com.lyc.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-11 19:56
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String type;
    private String typeName;
    private Long outerid;
}
