package MoviesServer.servlet;

import com.alibaba.fastjson.JSON;

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
     * 这个是HttpServlet中实现方法分发的方法（doPost，doGet等），重写该方法，定义自己的分发方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://localhost:8080/user/login 后面的 uri = /user/login
        String uri = req.getRequestURI();

        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);

        try {
            //获取方法对象的method方法
            //谁调用我？this我代表谁  public final 类<?> getClass()返回此Object的运行时类。
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //执行方法
                //暴力反射 method.setAccessible(true);用于当方法为私有的等等
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void write(Object obj,HttpServletResponse response) throws IOException {
        //将数据序列化为json写入response中
        String json = JSON.toJSONString(obj);
        //设置content-type 防止中文乱码
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
