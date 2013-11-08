package com.midea.iot.pad;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.midea.iot.R;

public class CusPadMainCircleBar extends FrameLayout {
	private ImageView mCircleBg;
	private ImageView mCircleMoving;
	private int mCircleValue = 100; //10's a circle;
	private int mdelay = 10 * 1000 / mCircleValue;
	private boolean mEnable = false;

	Runnable mCircleMovingRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			count++;
			if(count > mCircleValue) {
				count = 0;
			}
			circleMoving(count);
			
			mHandler.postDelayed(mCircleMovingRunnable, mdelay);
		}
	};

	Bitmap bitmap = null;

	Bitmap mBitmap = null;
	
	private Handler mHandler = null;
	
	int count = 0;
	
	public CusPadMainCircleBar(Context context) {
//		super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public CusPadMainCircleBar(Context context, AttributeSet attrs) {
//		super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}
	
	public CusPadMainCircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.cus_pad_main_circle_bar, this, true);
		mCircleBg = (ImageView) findViewById(R.id.image_circle_bg);
		mCircleMoving = (ImageView) findViewById(R.id.image_circle_moving);
	}
    public void circleMoving(int precent) {
		bitmap = createSector(precent);
    	if(bitmap != null){
    		mCircleMoving.setImageBitmap(bitmap);
    	}
	}
	private Bitmap createSector(int progress) {
    	if(mBitmap == null){
        	InputStream is = getResources().openRawResource(R.drawable.pad_main_circle_moving);    
            mBitmap = BitmapFactory.decodeStream(is);   
    	}

        final Bitmap b = Bitmap.createBitmap(
        		mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    	Canvas canvas = new Canvas(b);
    	canvas.drawColor(Color.TRANSPARENT);
    	Paint p = new Paint(); // �ʴ�        
    	p.setAntiAlias(true); // �����          
 
    	p.setStyle(Paint.Style.FILL);  
    	if(progress > mCircleValue){
    		progress = mCircleValue;
    	}
    	Path path = getSectorClip(canvas, mBitmap.getWidth()/2, mBitmap.getHeight()/2, mBitmap.getWidth()/2, 270, 360F*progress/mCircleValue + 270);

    	canvas.drawPath(path, p);

    	p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
    	canvas.drawBitmap(mBitmap, 0, 0, p);
    	
    	return b;
    }
	public Path getSectorClip(Canvas canvas,float center_X,float center_Y,float r,float startAngle,float sweepAngle) 
    { 

        Path path = new Path(); 

         //�����ǻ��һ������εļ����� 
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

//      //����һ�����Σ�����Բ 

        RectF rectF = new RectF(center_X-r,center_Y-r,center_X+r,center_Y+r); 

        //�����ǻ�û��μ�����ķ���     
        path.addArc(rectF, startAngle, sweepAngle - startAngle);   
        return path;
    }
    public void setCircleValue(int value) {
		if(value > 0) {
			mCircleValue = value;
		}
	}
    
	public void setHandler(Handler handler) {
		// TODO Auto-generated method stub
		mHandler  = handler;
	}

	public void updateCircle(boolean enable) {
		if(enable && mEnable) return;
		
		if(enable) {
			if(mHandler != null) mHandler.postDelayed(mCircleMovingRunnable, mdelay);
		} else {
			if(mHandler != null) mHandler.removeCallbacks(mCircleMovingRunnable);
		}
		
		mEnable = enable;
	}
}
