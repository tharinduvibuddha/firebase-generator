package com.example.demoFirebase.domain.dao;

import com.example.demoFirebase.domain.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    RoleEntity findByAuthority(String authority);

}
