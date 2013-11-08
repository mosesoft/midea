package com.midea.iot.myview;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.MainActivity;
import com.midea.iot.activities.MyApplication;

public class MyTitlebar implements MySideBar.TitleView{
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("*****BtnOnClickListener");
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.listbtn:
				/*if(isLeft){
					viewClickable.setViewClickable(false);
			    	myScrollView.smoothScrollTo(-156-353, duration);
					isLeft = false;
					System.out.println("****listbtn  VISIBLE");
				}else{
					viewClickable.setViewClickable(true);
					myScrollView.smoothScrollTo(156353, duration);
					isLeft = true;
					System.out.println("****listbtn  GONE");
				}*/
				break;
			case R.id.homebtn:
				myApplication.setOperateStatus(id, AppConstant.HAVE_OPERATE);
				myApplication.exit();
				/*Intent intent = new Intent();
				intent.setClass(MainActivity.getInstance(), MainActivity.class);
				ctx.startActivity(intent);
				
				MainActivity.getInstance().*/
				
				break;
			case R.id.lightbtn:
				if(isLighting){
					lightbtn.setImageResource(R.drawable.light_btn_nor);
					isLighting = false;
				}else{
					lightbtn.setImageResource(R.drawable.light_btn_sel);
					isLighting = true;
				}
				switch(id){
				case AppConstant.MICROWAVE:
					//MainActivity.getInstance().getMicrowaveDevice().lightOn(isLighting);
					break;
					
				case AppConstant.OVEN:
					MainActivity.getInstance().getOvenDevice().lightOn(isLighting);
					break;
					
				case AppConstant.HOOD:
					MainActivity.getInstance().getHoodDevice().setLightOn(isLighting);
					break;
					
				case AppConstant.HOB:
					//MainActivity.getInstance().getHobDevice().lightOn(isLighting);
					break;
					
				case AppConstant.MINIOVEN:
					MainActivity.getInstance().getMiniovenDevice().lightOn(isLighting);
					break;
					
				case AppConstant.CLEAN:
					//MainActivity.getInstance().getCleanDevice().lightOn(isLighting);
					break;
				}

				break;
			case R.id.lockbtn:
				if(isLocked){
					lockbtn.setImageResource(R.drawable.lock_btn_nor);
					isLocked = false;
				}else{
					lockbtn.setImageResource(R.drawable.lock_btn_sel);
					isLocked = true;
				}
				
				switch(id){
				case AppConstant.MICROWAVE:
					//MainActivity.getInstance().getMicrowaveDevice().childrenLock(isLocked);
					break;
					
				case AppConstant.OVEN:
					MainActivity.getInstance().getOvenDevice().childrenLock(isLocked);
					break;
					
				case AppConstant.HOOD:
					//MainActivity.getInstance().getHoodDevice().childrenLock(isLockeda);
					break;
					
				case AppConstant.HOB:
					MainActivity.getInstance().getHobDevice().setChildrenLock(isLocked);
					break;
					
				case AppConstant.MINIOVEN:
					//MainActivity.getInstance().getMiniovenDevice().setChildrenLock(isLighting);
					break;
					
				case AppConstant.CLEAN:
					//MainActivity.getInstance().getCleanDevice().lightOn(isLighting);
					break;
				}
				
				break;

			default:
				break;
			}
		}

	}
	public interface ViewClickable{
		public void setViewClickable(boolean clickable);
	}
	Context ctx;
	MyApplication myApplication;
	FrameLayout parent;
	int id;
    private boolean isSettingView = false;
	private int duration = AppConstant.SCROLL_DURATION_LONG;
	boolean isLeft = true;
	public boolean isLocked = false;
	public boolean isLighting = false;
	private ImageButton listbtn = null;
	private ImageButton homebtn = null;
	private ImageButton lightbtn = null;
	private ImageButton lockbtn = null;
	private TextView title = null;
	
	private MyScrollView myScrollView = null;
	
    ViewClickable viewClickable = null;

	public MyTitlebar(FrameLayout parent, MyApplication myApplication, 
			int id, MyScrollView myScrollView, ViewClickable viewClickable, boolean isSettingView) {
		super();
		this.parent = parent;
		this.myApplication = myApplication;
		this.id = id;
		this.myScrollView = myScrollView;
		this.viewClickable = viewClickable;
		this.isSettingView = isSettingView;
		if(isSettingView){
			duration = AppConstant.SCROLL_DURATION_LONG;
		}else{
			duration = AppConstant.SCROLL_DURATION_SHORT;
		}
		
		isLeft = true;
		isLocked = false;
		isLighting = false;
		initView();
	}
    private void initView() {
    	
		title = (TextView)parent.findViewById(R.id.title);
		listbtn = (ImageButton) parent.findViewById(R.id.listbtn);
		homebtn = (ImageButton) parent.findViewById(R.id.homebtn);
		lightbtn = (ImageButton) parent.findViewById(R.id.lightbtn);
		lockbtn = (ImageButton) parent.findViewById(R.id.lockbtn);
		
		setTitleView(id);
		listbtn.setOnClickListener(new BtnOnClickListener());
		homebtn.setOnClickListener(new BtnOnClickListener());
		lightbtn.setOnClickListener(new BtnOnClickListener());
		lockbtn.setOnClickListener(new BtnOnClickListener());
    }
	public void setBtnClickable(boolean clickable){

    	homebtn.setClickable(clickable);
    	lightbtn.setClickable(clickable);
    	lockbtn.setClickable(clickable);
    }
    public void setLightStatus(boolean status){
		if(status){
			lightbtn.setImageResource(R.drawable.light_btn_sel);
			isLighting = true;
		}else{
			lightbtn.setImageResource(R.drawable.light_btn_nor);
			isLighting = false;
		}
    }
    
    public void setLightVisiable(int visibility){
    	lightbtn.setVisibility(visibility);
    }
    public void setLockStatus(boolean status){
		if(status){
			lockbtn.setImageResource(R.drawable.lock_btn_sel);
			isLocked = true;
		}else{
			lockbtn.setImageResource(R.drawable.lock_btn_nor);
			isLocked = false;
		}
    }
    
    public void setLockVisiable(int visibility){
    	lockbtn.setVisibility(visibility);
    }
    @Override
    public void setTitleView(int id){
    	this.id = id;
    	title.setText(AppConstant.name[id]);
    	switch(id){
    	case AppConstant.MICROWAVE:
    		setLightVisiable(View.INVISIBLE);
    		setLockVisiable(View.INVISIBLE);
    		break;
    		
    	case AppConstant.OVEN:
    		setLightVisiable(View.VISIBLE);
    		setLockVisiable(View.VISIBLE);
    		break;
	
    	case AppConstant.HOOD:
    		setLightVisiable(View.VISIBLE);
    		setLockVisiable(View.INVISIBLE);
    		break;
    		
    	case AppConstant.HOB:
    		setLightVisiable(View.INVISIBLE);
    		setLockVisiable(View.VISIBLE);
    		break;
    		
    	case AppConstant.MINIOVEN:
    		setLightVisiable(View.VISIBLE);
    		setLockVisiable(View.INVISIBLE);
    		break;
    		
    	case AppConstant.CLEAN:
    		setLightVisiable(View.INVISIBLE);
    		setLockVisiable(View.INVISIBLE);
    		break;
    		
    	default:
    		break;
    		
    	}
    	
    } 
}