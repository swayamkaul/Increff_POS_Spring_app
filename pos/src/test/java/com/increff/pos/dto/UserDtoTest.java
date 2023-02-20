package com.increff.pos.dto;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest {
    @Autowired
    UserDto userDto;

    @Test
    public void userTest() throws ApiException {
        UserForm userForm = FormHelper.createUser("swayameron@gmail.com", "1234", "supervisor");
        userDto.addUser(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());
    }

    @Test
    public void userDeleteTest() throws ApiException {
        UserForm userForm = FormHelper.createUser("swayameron@gmail.com", "1234", "supervisor");
        userDto.addUser(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());

        userDto.deleteUser(userDataList.get(0).getId());
        List<UserData> userDataList2 = userDto.getAllUser();
        assertEquals(0, userDataList2.size());

    }

    @Test
    public void userGetTest() throws ApiException {
        UserForm userForm = FormHelper.createUser("swayameron@gmail.com", "1234", "supervisor");
        userDto.addUser(userForm);

        List<UserData> userDataList = userDto.getAllUser();
        assertEquals(1, userDataList.size());

        assertEquals(true, userDto.checkEmailExists("swayameron@gmail.com"));
    }

    @Test
    public void checkIllegalEmailTest() throws ApiException {
        UserForm userForm = FormHelper.createUser("swayameron@gmail.com", "1234", "supervisor");
        userDto.addUser(userForm);

        assertEquals(false, userDto.checkEmailExists("random@gmail.com"));
    }
}
