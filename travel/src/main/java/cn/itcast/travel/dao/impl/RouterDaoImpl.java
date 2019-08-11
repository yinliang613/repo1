package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouterDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouterDaoImpl implements RouterDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据cid查询总记录数
     */
    @Override
    public int findTotalcount(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<String> param = new ArrayList<>();
        if(cid != 0){
            sb.append(" and cid = ? ");
            param.add(String.valueOf(cid));
        }
        if(rname != null){
            sb.append(" and rname like ? ");

            param.add("%"+rname+"%");
        }
        sql = sb.toString();

        return template.queryForObject(sql,Integer.class, param.toArray());

    }
    /**
     * 跟据 cid 开始位置start 每页显示的条数pagesize 分页查询，返回List<Route>对象
     *
     */
    @Override
    public List<Route> pageQuery(int cid, int start, int pagesize, String rname) {
        //String sql = "select * from tab_route where cid = ? limit ?,?";
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List param = new ArrayList<>();
        if(cid != 0){
            sb.append(" and cid = ? ");
            param.add(cid);
        }
        if(rname != null){
            sb.append(" and rname like ? ");

            param.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        param.add(start);
        param.add(pagesize);
        sql = sb.toString();

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),param.toArray());
        return list;
    }

    @Override
    public Route findByrid(int id) {
        String sql = "select * from tab_route where rid = ?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),id);
    }
}
