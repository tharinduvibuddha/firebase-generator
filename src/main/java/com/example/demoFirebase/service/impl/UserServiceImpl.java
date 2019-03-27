package com.example.demoFirebase.service.impl;

import com.example.demoFirebase.config.SecurityConfig.Roles;
import com.example.demoFirebase.domain.dao.RoleRepository;
import com.example.demoFirebase.domain.dao.UserRepository;
import com.example.demoFirebase.domain.model.RoleEntity;
import com.example.demoFirebase.domain.model.UserEntity;
import com.example.demoFirebase.service.UserService;
import com.example.demoFirebase.service.shared.RegisterUserInit;


import javafx.beans.binding.SetBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service(value = UserServiceImpl.NAME)
public class UserServiceImpl implements UserService {


public final static String NAME = "UserService";
private final static Logger logger= Logger.getLogger(UserServiceImpl.class);

@Autowired
private UserRepository userDao;

@Autowired
   private   RoleRepository roleRepository;



@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = userDao.findByUsername(userName);
        if (userDetails == null)
        return null;

        Set<GrantedAuthority> grantedAuthorities = new  HashSet<GrantedAuthority>();
        for (GrantedAuthority role : userDetails.getAuthorities()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
                userDetails.getPassword(),userDetails.getAuthorities());

    }

    @Override
    @Transactional
    @Secured(value = Roles.ROLE_ANONYMOUS)
    public UserEntity registerUser(RegisterUserInit init) {
        UserEntity userLoaded = userDao.findByUsername(init.getUserName());

        if (userLoaded == null){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(init.getUserName());
            userEntity.setEmail(init.getEmail());

            userEntity.setAuthorites(getUserRoles());

            userEntity.setPassword(UUID.randomUUID().toString());
            userDao.save(userEntity);
            logger.info("register user -> user created");
            return userEntity;

        }else {
            logger.info("register -> user exists");
            return userLoaded;
        }
    }

    @PostConstruct
    public void init() {

        if (userDao.count() == 0) {
            UserEntity adminEntity = new UserEntity();
            adminEntity.setUsername("admin");
            adminEntity.setPassword("admin");
            adminEntity.setEmail("vibuddha@xiges.io");

            adminEntity.setAuthorites(getAdminRoles());
            userDao.save(adminEntity);

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("user1");
            userEntity.setPassword("user1");
            userEntity.setEmail("vibuddha@xiges.io");
           userEntity.setAuthorites(getUserRoles());

            userDao.save(userEntity);
        }
    }

    private List<RoleEntity> getAdminRoles() {
        return Collections.singletonList(getRole(Roles.ROLE_ADMIN));
    }

    private List<RoleEntity> getUserRoles() {
        return Collections.singletonList(getRole(Roles.ROLE_USER));
    }

    /**
     * Get or create role
     * @param authority
     * @return
     */
    private RoleEntity getRole(String authority) {
        RoleEntity adminRole = roleRepository.findByAuthority(authority);
        if (adminRole == null) {
            return new RoleEntity(authority);
        } else {
            return adminRole;
        }
    }

}
