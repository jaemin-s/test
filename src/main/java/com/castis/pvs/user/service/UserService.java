package com.castis.pvs.user.service;

import com.castis.pvs.entity.User;
import com.castis.pvs.exception.DBAlreadyExistsException;
import com.castis.pvs.exception.DBNotFoundException;
import com.castis.pvs.exception.IncorrectOldPasswordException;
import com.castis.pvs.user.dto.TableUserDTO;
import com.castis.pvs.user.dto.UserDTO;

import java.util.List;

public interface UserService {

	User getUser(String userId) throws DBNotFoundException;

	List<TableUserDTO> listUserDTO(String userId);

	UserDTO getUserDTO(String userId) throws DBNotFoundException;

	void editUserDTO(UserDTO userDTO) throws DBNotFoundException;

	void editPassword(String password, String newPassword, String newPassword2) throws IncorrectOldPasswordException, DBNotFoundException;

	void deleteUser(String userId) throws DBNotFoundException;

	void createUser(UserDTO userDTO) throws DBAlreadyExistsException;

	void editUser(User user);

}
