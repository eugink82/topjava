package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private AdminRestController adminController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext wac= WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        adminController=wac.getBean(AdminRestController.class);
    }

    private static final Logger LOG= LoggerFactory.getLogger(UserServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId=Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
       // request.getRequestDispatcher("meals").forward(request,response);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("getAll");
        request.setAttribute("users",adminController.getAll());
        request.getRequestDispatcher("users.jsp").forward(request,response);
    }
}
