package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private MealService mealservice;

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users",service.getAll());
        return "users";
    }

    @GetMapping("/meals")
    public String meals(Model model){
        model.addAttribute("meals",MealsUtil.getWithExcess(mealservice.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }


    @PostMapping("/users")
    public String setUser(HttpServletRequest request){
        int userId=Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

}
