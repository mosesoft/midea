package com.midea.iot.myview;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.midea.iot.R;
import com.midea.iot.activities.AppConstant;
import com.midea.iot.activities.MyApplication;

public class MySideBar {
	class QuickImageOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			System.out.println("*****ImageOnClickListener");
			ImageView quickIcon = null;
			int tempId = 0;
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.mwoquickbtn:
				tempId = AppConstant.MICROWAVE;
				quickIcon = mwoquickbtn;
				break;
			case R.id.ovenquickbtn:
				tempId = AppConstant.OVEN;
				quickIcon = ovenquickbtn;
				break;
			case R.id.hoodquickbtn:
				tempId = AppConstant.HOOD;
				quickIcon = hoodquickbtn;
				break;
			case R.id.hobquickbtn:
				tempId = AppConstant.HOB;
				quickIcon = hobquickbtn;
				break;
			case R.id.miniovenquickbtn:
				tempId = AppConstant.MINIOVEN;
				quickIcon = miniovenquickbtn;
				break;
			case R.id.cleanquickbtn:
				tempId = AppConstant.CLEAN;
				quickIcon = cleanquickbtn;
				break;
			default:
				tempId = AppConstant.MICROWAVE;
				quickIcon = mwoquickbtn;
				break;
			}
			if(myApplication.getConnStatus(tempId) != AppConstant.CONN_FAILED){
				id = tempId;
				setAllQuickIconNormal();
				setSelQuickView(quickIcon, id);
				selectDeviceLayout.selectDevice(id);
				titleView.setTitleView(id);
			}

		}
		
	}
	public interface SelectDeviceLayout{
		public void selectDevice(int id);
	}
	public interface TitleView{
		public void setTitleView(int id);
	}
	MyApplication myApplication;
	
	Context ctx;
	FrameLayout parent;
	int id;
	private ImageView mwoquickbtn = null;
	private ImageView ovenquickbtn = null;
	private ImageView hoodquickbtn = null;
	
	private ImageView hobquickbtn = null;
	private ImageView miniovenquickbtn = null;

	private ImageView cleanquickbtn = null;
	private ImageView mwoquickinfobg = null;

	private ImageView mwoquickinfo = null;
	private ImageView ovenquickinfobg = null;
	
	private ImageView ovenquickinfo = null;
	private ImageView hoodquickinfobg = null;
	
	private ImageView hoodquickinfo = null;
	private ImageView hobquickinfobg = null;
	
	private ImageView hobquickinfo = null;
	private ImageView miniovenquickinfobg = null;
	private ImageView miniovenquickinfo = null;
	private ImageView cleanquickinfobg = null;
	private ImageView cleanquickinfo = null;
	
    SelectDeviceLayout selectDeviceLayout = null;
	
	TitleView titleView = null;
	public MySideBar(/*Context ctx, */FrameLayout parent, MyApplication myApplication, int id, SelectDeviceLayout selectDeviceLayout, TitleView titleView) {
		super();
		//this.ctx = ctx;
		this.parent = parent;
		this.myApplication = myApplication;
		this.id = id;
		this.selectDeviceLayout = selectDeviceLayout;
		this.titleView = titleView;
		initView();
	}
	
	private void initView() {
    	
    	mwoquickbtn = (ImageView) parent.findViewById(R.id.mwoquickbtn);
    	ovenquickbtn = (ImageView) parent.findViewById(R.id.ovenquickbtn);
    	hoodquickbtn = (ImageView) parent.findViewById(R.id.hoodquickbtn);
    	hobquickbtn = (ImageView) parent.findViewById(R.id.hobquickbtn);
    	miniovenquickbtn = (ImageView) parent.findViewById(R.id.miniovenquickbtn);
    	cleanquickbtn = (ImageView) parent.findViewById(R.id.cleanquickbtn);
    	
    	mwoquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	ovenquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	hoodquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	hobquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	miniovenquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	cleanquickbtn.setOnClickListener(new QuickImageOnClickListener());
    	
    	mwoquickinfobg = (ImageView)parent.findViewById(R.id.mwoquickinfobg);
		mwoquickinfo = (ImageView)parent.findViewById(R.id.mwoquickinfo);

		ovenquickinfobg = (ImageView)parent.findViewById(R.id.ovenquickinfobg);
		ovenquickinfo = (ImageView)parent.findViewById(R.id.ovenquickinfo);

		hoodquickinfobg = (ImageView)parent.findViewById(R.id.hoodquickinfobg);
		hoodquickinfo = (ImageView)parent.findViewById(R.id.hoodquickinfo);
		
		hobquickinfobg = (ImageView)parent.findViewById(R.id.hobquickinfobg);
		hobquickinfo = (ImageView)parent.findViewById(R.id.hobquickinfo);
		
		miniovenquickinfobg = (ImageView)parent.findViewById(R.id.miniovenquickinfobg);
		miniovenquickinfo = (ImageView)parent.findViewById(R.id.miniovenquickinfo);
		
		cleanquickinfobg = (ImageView)parent.findViewById(R.id.cleanquickinfobg);
		cleanquickinfo = (ImageView)parent.findViewById(R.id.cleanquickinfo);
		setSelQuickView(id);
		setBtnClickable(false);
    }
	
	private void setAllQuickIconNormal(){
		mwoquickbtn.setImageResource(R.drawable.quick_nor);
		ovenquickbtn.setImageResource(R.drawable.quick_nor);
		hoodquickbtn.setImageResource(R.drawable.quick_nor);
		hobquickbtn.setImageResource(R.drawable.quick_nor);
		miniovenquickbtn.setImageResource(R.drawable.quick_nor);
		cleanquickbtn.setImageResource(R.drawable.quick_nor);
	}
	public void setBtnClickable(boolean clickable) {
		// TODO Auto-generated method stub
		mwoquickbtn.setClickable(clickable);
		ovenquickbtn.setClickable(clickable);
		hoodquickbtn.setClickable(clickable);
		hobquickbtn.setClickable(clickable);
		miniovenquickbtn.setClickable(clickable);
		cleanquickbtn.setClickable(clickable);
	}
	
	private void setQuickIcon(ImageView view, int id, boolean isSelect){
		if(isSelect){
			view.setImageResource(AppConstant.quick_sel[id]);
		}else{
			view.setImageResource(AppConstant.quick_nor[id]);
		}
	}
	
	private void setSelQuickView(ImageView view, int id){
		setQuickIcon(view, id, true);
	}
	private void setSelQuickView(int id){
		ImageView quickIcon = null;
		switch(id){
		case AppConstant.MICROWAVE:
			quickIcon = mwoquickbtn;
			break;
		case AppConstant.OVEN:
			quickIcon = ovenquickbtn;
			break;
		case AppConstant.HOOD:
			quickIcon = hoodquickbtn;
			break;
		case AppConstant.HOB:
			quickIcon = hobquickbtn;
			break;
		case AppConstant.MINIOVEN:
			quickIcon = miniovenquickbtn;
			break;
		case AppConstant.CLEAN:
			quickIcon = cleanquickbtn;
			break;
		default:
			id = AppConstant.MICROWAVE;
			quickIcon = mwoquickbtn;
			break;
		}
		setQuickIcon(quickIcon, id, true);
	}
	
	public void setWorkStatus(ImageView imageView, ImageView imageViewBg,
			int status) {
		if(myApplication.getConnStatus(id) == AppConstant.CONN_FAILED){
			System.out.println("***** CONN_FAILED");
			imageView.setImageResource(R.drawable.digital1);
			imageView.setVisibility(View.INVISIBLE);
			imageViewBg.setVisibility(View.INVISIBLE);
		}else{
			switch (status) {
			case AppConstant.WAIT:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.INVISIBLE);
				imageViewBg.setVisibility(View.INVISIBLE);
				System.out.println("***** WAIT");
				break;
			case AppConstant.RUN:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** RUN");
				break;
			case AppConstant.PAUSE:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** PAUSE");
				break;
			case AppConstant.FINISH:
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.VISIBLE);
				imageViewBg.setVisibility(View.VISIBLE);
				System.out.println("***** FINISH");
				break;
			default:
				System.out.println("***** default");
				imageView.setImageResource(R.drawable.digital1);
				imageView.setVisibility(View.INVISIBLE);
				imageViewBg.setVisibility(View.INVISIBLE);
				break;
			}
		}

	}
	
	public void setWorkStatus(int id, int status){
		switch(id){
		case AppConstant.MICROWAVE:
			setWorkStatus(mwoquickinfo, mwoquickinfobg, status);
			System.out.println("***** microwave");
			break;
		case AppConstant.OVEN:
			setWorkStatus(ovenquickinfo, ovenquickinfobg, status);
			System.out.println("***** oven");
			break;
		case AppConstant.HOOD:
			setWorkStatus(hoodquickinfo, hoodquickinfobg, status);
			System.out.println("***** hood");
			break;
		case AppConstant.HOB:
			setWorkStatus(hobquickinfo, hobquickinfobg, status);
			System.out.println("***** hob");
			break;
		case AppConstant.MINIOVEN:
			setWorkStatus(miniovenquickinfo, miniovenquickinfobg, status);
			System.out.println("***** minioven");
			break;
		case AppConstant.CLEAN:
			setWorkStatus(cleanquickinfo, cleanquickinfobg, status);
			System.out.println("***** clean");
			break;
		default:
			break;
		}
		myApplication.setWorkStatus(id, status);
	}
}