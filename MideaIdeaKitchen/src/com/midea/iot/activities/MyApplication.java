package com.midea.iot.activities;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;


public class MyApplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	//private static MyApplication instance;

/*	private MyApplication() {

	}*/

	public int status[][] = {
		// ����״̬ ����״̬ ����ģʽ  �Ƿ������
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE},
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE},
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE},
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE},
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE},
		{ AppConstant.CONN_FAILED, AppConstant.WAIT, AppConstant.MODE_1, AppConstant.NOT_OPERATE} };

	// ��������Activity��finish

	// ����ģʽ�л�ȡΨһ��MyApplicationʵ��
/*	public MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}
*/
	// ���Activity��������
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	
	public void cleanAllStatus(){
		for(int i = 0; i < status.length; i++){
			status[i][0] = AppConstant.CONN_FAILED;
		}
		for(int i = 0; i < status.length; i++){
			status[i][1] = AppConstant.WAIT;
		}
		for(int i = 0; i < status.length; i++){
			status[i][2] = AppConstant.MODE_1;
		}
		for(int i = 0; i < status.length; i++){
			status[i][3] = AppConstant.NOT_OPERATE;
		}

	}
	public void cleanOperateStatus(){
		for(int i = 0; i < status.length; i++){
			status[i][3] = AppConstant.NOT_OPERATE;
		}

	}
	public void exit() {
		System.out.println("****** activity exit size = " + activityList.size());
		for(int i = activityList.size() - 1; i >= 0; i--)
		{                        
			Activity activity = activityList.get(i);
			System.out.println("****** activity exit activity[" + i + "] = " + activity);
			if(activity != null)
			{
				activityList.remove(i);
				activity.finish();
			}
		}
	}
	
	public int getConnStatus(int id){
		return status[id][0];
	}
	
	public int getOperateStatus(int id){
		return status[id][3];
	}
	
	public int getWorkMode(int id){
		return status[id][2];
	}
	
	public int getWorkStatus(int id){
		return status[id][1];
	}
	public boolean isActivityVisible(String activityName) {
		//System.out.println("****** activity exit size = " + activityList.size());
		if(activityList.size() > 0){
			Activity activity = activityList.get(activityList.size() - 1);
			System.out.println("****** activity activityName = " + activityName + ", activity = " + activity.getTitle().toString());

			if(activity.toString().contains(activityName)){
				return true;
			}else{
				return false;
			}
		}else if(activityName.equals(AppConstant.MAIN_ACTIVITY_NAME)){
			return true;
		}else{
			return false;
		}


	}
	
	public void remove() {
		//System.out.println("****** activity exit size = " + activityList.size());
		int i = activityList.size() - 1;
		Activity activity = activityList.get(activityList.size() - 1);
		activityList.remove(activityList.size() - 1);
		activity.finish();
		System.out.println("****** activity remove[" + i + "] = " + activity);
	}
	
	public void setConnStatus(int id, int conn_status){
		status[id][0] = conn_status;
	}
	
	public void setOperateStatus(int id, int op_status){
		status[id][3] = op_status;
	}
	public void setWorkMode(int id, int mode){
		status[id][2] = mode;
	}
	
	public void setWorkStatus(int id, int work_status){
		status[id][1] = work_status;
	}
}