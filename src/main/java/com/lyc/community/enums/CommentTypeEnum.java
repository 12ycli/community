package com.lyc.community.enums;

//评论的类型
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum:CommentTypeEnum.values()){
            if(commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
