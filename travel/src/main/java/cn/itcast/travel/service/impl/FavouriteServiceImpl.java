package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavouriteService;

public class FavouriteServiceImpl implements FavouriteService {
    private FavouriteDao favouriteDao= new FavouriteDaoImpl();
    @Override
    public boolean isFavourite(int rid, int uid) {
        Favorite favorite = favouriteDao.findByRidAndUid(rid,uid);
        return favorite != null;//查询到favourite返回ture,否则false
    }

    @Override
    public void addFavorite(int rid, int uid) {
        favouriteDao.add(rid,uid);
    }
}
