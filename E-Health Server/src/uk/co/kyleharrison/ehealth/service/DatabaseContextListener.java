package uk.co.kyleharrison.ehealth.service;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseContextListener implements ServletContextListener {

	private DatabaseUpdateThread databaseThread;

    public void contextInitialized(ServletContextEvent arg0) {
    	System.out.println("Database context listener Created : "+new Date().toString());
    	databaseThread = new DatabaseUpdateThread();
    	databaseThread.start();
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        
    }
    
}
