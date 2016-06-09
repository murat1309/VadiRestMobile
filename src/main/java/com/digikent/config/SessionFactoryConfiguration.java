package com.digikent.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by yunusoncel on 14.2.2016.
 */
@Configuration
@AutoConfigureAfter(DatabaseConfiguration.class)
@EnableTransactionManagement
public class SessionFactoryConfiguration {

	@PersistenceContext
	EntityManager entityManager;

	protected Session getCurrentSession()  {
		return entityManager.unwrap(Session.class);
	}

	@Bean
	@Transactional
	public SessionFactory sessionFactory() {
		Session session = getCurrentSession();

		return session.getSessionFactory();
	}
}
