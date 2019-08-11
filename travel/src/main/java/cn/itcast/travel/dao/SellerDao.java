package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据卖家sid查询卖家详情
     * @return
     */
    public Seller findBySid(int sid);
}
