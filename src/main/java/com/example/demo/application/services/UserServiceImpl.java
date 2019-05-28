package com.example.demo.application.services;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.UserMapper;
import com.example.demo.application.model.user.Role;
import com.example.demo.application.repository.RoleRepo;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.application.model.user.User;
import com.example.demo.application.resource.filter.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
public class UserServiceImpl implements UserService {

	private UserRepository userDao;
	private BCryptPasswordEncoder bcryptEncoder;
	private UserMapper userMapper;
	private RoleRepo roleRepo;

	public UserServiceImpl(UserRepository userDao,
	                       BCryptPasswordEncoder bcryptEncoder,
	                       UserMapper userMapper,
	                       RoleRepo roleRepo) {
		this.userDao = userDao;
		this.bcryptEncoder = bcryptEncoder;
		this.userMapper = userMapper;
		this.roleRepo = roleRepo;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserDTO> findAll() {
		List<UserDTO> list = new ArrayList<>();
		userDao.findAll().stream().forEach(user -> {
			list.add(userMapper.userToUserDto(user));
		});
		return list;
	}

	public void delete(Long id) {
		userDao.delete(findById(id));
	}

	public User findOne(String username) {
		return userDao.findByUsername(username).get();
	}

	public User findById(Long id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	public UserDTO update(UserDTO UserDTO) {
		User user = findById(UserDTO.getId());
		if (user != null) {
			BeanUtils.copyProperties(UserDTO, user, "password");
			userDao.save(user);
		}
		return UserDTO;
	}

	public Collection<String> findLoggedUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null ? authentication.getAuthorities().stream()
				.map(o -> o.getAuthority()).collect(Collectors.toList()) : null;
	}

	public User saveUser(UserDTO user) {
		Role defaultRole = getDefaultRole();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userEntity = userMapper.userDtoToUser(user);
		if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal().toString().equals(user.getUsername())) {
			//todo update user
		} else {
			//todo save new userEntity
			userEntity.setPassword(bcryptEncoder.encode(userEntity.getPassword()));
			userEntity.setRoles(Collections.singletonList(roleRepo.findRoleByRoleName("ROLE_USER")));
		}
		return userDao.save(userEntity);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<Role> roles) {
		List<GrantedAuthority> authorities
				= new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName().replace("ROLE_", "")));
			role.getPrivileges().stream()
					.map(p -> new SimpleGrantedAuthority(p.getName()))
					.forEach(authorities::add);
		}

		return authorities;
	}

	Role getDefaultRole() {
		Role role_user = roleRepo.findRoleByRoleName("ROLE_USER");
		return role_user;
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
	}

	public User findByUserName(String username) {
		return userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
	}
}
