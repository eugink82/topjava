package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "/rest/profile/meals";

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal newMeal = super.create(meal);
        URI uriOfNewMeal= ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(REST_URL + "/{id}")
        .buildAndExpand(newMeal.getId()).toUri();
        return  ResponseEntity.created(uriOfNewMeal).body(newMeal);
    }

    @Override
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal,@PathVariable int id){
        super.update(meal,id);
    }

   // @Override
    @GetMapping("/between")
//    public List<MealTo> getBetween(@RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)LocalDate startDate,
//                                   @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime startTime,
//                                   @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate,
//                                   @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime endTime) {
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }
    public List<MealTo> getBetween(@RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                   @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        return super.getBetween(startDate.toLocalDate(),startDate.toLocalTime(),endDate.toLocalDate(),endDate.toLocalTime());
    }
}