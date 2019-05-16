package com.example.demo.security;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.UserMapper;
import com.example.demo.application.model.user.Role;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.application.model.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Luke on 24.10.2018.
 */
@Service(value = "userService")
public class UsernamePasswordDetailsService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRoles()));
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Role> roleList) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        roleList.stream().forEach(e -> list.add(new SimpleGrantedAuthority(e.getRoleName())));
        return list;
    }

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
        if(user != null) {
            BeanUtils.copyProperties(UserDTO, user, "password");
            userDao.save(user);
        }
        return UserDTO;
    }

    public User save(UserDTO user) {
        User newUser = userMapper.userDtoToUser(user);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userDao.save(newUser);
    }
}
