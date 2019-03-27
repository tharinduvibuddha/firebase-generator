package com.example.demoFirebase.config;

import com.example.demoFirebase.domain.dao.Daopackage;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = Daopackage.class)
public class DataConfig {
}
