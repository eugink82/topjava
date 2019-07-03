package ru.javawebinar.topjava.web;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseToLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseToLocalTime;

public class MealServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init() throws ServletException {
        springContext=new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml");
        mealController=springContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id=request.getParameter("id");
        Meal meal=new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
        if(StringUtils.isEmpty(id)){
            mealController.create(meal);
        } else {
            mealController.update(meal,getId(request));
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        switch(action==null ? "All" : action){
            case "delete":
             int id=getId(request);
              mealController.delete(id);
              response.sendRedirect("meals");
            break;
            case "filter":
                LocalDate startDate=parseToLocalDate(request.getParameter("startDt"));
                LocalDate   endDate=parseToLocalDate(request.getParameter("endDt"));
                LocalTime startTime=parseToLocalTime(request.getParameter("startTime"));
                LocalTime endTime=parseToLocalTime(request.getParameter("endTime"));
                request.setAttribute("meals",mealController.getBetween(startDate,startTime,endDate,endTime));
                request.getRequestDispatcher("meals.jsp").forward(request,response);
                break;
            case "create":
            case "update":
                Meal meal="create".equals(action) ? new Meal(LocalDateTime.now(),"",1000) :
                mealController.get(getId(request));
                request.setAttribute("meal",meal);
                request.getRequestDispatcher("edit.jsp").forward(request,response);
            break;
            case "All":
            default:
                request.setAttribute("meals", mealController.getAll());
                request.getRequestDispatcher("meals.jsp").forward(request,response);
        }
    }
    private int getId(HttpServletRequest request){
        String paramId= Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
