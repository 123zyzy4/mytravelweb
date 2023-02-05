package com.zy.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.travel.domain.ResultInfo;
import com.zy.travel.domain.User;
import com.zy.travel.service.IUserService;
import com.zy.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 22337
 * @version 1.0
 * @description: TODO
 * @date 2023/2/3 10:49
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet{
    private IUserService service = new UserServiceImpl();
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //马上清除session，保证验证码只能用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            ResultInfo info = new ResultInfo();
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误！请刷新重试······");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            //将json数据写回客户端
            response.getWriter().write(json);

            return;
        }
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册
        boolean flag = service.regist(user);
        ResultInfo info= new ResultInfo();
        //响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        String json =writeValueAsString(info);
        //使用response写回页面
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("尚未激活");
        }
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        //响应数据
        writeValue(response,info);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user=request.getSession().getAttribute("user");
        //响应数据
        writeValue(response,user);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");

        if (code != null) {
            //2.调用service完成激活
            boolean flag = service.active(code);
            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
