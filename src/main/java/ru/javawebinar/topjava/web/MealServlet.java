package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealInMemoryRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(name = "MealServlet")
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealInMemoryRepository();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")), request.getParameter("description"), Integer.valueOf(request.getParameter("calories")));
        LOG.error(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getWithExcess(repository.getAll(), 2000));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int id = getId(request);
            repository.delete(id);
            response.sendRedirect("meals");
        } else {
            Meal meal = "create".equals(action) ? new Meal(LocalDateTime.now(), "", 1000) : repository.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        return Integer.valueOf(request.getParameter("id"));
    }
}
