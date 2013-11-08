package com.midea.iot.myview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.midea.iot.R;
import com.midea.iot.activities.MyApplication;

public class MyAlertDialog {
	Context ctx;
	Handler handler;
	MyApplication myApplication;
	Dialog dialog = null;

	Context mContext = null;
	private final Runnable timerTask = new Runnable() {  

		@Override  
		public void run() {  
			// TODO Auto-generated method stub  
			dialogClose();
		}  
	};
	public MyAlertDialog(Context ctx, Handler handler, MyApplication myApplication) {
		super();
		this.ctx = ctx;
		this.handler = handler;
		this.myApplication = myApplication;
		showAlertDialog(ctx, handler);
	}
	
	public void dialogClose(){
		dialog.dismiss();
		System.out.println("*****dialog.dismiss 2 context = " + mContext);
		myApplication.exit();
	} 
	public void showAlertDialog(Context context, Handler handler){
		mContext = context;
		//int index = 1;
		LayoutInflater li = LayoutInflater.from(context);  //NOTE
		final View view = li.inflate(R.layout.alert_layout, null); 
		
		FrameLayout alert = (FrameLayout) view.findViewById(R.id.alert);
		ImageView alertimage1 = (ImageView) view.findViewById(R.id.alertimage1);
		ImageView alertimage2 = (ImageView) view.findViewById(R.id.alertimage2);
		ImageView alertimage3 = (ImageView) view.findViewById(R.id.alertimage3);
/*		switch(index){
		case 0:
			alertimage1.setVisibility(View.VISIBLE);
			alertimage2.setVisibility(View.INVISIBLE);
			alertimage3.setVisibility(View.INVISIBLE);
			break;
		case 1:*/
			alertimage1.setVisibility(View.INVISIBLE);
			alertimage2.setVisibility(View.VISIBLE);
			alertimage3.setVisibility(View.INVISIBLE);
/*			break;
		case 2:
			alertimage1.setVisibility(View.INVISIBLE);
			alertimage2.setVisibility(View.INVISIBLE);
			alertimage3.setVisibility(View.VISIBLE);
			break;
		default:
			alertimage1.setVisibility(View.VISIBLE);
			alertimage2.setVisibility(View.INVISIBLE);
			alertimage3.setVisibility(View.INVISIBLE);
			break;
		}*/
		alert.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogClose();
			}
		});
		if(dialog != null){
			dialog = null;
		}
		//if(dialog == null){
		dialog =  new Dialog(context, R.style.dialog); 
		//}

		dialog.setContentView(view);//  setContentView(view); 
		dialog.setCancelable(false); 
		dialog.setCanceledOnTouchOutside(false);
		
		dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener(){ 
			@Override 
			public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) { 
				switch (keyCode) { 
				case KeyEvent.KEYCODE_BACK: 
					return true; 
				case KeyEvent.KEYCODE_MENU: 
					return true; 
				} 
				return false; 
			} 
		});
		//dialog.setOnDismissListener(new PopupOnDismissListener());
		dialog.show();
		System.out.println("*****dialog.show 1 context = " + context);
		handler.postDelayed(timerTask, 20000);
	}
}