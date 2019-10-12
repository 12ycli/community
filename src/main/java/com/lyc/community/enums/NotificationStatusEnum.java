package com.lyc.community.enums;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-11 08:23
 **/
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status){
        this.status = status;
    }
}
