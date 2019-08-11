package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求数据，封装成map
        Map<String, String[]> map = request.getParameterMap();
        //2.使用BeanUtils,封装成User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //验证验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//移除session保证一个验证码只能用一次
        //验证验证码是否错误
        if (checkCheckCode(response, check, checkcode_server)) return;

        //3.调用Service的注册方法
        //UserService service = new UserServiceImpl();
        Boolean flag = service.register(user);
        //4.设置注册结果对象info
        ResultInfo info = new ResultInfo();
        if (flag != null) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！用户名已经存在");
        }
        //5.使用jackson将info封装成json格式
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(info);
        //6.设置ContentType的mime类型
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    private boolean checkCheckCode(HttpServletResponse response, String check, String checkcode_server) throws IOException {
        if (checkcode_server == null || !check.equalsIgnoreCase(checkcode_server)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return true;

        }
        return false;
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        String auto = request.getParameter("auto");
        //封装user对象
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //验证验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//移除session保证一个验证码只能用一次
        if (checkCheckCode(response, check, checkcode_server)) return;
        //若自动登录，则将用户信息存入cookie

        if("on".equals(auto)){
            Cookie cookie = new Cookie("userinfo", URLEncoder.encode(user.getUsername(),"utf-8")+"#"+user.getPassword());
            cookie.setMaxAge(60*60*24*14);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);


        }
        //调用service的登录方法
        //UserService service =new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }else {
            if(u.getStatus().equals("N")){
                info.setFlag(false);
                info.setErrorMsg("用户未激活，请查看邮件进行激活！");
            }else{
                //将返回的用户对象存入session
                session.setAttribute("user",u);
                info.setFlag(true);
            }
        }
        //响应info对象
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),info);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        User user = (User)session.getAttribute("user");
        ObjectMapper mapper =new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        //将cookie销毁
        Cookie cookie = new Cookie("userinfo","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code != null){

            Boolean flag = service.active(code);
            String msg = null;
            if(flag){
                msg = "激活成功，请<a href = 'login.html'>登录</a>，或回到浏览器登录";
            }else{
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }else{
            return;
        }
    }
}
