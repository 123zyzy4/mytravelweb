package com.zy.travel.service.impl;



import com.zy.travel.dao.IFavoriteDao;
import com.zy.travel.dao.IRouteDao;
import com.zy.travel.dao.IRouteImgDao;
import com.zy.travel.dao.ISellerDao;
import com.zy.travel.dao.impl.FavoriteDaoImpl;
import com.zy.travel.dao.impl.RouteDaoImpl;
import com.zy.travel.dao.impl.RouteImgDaoImpl;
import com.zy.travel.dao.impl.SellerDaoImpl;
import com.zy.travel.domain.PageBean;
import com.zy.travel.domain.Route;
import com.zy.travel.domain.RouteImg;
import com.zy.travel.domain.Seller;
import com.zy.travel.service.IRouteService;

import java.util.List;

public class RouteServiceImpl implements IRouteService {
    private IRouteDao routeDao = new RouteDaoImpl();
    private IRouteImgDao routeImgDao=new RouteImgDaoImpl();
    private ISellerDao sellerDao=new SellerDaoImpl();
    private IFavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();

        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);

        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pageBean.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));

        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);

        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }


}
