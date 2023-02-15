package com.increff.pos.dto;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserDto {
    @Autowired
    private UserService userService;
    public void add(UserForm form) throws ApiException {
        ValidateUtil.validateForms(form);
        NormaliseUtil.normalise(form);
        UserPojo p = ConvertorUtil.convert(form);
        userService.add(p);
    }
    public void delete(Integer id) {
        userService.delete(id);
    }
    public List<UserData> getAllUser() {
        List<UserPojo> list = userService.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(ConvertorUtil.convert(p));
        }
        return list2;
    }

    public boolean checkEmailExists(String email) throws ApiException {
        UserPojo userPojo= userService.get(email);
        return !Objects.isNull(userPojo);
    }


}
