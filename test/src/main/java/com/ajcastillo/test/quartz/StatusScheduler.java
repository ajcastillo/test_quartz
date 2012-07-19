/**
 * 
 */
package com.ajcastillo.test.quartz;

import org.quartz.CalendarIntervalTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;

/**
 * @author sadesi
 * 
 */
public class StatusScheduler {

	private Scheduler scheduler;

	private void init() throws SchedulerException {
		// Creamos la tarea
		JobDetail jobDetail = JobBuilder.newJob(JobExample.class)
				.withIdentity("tarea_ejemplo", "test_job")
				.usingJobData("path", "file.txt").build();
		
		//Creamos el trigger que se encarga de lanzar la tarea
		//El trigger lanzará la tarea cada 15 segundos
		CalendarIntervalTrigger trigger = new CalendarIntervalTriggerImpl(
				"trigger_tarea_ejemplo", IntervalUnit.SECOND, 10);

		//Obtenemos el planificador por defecto
		scheduler = StdSchedulerFactory.getDefaultScheduler();

		//Añadimos la tarea
		scheduler.scheduleJob(jobDetail, trigger);
		
		//Iniciamos el planificador
		scheduler.start();
	}
	
	private void stop() throws SchedulerException{
		scheduler.shutdown();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		StatusScheduler programador = new StatusScheduler();
		
		try {
			programador.init();
			System.out.println("Programador trabajando... pulse enter para finalizarlo");
			System.in.read();
			
			programador.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
