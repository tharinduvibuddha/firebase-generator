package com.example.demoFirebase.domain.model;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
//import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;

@Entity(name = "user")
@Table(name = "USER")
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = 4815877135015943617L;

    @Id()
    @Column(name = "ID_")
    @GeneratedValue(strategy =GenerationType.AUTO)
   private Long id;

    @Column(name = "USERNAME_",nullable = false,unique = true)
    private String username;

    @Column(name = "PASSWORD_",nullable = false)
    private String password;

    @Column(name = "EMAIL_",nullable = false)
    @Email
    private String email;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<RoleEntity> authorites;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorites;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAuthorites(List<RoleEntity> authorites) {
        this.authorites = authorites;
    }
}
