package com.lyc.community.exception;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-06 10:22
 **/
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    CREATOR_IS_EMPTY(3001,"问题创建者为空，question数据库数据存在问题"),
    QUESTION_NOT_FOUND(2001,"问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请先登陆再重试"),
    SYSTEM_ERROR(2004,"服务器冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005,"评论内容错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"你找的评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢？！"),
    NOTIFICATION_NOT_FOUND(2009,"找不到通知"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败");

    private String message;
    private Integer code;

    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
