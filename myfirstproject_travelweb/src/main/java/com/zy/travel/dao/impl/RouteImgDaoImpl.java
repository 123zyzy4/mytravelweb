package com.zy.travel.dao.impl;



import com.zy.travel.dao.IRouteImgDao;
import com.zy.travel.domain.RouteImg;
import com.zy.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class RouteImgDaoImpl implements IRouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";

        return template.query(sql,
                new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
