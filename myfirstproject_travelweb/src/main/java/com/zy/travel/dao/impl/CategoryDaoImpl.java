package com.zy.travel.dao.impl;



import com.zy.travel.dao.ICategoryDao;
import com.zy.travel.domain.Category;
import com.zy.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: 杨朝阳
 * @Version: V1.0
 * @Date: 2019/2/12 18:09
 * @Description: TODO
 **/
public class CategoryDaoImpl implements ICategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";

        return template.query(sql,
                new BeanPropertyRowMapper<Category>(Category.class));
    }
}
