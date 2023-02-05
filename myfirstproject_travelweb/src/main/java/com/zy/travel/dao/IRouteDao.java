package com.zy.travel.dao;



import com.zy.travel.domain.Route;

import java.util.List;


public interface IRouteDao {

    int findTotalCount(int cid,String rname);

    List<Route> findByPage(int cid, int start, int pageSize,String rname);
    Route findOne(int rid);




}
