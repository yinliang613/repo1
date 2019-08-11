package cn.itcast.travel.service;

public interface FavouriteService {
    /**
     * 判断用户是否登录
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavourite(int rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void addFavorite(int rid, int uid);
}
