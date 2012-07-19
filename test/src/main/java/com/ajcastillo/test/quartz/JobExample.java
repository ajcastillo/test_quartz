package com.ajcastillo.test.quartz;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Clase de ejemplo para probar tareas de quartz.
 * @author Antonio Jesús Castillo Cotán
 *
 */
public class JobExample implements Job {

	private static final String ruta_file = "path";

	/** Este método será el encargado de ejecutar el trabajo.
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext jobExecution) throws JobExecutionException {
		
		StringBuilder mensaje = new StringBuilder();
		
		JobDataMap map = jobExecution.getMergedJobDataMap();
		String path = (String) map.get(ruta_file);
		
		if(path == null || !"".equals(path.trim())){
			path = "file.txt";
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(path), true);
			Runtime runtime = Runtime.getRuntime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DecimalFormat format = new DecimalFormat("#.##");
			mensaje.append(sdf.format(new Date()));
			mensaje.append(": Memoria usada ");
			mensaje.append(format.format(runtime.totalMemory()/1048576));
			mensaje.append("MB -  Memoria libre ");
			mensaje.append(format.format(runtime.freeMemory()/1048576));
			mensaje.append("MB\n");
			writer.append(mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if( writer != null){
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		
	}

}
