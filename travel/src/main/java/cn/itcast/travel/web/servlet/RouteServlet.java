package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavouriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavouriteServiceImpl;
import cn.itcast.travel.service.impl.RouterServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //当前页码数
        String currentPageStr = request.getParameter("currentPage");
        //每页的条目数
        String pageSizeStr = request.getParameter("pageSize");
        //获取路线名称
        String rname = request.getParameter("rname");
        //System.out.println(rname);

        //类别id
        String cidStr = request.getParameter("cid");
        int cid =5;
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //当前页码默认为1
        int currentPage = 1;
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }
        //每页条目默认为5
        int pageSize = 5;
        if (pageSizeStr != null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //调用service查询
        RouteService service = new RouterServiceImpl();
        PageBean<Route> pageBean = service.pageQuery(cid,currentPage,pageSize,rname);
        //响应json数据
        writeValue(pageBean,response);

    }

    /**
     * 查询线路详情
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路的rid
        String rid = request.getParameter("rid");
        //调用service 查询
        RouteService service = new RouterServiceImpl();
        Route route = service.findOne(rid);
        //写回route对象
        writeValue(route,response);

    }

    /**
     * 判断当前用户是否收藏该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavourite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int uid;
        if(user == null){
            uid = 0;
        }else{//用户已经登录
            uid=user.getUid();
        }
        FavouriteService service = new FavouriteServiceImpl();
        boolean favourite = service.isFavourite(Integer.parseInt(rid), uid);
        writeValue(favourite,response);

    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int uid;
        if(user == null){//用户未登录
            return;
        }else{//用户已经登录
            uid=user.getUid();
        }
        FavouriteService service = new FavouriteServiceImpl();
        service.addFavorite(Integer.parseInt(rid),uid);

    }
    


}
