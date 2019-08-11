package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavouriteDao {
    /**
     * 通过rid和uid查询favourite
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid,int uid);

    /**
     * 通过rid查询收藏次数
     * @param rid
     * @return
     */
    public int findCoutByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(int rid, int uid);
}
