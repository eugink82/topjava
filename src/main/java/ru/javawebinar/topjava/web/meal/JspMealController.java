package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RequestMapping("/meals")
@Controller
public class JspMealController extends AbstractMealController{

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request){
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filterMeals(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals",super.filter(startDate,startTime,endDate,endTime));
        return "meals";
    }

    @GetMapping("/create")
    public String createMeal(HttpServletRequest request,Model model) {
        Meal meal=new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",1000);
        model.addAttribute("meal",meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal(HttpServletRequest request,Model model){
        model.addAttribute("meal",super.get(getId(request)));
        return "mealForm";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request){
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            super.create(meal);
        } else {
            super.update(meal,getId(request));
        }
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request){
        return Integer.parseInt(request.getParameter("id"));
    }
}
