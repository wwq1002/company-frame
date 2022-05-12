package com.yingxue.lesson.utils;

import com.github.pagehelper.Page;
import com.yingxue.lesson.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: PageUtil
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public class PageUtil {
    private PageUtil(){}
    public static <T> PageVO getPageVo(List<T> list){
        PageVO<T> pageVO=new PageVO<>();
        if(list instanceof Page){
            Page page = (Page)list;
            pageVO.setTotalRows(page.getTotal());
            pageVO.setList(page.getResult());
            pageVO.setTotalPages(page.getPages());
            pageVO.setCurPageSize(page.size());
            pageVO.setPageNum(page.getPageNum());
            pageVO.setPageSize(page.getPageSize());
        }
        return pageVO;
    }
}
