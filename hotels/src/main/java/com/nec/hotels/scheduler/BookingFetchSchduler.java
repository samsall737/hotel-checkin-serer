package com.nec.hotels.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.nec.hotels.service.BookingSchedulerService;
import com.nec.hotels.service.MasterService;
import com.nec.master.model.Hotel;

@Configuration
public class BookingFetchSchduler implements SchedulingConfigurer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingFetchSchduler.class);
	
	public final MasterService masterService;
	private final BookingSchedulerService bookingSchedulerService;
	
	@Autowired
	public BookingFetchSchduler(final MasterService masterService,  final BookingSchedulerService bookingSchedulerService) {
		this.masterService = masterService;
		this.bookingSchedulerService = bookingSchedulerService;
	}

	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		List<Hotel> hotels = masterService.getAllHotels();        
		scheduler.setPoolSize(hotels.size());
		scheduler.initialize();
		return scheduler;
	}


	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
	taskRegistrar.setScheduler(poolScheduler());
	Map<Runnable,Trigger> map = new HashMap<>();
	List<Hotel> hotels = masterService.getAllHotels(); 

	for(Hotel hotel : hotels) {
	
		//todo enable this line to fetch booking first time 
		bookingSchedulerService.fetchBookings(hotel);
		map.put(() -> bookingSchedulerService.fetchBookings(hotel) , (triggerContext) -> {
			Calendar nextExecutionTime = new GregorianCalendar();
			Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
			nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
			nextExecutionTime.add(Calendar.MINUTE, (int)hotel.getConfiguration().getPollPeriod());
			return nextExecutionTime.getTime();
			});
		taskRegistrar.setTriggerTasks(map);
		}
	}

}
