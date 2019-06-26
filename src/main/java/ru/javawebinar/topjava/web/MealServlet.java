package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init() throws ServletException {
        springContext=new ClassPathXmlApplicationContext("spring/spring-app.xml");
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
