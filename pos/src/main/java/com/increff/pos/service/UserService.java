package com.increff.pos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.dao.UserDao;
import com.increff.pos.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public void add(UserPojo p) throws ApiException {
		normalize(p);
		UserPojo existing = userDao.select(p.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		userDao.insert(p);
	}

	@Transactional(rollbackFor = ApiException.class)
	public UserPojo get(String email) throws ApiException {
		return userDao.select(email);
	}

	@Transactional
	public List<UserPojo> getAll() {
		return userDao.selectAll();
	}

	@Transactional
	public void delete(Integer id) {
		userDao.delete(id);
	}

	protected static void normalize(UserPojo p) {
		p.setEmail(p.getEmail().toLowerCase().trim());
		p.setRole(p.getRole().toLowerCase().trim());
	}
}
