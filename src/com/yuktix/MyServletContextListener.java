package com.yuktix;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{
	 private SampleThread myThread = null;
	 private ScheduledExecutorService scheduler;
	 
	    public void contextInitialized(ServletContextEvent sce) {
	    //	 scheduler = Executors.newSingleThreadScheduledExecutor();
	    		//    scheduler.scheduleAtFixedRate(new CleanDBTask(), 0, 1, TimeUnit.HOURS);
	      //   scheduler.scheduleAtFixedRate(new SampleThread(), 0, 24, TimeUnit.HOURS);
	    }

	    public void contextDestroyed(ServletContextEvent event) {
		 //   scheduler.shutdownNow();
		}
}
