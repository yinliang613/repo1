package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    /**
     * 完成方法的分发
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径中的方法名
        String uri = req.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象转为json格式并写入输出流
     * @param obj
     * @param response
     */
    public void writeValue(Object obj,HttpServletResponse response){

        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象序列化为json字符串
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            s = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
