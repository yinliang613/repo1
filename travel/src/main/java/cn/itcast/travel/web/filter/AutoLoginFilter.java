package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null&&cookies != null){
            for (Cookie cookie : cookies) {
                if("userinfo".equals(cookie.getName())){
                    String vaule = cookie.getValue();
                    String[] split = vaule.split("#");
                    String username = URLDecoder.decode(split[0],"utf-8");
                    String password = split[1];
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    UserService service = new UserServiceImpl();
                    User loginuser = service.login(user);
                    if(loginuser != null){
                        session.setAttribute("user",loginuser);
                        response.sendRedirect(request.getContextPath()+"/index.html");
                        return;
                    }
                }

            }

        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
