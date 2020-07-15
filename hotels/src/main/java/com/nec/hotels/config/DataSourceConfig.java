package com.nec.hotels.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.discovery.EurekaClient;

@Configuration
public class DataSourceConfig {


	private final EurekaClient  eurekaClient;
	private final MasterEurekaConfig masterEurekaConfig;
	
	@Autowired 
	public DataSourceConfig(final EurekaClient  eurekaClient, final MasterEurekaConfig masterEurekaConfig) {
		this.eurekaClient = eurekaClient;
		this.masterEurekaConfig = masterEurekaConfig;
	}
	
	@Bean
	public DataSource dataSource() {
		CustomRoutingDataSource customDataSource = new CustomRoutingDataSource();
		customDataSource.setTargetDataSources(masterEurekaConfig.getDataSourceHashMap(eurekaClient));
		return customDataSource;
	}
}
