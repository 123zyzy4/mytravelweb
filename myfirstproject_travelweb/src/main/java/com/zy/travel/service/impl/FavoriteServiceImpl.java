package com.zy.travel.service.impl;


import com.zy.travel.dao.IFavoriteDao;
import com.zy.travel.dao.impl.FavoriteDaoImpl;
import com.zy.travel.domain.Favorite;
import com.zy.travel.service.IFavoriteService;


public class FavoriteServiceImpl implements IFavoriteService {
    private IFavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
