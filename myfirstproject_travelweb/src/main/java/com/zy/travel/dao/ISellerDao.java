package com.zy.travel.dao;


import com.zy.travel.domain.Seller;

/**
 * @Author: 杨朝阳
 * @Version: V1.0
 * @Date: 2019/3/11 22:03
 * @Description: TODO
 **/
public interface ISellerDao {
    Seller findBySid(int sid);
}
