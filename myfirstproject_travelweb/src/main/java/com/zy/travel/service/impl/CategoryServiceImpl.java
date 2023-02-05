package com.zy.travel.service.impl;


import com.zy.travel.dao.ICategoryDao;
import com.zy.travel.dao.impl.CategoryDaoImpl;
import com.zy.travel.domain.Category;
import com.zy.travel.service.ICategoryService;
import com.zy.travel.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CategoryServiceImpl implements ICategoryService {
    private ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs=null;
        if(category==null||category.size()==0){
//            System.out.println("find from mysql");
            cs=categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
//            System.out.println("find from redis");
            cs=new ArrayList<Category>();
            for (Tuple tuple :category) {
                Category eachcategory = new Category();
                eachcategory.setCname(tuple.getElement());
                eachcategory.setCid((int)tuple.getScore());
                cs.add(eachcategory);
            }
        }
        return cs;
    }
}
