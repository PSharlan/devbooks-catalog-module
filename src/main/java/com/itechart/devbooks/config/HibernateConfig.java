package com.itechart.devbooks.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

@Configuration
public class HibernateConfig {

    @Bean
    public ModelMapper modelMapper(EntityManagerFactory entityManagerFactoryBean) {
        final ModelMapper modelMapper = new ModelMapper();

        //configuration to ignore not loaded lazy fields
        final PersistenceUnitUtil unitUtil = entityManagerFactoryBean.getPersistenceUnitUtil();
        modelMapper.getConfiguration().setPropertyCondition(context -> unitUtil.isLoaded(context.getSource()));
        return modelMapper;
    }

    @Bean
    public AbstractEntityManagerFactoryBean entityManagerFactory() {
        AbstractEntityManagerFactoryBean entityManagerFactoryBean = new LocalEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("catalogPersistenceUnit");
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());
        return tm;
    }
}
