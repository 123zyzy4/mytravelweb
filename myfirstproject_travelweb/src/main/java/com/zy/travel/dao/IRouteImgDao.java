package com.zy.travel.dao;



import com.zy.travel.domain.RouteImg;

import java.util.List;


public interface IRouteImgDao {
    List<RouteImg> findByRid(int rid);
}
