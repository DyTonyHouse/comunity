package com.maurice.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Maurice
 * @Date: 2019/8/14
 * @Description:
 */
@Data
public class PaginationDTO {
    private Integer totalCount;
    private Integer currentPage;
    private Integer totalPage;
    private Boolean hasPrevious = true;
    private Boolean hasFirstPage = true;
    private Boolean hasNext = true;
    private Boolean hasEndPage = true;
    //可显示的页面序号
    private List<Integer> pages = new ArrayList<>();
    private List<QuestionDTO> questionDTOS;

    public void setPagination(Integer totalCount, Integer currentPage, Integer size) {
        //由页面问题个数与问题总个数求出需要几个页面
        this.setTotalCount(totalCount);
        this.setTotalPage(this.getTotalCount() % size == 0? this.getTotalCount() / size :(this.getTotalCount() / size) + 1);
        System.out.println("TotalPage>>>>>>>>>>>>>>>>>>>>>>>>>>>"+this.getTotalPage());
        if (currentPage <= 1){
            this.setCurrentPage(1);
            this.setHasPrevious(false);
            this.setHasFirstPage(false);

        }else if (currentPage >= this.getTotalPage()){
            this.setCurrentPage(this.getTotalPage());
            this.setHasNext(false);
            this.setHasEndPage(false);
        }else {
            this.setCurrentPage(currentPage);;
        }

        pages.add(this.getCurrentPage());

        // 将当前页的左右三页加入 pages 列表
        for (int i = 1; i<=3; i++){
            if (this.getCurrentPage() - i > 0){
                pages.add(0,this.getCurrentPage() - i);
            }

            if (this.getCurrentPage() + i <= this.getTotalPage()) {
                pages.add(this.getCurrentPage() + i);
            }
        }

        if (pages.contains(1)){
            this.setHasFirstPage(false);
        }
        if (pages.contains(this.totalPage)) {
            this.setHasEndPage(false);
        }

    }
}
