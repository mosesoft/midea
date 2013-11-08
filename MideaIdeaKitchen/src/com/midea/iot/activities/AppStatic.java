package com.midea.iot.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;


public class AppStatic {
	
	/**


     * @param canvas  //����

     * @param center_X  //Բ��X���

     * @param center_Y  //Բ��Y���

     * @param r         //�뾶

     * @param startAngle  //��ʼ�Ƕ�

     * @param sweepAngle  //�յ�Ƕ�

     * 

     */ 

	public static  Path getSectorClip(Canvas canvas,float center_X,float center_Y,float r,float startAngle,float sweepAngle) 
    { 

        Path path = new Path(); 

//        System.out.println("******getSectorClip center_X = " + center_X + ", center_Y = " + center_Y);
        path.moveTo(center_X, center_Y);  //Բ�� 
        
        float dot1_x = (float)(center_X+r*Math.cos(startAngle* Math.PI / 180));
        float dot1_y = (float)(center_Y+r*Math.sin(startAngle* Math.PI / 180));
//        System.out.println("******getSectorClip dot1_x = " + dot1_x + ", dot1_y = " + dot1_y);
        path.lineTo(dot1_x, dot1_y);
        
        float dot2_x = (float)(center_X+r*Math.cos(sweepAngle* Math.PI / 180));
        float dot2_y = (float)(center_Y+r*Math.sin(sweepAngle* Math.PI / 180));
//        System.out.println("******getSectorClip dot2_x = " + dot2_x + ", dot2_y = " + dot2_y);
        path.lineTo(dot2_x, dot2_y);

        path.close(); 


        RectF rectF = new RectF(center_X-r,center_Y-r,center_X+r,center_Y+r); 


        path.addArc(rectF, startAngle, sweepAngle - startAngle);   
        return path;
    }

	public static boolean getSharedBoolean(String label, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				AppConstant.PACKA_NAME, Context.MODE_PRIVATE);
		boolean data = settings.getBoolean(label, true);
		return data;
	}
	
	public static String getSharedPreData(String label, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				AppConstant.PACKA_NAME, Context.MODE_PRIVATE);
		String data = settings.getString(label, "0");
		return data;
	}

	public static void saveSharedBoolean(String label, boolean data, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				AppConstant.PACKA_NAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putBoolean(label, data);
		editor.commit();
	}
	
    public static void saveSharedPreData(String label, String data, Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				AppConstant.PACKA_NAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(label, data);
		editor.commit();
	}
}

