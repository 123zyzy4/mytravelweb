package com.zy.travel.service;


import com.zy.travel.domain.PageBean;
import com.zy.travel.domain.Route;


public interface IRouteService {
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    Route findOne(String rid);
}