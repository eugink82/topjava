package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.net.URI;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController{
    static final String REST_URL="/rest/profile";

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(SecurityUtil.authUserId());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(SecurityUtil.authUserId());
    }

    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        super.update(userTo, SecurityUtil.authUserId());
    }

    @GetMapping("/text")
    public String textUTF(){
        return "текст";
    }

    @PostMapping(value="/register",consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody UserTo userTo){
        User created=super.create(userTo);
        URI uriOfNewResource= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL+"/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
