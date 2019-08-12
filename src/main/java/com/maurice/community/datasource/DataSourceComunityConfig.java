package com.maurice.community.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;


import tk.mybatis.mapper.mapperhelper.MapperHelper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.maurice.community.mapper.comunity", sqlSessionTemplateRef  = "comunitySqlSessionTemplate")
public class DataSourceComunityConfig {

    @Bean(name = "dataSourceComunity")
    @ConfigurationProperties(prefix = "spring.datasource.comunity")
    public DataSource testDataSource() {
        //原始Datasource不需要设置type类型
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "comunitySqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSourceComunity") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        //2019-4-22 添加
        org.apache.ibatis.session.Configuration configuration = bean.getObject().getConfiguration();
        configuration.setCacheEnabled(false);
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.processConfiguration(configuration);
        //2019-4-22 添加

        return bean.getObject();
    }

    @Bean(name = "comunityTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSourceComunity") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "comunitySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("comunitySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}