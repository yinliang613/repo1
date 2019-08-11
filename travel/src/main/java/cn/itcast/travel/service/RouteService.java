package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     *
     * @param cid 旅游类别id
     * @param currentPage 当前页码数
     * @param pageSize  每页显示的条目数
     * @param rname
     * @return  返回一个pageBean对象
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    public  Route findOne(String rid);
}
