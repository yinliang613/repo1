package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据线路rid查询RouteImg集合
     * @return
     */
    public List<RouteImg> findByrid(int rid);
}
