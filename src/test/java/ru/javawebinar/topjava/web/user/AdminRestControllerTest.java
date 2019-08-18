package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;

class AdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL=AdminRestController.REST_URL+"/";

    @Test
    void testGet() throws Exception{
        mockMvc.perform(get(REST_URL+ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    void testGetByEmail() throws Exception{
        mockMvc.perform(get(REST_URL+"by?email="+USER.getEmail()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL+USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
                assertMatch(userService.getAll(),ADMIN);
    }

    @Test
    void testUpdate() throws Exception{
        User newUser=new User(USER);
        newUser.setName("NewName");
        newUser.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL+USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andExpect(status().isNoContent());
        assertMatch(userService.get(USER_ID),newUser);
    }

    @Test
    void testCreate() throws Exception {
        User expected=new User(null,"newName","new@mail.ru","newpass",Role.ROLE_USER,Role.ROLE_ADMIN);
        ResultActions action=mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        User returned= TestUtil.readFromJson(action,User.class);
        expected.setId(returned.getId());

        assertMatch(returned,expected);
        assertMatch(userService.getAll(),ADMIN,expected,USER);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN,USER));

    }
}