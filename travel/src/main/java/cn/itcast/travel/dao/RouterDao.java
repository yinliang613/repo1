package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouterDao {
    /**
     * 根据cid查询总记录数
     */
    public int findTotalcount(int cid, String rname);

    /**
     * 跟据 cid 开始位置start 每页显示的条数pagesize 分页查询，返回List<Route>对象
     *
     */
    public List<Route> pageQuery(int cid, int start, int pagesize, String rname);

    public Route findByrid(int id);
}
