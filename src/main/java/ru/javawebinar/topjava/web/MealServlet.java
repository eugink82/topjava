package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger LOG= LoggerFactory.getLogger(MealServlet.class);
    private static MealRepository repository;

    @Override
    public void init() throws ServletException {
        repository=new InMemoryMealRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id=request.getParameter("id");
                Meal meal=new Meal(id.isEmpty() ? null : Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
                repository.save(meal, SecurityUtil.authUserId());
                response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        String id=request.getParameter("id");


        switch(action==null ? "All" : action){
            case "delete":
              repository.delete(Integer.valueOf(id),SecurityUtil.authUserId());
              response.sendRedirect("meals");
            break;
            case "create":
            case "update":
                Meal meal="create".equals(action) ? new Meal(LocalDateTime.now(),"",1000) :
                    repository.get(Integer.parseInt(request.getParameter("id")),SecurityUtil.authUserId());
                request.setAttribute("meal",meal);
                request.getRequestDispatcher("edit.jsp").forward(request,response);
            break;
            case "All":
            default:
                request.setAttribute("meals", MealsUtil.getWithExcess(repository.getAll(SecurityUtil.authUserId()),MealsUtil.DEFAULT_EXCEED_CALORIES));
                request.getRequestDispatcher("meals.jsp").forward(request,response);
        }
    }
    private int getId(HttpServletRequest request){
        String paramId= Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
