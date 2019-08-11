package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        Jedis jedis = new Jedis();
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeByScoreWithScores("category", 0, -1);
        List<Category> list = null;
        //若缓存中没有数据，则调用数据库进行查询并将结果存入Redis
        if(categorys == null || categorys.size() ==0){
            list = categoryDao.findAll();
            for (Category category1 : list) {
                jedis.zadd("category",category1.getCid(),category1.getCname());
            }

        }else{//缓存中有数据则将数据存入list
            list = new ArrayList<Category>();

            for (Tuple tuple : categorys) {
                Category c = new Category();
                c.setCid((int)tuple.getScore());
                c.setCname(tuple.getElement());
                list.add(c);
            }

        }
        return list;
    }
}
