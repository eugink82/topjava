package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;


@Controller
public class RootController {

    @Autowired
    private MealService mealservice;

    @GetMapping("/")
    public String root(){
        return "redirect:meals";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping("/meals")
    public String meals(Model model){
        model.addAttribute("meals",MealsUtil.getWithExcess(mealservice.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

}
