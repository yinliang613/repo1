package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.RouterDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.RouterDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouterServiceImpl implements RouteService {
    private RouterDao routerDao = new RouterDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavouriteDao favouriteDao = new FavouriteDaoImpl();
    /**
     *
     * @param cid 旅游类别id
     * @param currentPage 当前页码数
     * @param pageSize  每页显示的条目数
     * @param rname
     * @return  返回一个pageBean对象
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //创建一个PageBean对象，对其进行封装
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //获取总条目数
        int totalcount = routerDao.findTotalcount(cid,rname);
        pageBean.setTotalCount(totalcount);
        //总页码数 = 总条目数/每页显示的条目数 向上取整
        int totalPage = (int)Math.ceil((double) totalcount/pageSize);
        pageBean.setTotalPage(totalPage);

        //设置每页显示的数据集合
        //数据起始位置
        int start=(currentPage-1)*pageSize;
        List<Route> list = routerDao.pageQuery(cid,start,pageSize,rname);
        pageBean.setList(list);
        return pageBean;
    }

    /**
     * 根据rid查询线路详情
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        int id = Integer.parseInt(rid);
        //创建route对象并初始化
        Route route = routerDao.findByrid(id);
        //设置商品的图片集合
        route.setRouteImgList(routeImgDao.findByrid(route.getRid()));
        //设置商品的卖家信息
        route.setSeller(sellerDao.findBySid(route.getSid()));
        route.setCount(favouriteDao.findCoutByRid(route.getRid()));
        return route;
    }

}
