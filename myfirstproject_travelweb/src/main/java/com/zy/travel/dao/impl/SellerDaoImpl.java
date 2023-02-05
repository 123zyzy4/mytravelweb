package com.zy.travel.dao.impl;


import com.zy.travel.dao.ISellerDao;
import com.zy.travel.domain.Seller;
import com.zy.travel.utils.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class SellerDaoImpl implements ISellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";

        return template.queryForObject(sql,
                new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }



}
