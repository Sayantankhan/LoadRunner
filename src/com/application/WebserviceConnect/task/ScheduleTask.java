package com.application.WebserviceConnect.task;

import com.application.WebserviceConnect.service.ConnectWSService;

public class ScheduleTask implements Runnable {

	private String task_url;
	
	public ScheduleTask(String url) {
		this.task_url = url;
	}
	
	@Override
	public void run() {
		System.out.println("Thread Running :: "+Thread.currentThread().getId());
		ConnectWSService conWs = new ConnectWSService(task_url);
		conWs.connectWs();
	}

}
