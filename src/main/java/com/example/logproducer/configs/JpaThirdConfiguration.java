package com.example.logproducer.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EntityScan(basePackages = "com.example.logproducer.models")
@EnableJpaRepositories(
        basePackages = "com.example.logproducer.repositories",
        entityManagerFactoryRef = "thirdEntityManagerFactoryBean",
        transactionManagerRef = "thirdTransactionManager"
)
@EnableTransactionManagement
public class JpaThirdConfiguration {

    @Bean(name = "dataSourceThird")
    @ConfigurationProperties(prefix = "spring.datasource.third")
    public DataSource dataSourceThird() {
        return DataSourceBuilder.create().build();
    }

    //The third data source must add Qualifier
    @Autowired
    @Qualifier( "dataSourceThird")
    private DataSource dataSource;

    //jpa other parameter configuration
    @Autowired
    private JpaProperties jpaProperties;

    //Entity Management Factory builder
    @Autowired
    private EntityManagerFactoryBuilder factoryBuilder;

    /**
     * Configure the bean of the second entity management factory
     * @return
     */
    @Bean (name = "thirdEntityManagerFactoryBean" )
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean () {
        return factoryBuilder
                .dataSource(dataSource)
                .properties(getVendorProperties())
                .packages("com.example.logproducer")
                .persistenceUnit("thirdPersistenceUnit")
                .build();
    }

    @Autowired
    private HibernateProperties hibernateProperties;

    private Map<String, Object> getVendorProperties() {
        Map<String,String> properties= jpaProperties.getProperties();

        // In order to solve the error of the paging query
        // Because the clicakhouse database is used, but there is no dialect of the clickhouse database, but when the paging query is performed, a dialect needs to be set.
        // Also because the dialect of MySQLDialect is similar, so MySQLDialect is set
        properties.put( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return hibernateProperties.determineHibernateProperties(properties, new HibernateSettings());
    }
    /*** EntityManager but explain it, anyone who has used jpa should understand
     * @return      */
    @Bean(name = "thirdEntityManager")
    public EntityManager entityManager () {
        return entityManagerFactoryBean()
                .getObject()
                .createEntityManager();
    }
    /**
     * @return
     */
    @Bean(name = "thirdTransactionManager")
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
