package com.nec.hotels.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.nec.hotels.service.MasterService;
import com.nec.master.model.Hotel;
import com.netflix.discovery.EurekaClient;


@Configuration
public class MasterEurekaConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterEurekaConfig.class);

	private final MasterService masterService;
	private HashMap<String, UUID> databaseSources;
	
	@Autowired
	public MasterEurekaConfig(final MasterService masterService) {
		this.masterService = masterService;
		this.databaseSources = new HashMap<>();
	}


	public  Map<Object, Object> getDataSourceHashMap(EurekaClient eurekaClient) {
		List<Hotel> hotels =  masterService.getAllHotels();
		HashMap<Object, Object> dataSourcesMap = new HashMap<>();
		for (Hotel hotel : hotels) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl(hotel.getDatabaseConnections().getUrl());
			LOGGER.debug("DB URL", hotel.getDatabaseConnections().getUrl()) ;
			dataSource.setUsername(hotel.getDatabaseConnections().getUserName());
			dataSource.setPassword(hotel.getDatabaseConnections().getPassword());
			dataSourcesMap.put(hotel.getId().toString(), dataSource);
			databaseSources.put(hotel.getHotelCode(), hotel.getId());
		}
		return dataSourcesMap;
	}
	
	public Map<String, UUID> getHotelCodeHotelIdMap(){
		return databaseSources;
	}

}
