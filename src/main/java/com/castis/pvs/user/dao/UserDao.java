package com.castis.pvs.user.dao;

import com.castis.pvs.entity.User;
import com.castis.pvs.user.dto.TableUserDTO;
import com.castis.pvs.user.dto.UserDTO;

import java.util.List;

public interface UserDao {

	User getUser(String userId);

	void editUser(User user);

	List<TableUserDTO> listUserDTO(String providerId);

	UserDTO getUserDTO(String userId);

	void deleteUser(User user);

	void createUser(User newUser);

}
