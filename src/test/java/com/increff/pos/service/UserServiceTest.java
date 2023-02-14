package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.UserPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AbstractUnitTest {
    @Autowired
    private UserService userService;
    @Test
    public void testAdd() throws ApiException {
        UserPojo p = new UserPojo();
        p.setEmail("swayameron@gmail.com");
        p.setPassword("12345678");
        p.setRole("supervisor");
        userService.add(p);

        List<UserPojo> list = userService.getAll();
        assertEquals(1, list.size());
    }

    @Test
    public void testGet() throws ApiException {
        UserPojo p = new UserPojo();
        p.setEmail("swayameron@gmail.com");
        p.setPassword("12345678");
        p.setRole("supervisor");
        userService.add(p);

        List<UserPojo> list = userService.getAll();
        assertEquals(1, list.size());

        String expectedEmail = "swayameron@gmail.com";
        String expectedPassword = "12345678";
        String expectedRole = "supervisor";

        UserPojo user = userService.get(expectedEmail);
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    public void testDelete() throws ApiException {
        UserPojo p = new UserPojo();
        p.setEmail("swayameron@gmail.com");
        p.setPassword("12345678");
        p.setRole("supervisor");
        userService.add(p);

        List<UserPojo> list = userService.getAll();
        assertEquals(1, list.size());

        String expectedEmail = "swayameron@gmail.com";
        String expectedPassword = "12345678";
        String expectedRole = "supervisor";

        UserPojo user = userService.get(expectedEmail);
        assertEquals(expectedEmail, user.getEmail());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedRole, user.getRole());

        userService.delete(user.getId());
        List<UserPojo> list2 = userService.getAll();
        assertEquals(0, list2.size());

    }
    @Test(expected = ApiException.class)
    public void testDuplicateEmail() throws ApiException {
        UserPojo p = new UserPojo();
        p.setEmail("swayameron@gmail.com");
        p.setPassword("12345678");
        p.setRole("supervisor");
        userService.add(p);
        userService.add(p);
    }
}
