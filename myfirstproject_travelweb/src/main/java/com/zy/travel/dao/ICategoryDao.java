package com.zy.travel.dao;



import com.zy.travel.domain.Category;

import java.util.List;

/**
 * @Author: 杨朝阳
 * @Version: V1.0
 * @Date: 2019/2/12 18:08
 * @Description: TODO
 **/
public interface ICategoryDao {
    List<Category> findAll();
}
