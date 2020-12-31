package MoviesServer.servlet;

import MoviesServer.dao.UserDao;
import MoviesServer.dao.impl.UserDaoImpl;
import MoviesServer.entity.ResultInfo;
import MoviesServer.entity.User;
import MoviesServer.service.UserService;
import MoviesServer.service.impl.UserServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /**
     * 用户注册
     * @param req
     * @param resp
     * @throws IOException
     */
    public void register(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        UserService userService = new UserServiceImpl();
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = new User(Integer.parseInt(userId),password);

        boolean b = userService.register(user);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(b);
        if(b == true){
            resultInfo.setMessage("注册成功");
            //将数据存储到session中
            HttpSession session = req.getSession();
            session.setAttribute("userId",userId);
        }else {
            resultInfo.setMessage("注册失败");
        }

//        将数据序列化为json写入response中
        String json = JSON.toJSONString(resultInfo);
        //设置content-type 防止中文乱码
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application-json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 用户登陆
     * @param req
     * @param resp
     * @throws IOException
     */
    public void login(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = new User(Integer.parseInt(userId),password);

        UserService userService = new UserServiceImpl();
        boolean b = userService.login(user);

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(b);
        if(b == false){
            resultInfo.setMessage("id或者密码错误");
        }

        HttpSession session = req.getSession();
        session.setAttribute("userId",userId);
        write(resultInfo,resp);
    }

    /**
     * 获取session中的用户的userId值
     * @param request
     * @param response
     * @throws IOException
     */
    public void getUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Object userId = request.getSession().getAttribute("userId");
        write(userId,response);
    }

    /**
     * 修改用户密码
     * @param request
     * @param response
     * @throws IOException
     */
    public void updateUserPassword(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        UserService userService =  new UserServiceImpl();
        userService.updatePassword(Integer.parseInt(userId),password);

    }


        //退出
    public void userExit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //销毁session中的全部值
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
