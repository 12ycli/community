package com.lyc.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-04 12:18
 **/
@Data
public class PageDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public PageDTO() {
    }

    public PageDTO(int count, int page, int size) {
        if(count==0){
            showPrevious = false;
            showFirstPage = false;
            showNext = false;
            showEndPage = false;
            page = 0;
            totalPage = 0;
            return;
        }

        Integer totalPage = 0;
        if(count%size==0){
            totalPage = count/size;
        }else{
            totalPage = count/size + 1;
        }

        this.totalPage = totalPage;

        if(page<=0){
            page=0;
        }

        if(page>totalPage){
            page=totalPage;
        }

        pages.add(page);
        for(int i=1;i<=3;i++){
            int left = page-i;
            if(left>0){
                pages.add(0,left);
            }

            int right = page+i;
            if(right<=totalPage){
                pages.add(right);
            }
        }

        //是否展示上一页和下一页
        showPrevious=page!=1;
        showNext=page!=totalPage;

        //是否展示第一页
        showFirstPage=!pages.contains(1);
        showEndPage=!pages.contains(totalPage);

        this.page = page;
    }
}
