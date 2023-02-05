package com.zy.travel.web.oldservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.travel.domain.ResultInfo;
import com.zy.travel.domain.User;
import com.zy.travel.service.IUserService;
import com.zy.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        IUserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info= new ResultInfo();
        //响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //将info转化为json对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //使用response写回页面
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
