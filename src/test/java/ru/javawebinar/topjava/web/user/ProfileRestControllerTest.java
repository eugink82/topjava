package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(),ADMIN);
    }

    @Test
    void testGet() throws Exception{
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testUpdate() throws Exception{
       User user=new User(USER_ID,"newFIO","myNewEmail@mail.ru","newpassword", Role.ROLE_USER);
       mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.writeValue(user)))
               .andDo(print())
               .andExpect(status().isNoContent());

       assertMatch(userService.getByEmail("myNewEmail@mail.ru"),user);
    }
}