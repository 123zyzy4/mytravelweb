package com.zy.travel.web.servlet;

import com.zy.travel.domain.Category;
import com.zy.travel.service.ICategoryService;
import com.zy.travel.service.impl.CategoryServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private ICategoryService service=new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cs=service.findAll();
        writeValue(response,cs);
    }

}
