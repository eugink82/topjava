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
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseToLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseToLocalTime;

@RequestMapping(value="/meals")
@Controller
public class JspMealController extends AbstractMealController {

    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request){
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filterMeals(HttpServletRequest request, Model model){
        LocalDate startDate=parseToLocalDate(request.getParameter("startDt"));
        LocalDate   endDate=parseToLocalDate(request.getParameter("endDt"));
        LocalTime startTime=parseToLocalTime(request.getParameter("startTime"));
        LocalTime   endTime=parseToLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals",super.getBetween(startDate,startTime,endDate,endTime));
        return "meals";
    }

    @GetMapping("/create")
    public String createMeal(Model model){
       Meal meal=new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",1000);
       model.addAttribute("meal",meal);
       return "edit";
    }

    @GetMapping("/update")
    public String updateMeal(HttpServletRequest request,Model model){
        model.addAttribute("meal",super.get(getId(request)));
        return "edit";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request){
        String id=request.getParameter("id");
        Meal meal=new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
        if(StringUtils.isEmpty(id)){
            super.create(meal);
        } else {
            super.update(meal,getId(request));
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request){
        String id=Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }
}
