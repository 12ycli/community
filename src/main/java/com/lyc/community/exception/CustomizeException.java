package com.lyc.community.exception;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-06 09:40
 **/
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode){
        message = errorCode.getMessage();
        code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
