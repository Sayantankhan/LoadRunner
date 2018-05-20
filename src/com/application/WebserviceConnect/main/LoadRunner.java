package com.application.WebserviceConnect.main;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.application.WebserviceConnect.task.ScheduleTask;
import com.application.WebserviceConnect.util.PropertyReader;

import static  com.application.WebserviceConnect.constant.Constant.*;

public class LoadRunner {

	// Maximum number of threads in thread pool
	public static void main(String[] args) {
		try {
			String  target_url = PropertyReader.getInstance().readProperty("application.target.url");
			String total_load = PropertyReader.getInstance().readProperty("application.executor.load");
			String chunkThread = PropertyReader.getInstance().readProperty("application.executor.reqperiteration");
			String sleeptime = PropertyReader.getInstance().readProperty("application.executor.waitTime");
			int threadPool = 0;
			
			threadPool = (Integer.parseInt(chunkThread) == 0) ? DEFAULT_THREAD_EXECUTOR : Integer.parseInt(chunkThread);
			
			// creates a thread pool with MAX_T no. of 
	        // threads as the fixed pool size(Step 2)
	        ExecutorService pool = Executors.newFixedThreadPool(threadPool);
	        
			for(int counter=1; counter<=Integer.parseInt(total_load);counter++) {
				if((Integer.parseInt(chunkThread)!= 0) && (counter == Integer.parseInt(chunkThread))) {
					Thread.sleep(Long.parseLong(sleeptime));
				}
				pool.execute(new ScheduleTask(target_url));
			}        	
	        // pool shutdown ( Step 4)
	        pool.shutdown();  
			
		}catch (IOException e) {
			System.out.println("check the property value carefully");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
