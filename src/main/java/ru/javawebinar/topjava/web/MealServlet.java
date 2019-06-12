package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "MealServlet")
public class MealServlet extends HttpServlet {
    private MealDao mealDao;
    public MealServlet() {
        super();
        mealDao=new MealDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id=request.getParameter("id")==null ? 0 : Integer.parseInt(request.getParameter("id"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);
        String description=request.getParameter("description");
        int calories=request.getParameter("calories")==null ? 0 : Integer.parseInt(request.getParameter("calories"));
        Meal meal=mealDao.getMeal(id);
        //meal.setId(id);
        meal.setDateTime(dateTime);
        meal.setDescription(description);
        meal.setCalories(calories);
        mealDao.updateMeal(meal);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=request.getParameter("id")==null ? 0 : Integer.parseInt(request.getParameter("id"));
        String action=request.getParameter("action");
        List<Meal> meals = mealDao.getAllMeals();
        List<MealTo> mealsTo = MealsUtil.getFilteredWithExcess(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        if(action==null){
            request.setAttribute("mealsTo",mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request,response);
            return;
        }
        Meal meal;
        switch(action){
            case "delete":
                mealDao.deleteMeal(id);
                response.sendRedirect("meals.jsp");
            case "view":
            case "edit":
                meal=mealDao.getMeal(id);
            break;
            default:
                throw new IllegalArgumentException("Action "+action+" is illegal");
        }
        request.setAttribute("meals",meal);
        request.getRequestDispatcher("view".equals(action) ? "view.jsp" : "edit.jsp").forward(request,response);
    }
}
