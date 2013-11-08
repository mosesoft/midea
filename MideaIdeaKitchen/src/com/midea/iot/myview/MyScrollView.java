package com.midea.iot.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class MyScrollView extends FrameLayout { 
	private Scroller scroller;  
	public static boolean isScrolling = false;  
	public MyScrollView(Context context) { 
		super(context); 
		init(); 
	}  
	public MyScrollView(Context context, AttributeSet attrs) { 
		super(context, attrs); 
		init(); 
	}  
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) { 
		super(context, attrs, defStyle); 
		init(); 
	}  
	private void clearChildrenCache() { 
		final int count = getChildCount(); 
		for (int i = 0; i < count; i++) { 
			final View layout = getChildAt(i); 
			layout.setDrawingCacheEnabled(false); 
		} 
	} 
	
	@Override 
	public void computeScroll() { 
		if (!scroller.isFinished()) {
			if (scroller.computeScrollOffset()) {
				int oldX = getScrollX(); 
				int oldY = getScrollY(); 
				int x = scroller.getCurrX(); 
				int y = scroller.getCurrY(); 
				if (oldX != x || oldY != y) { 
					scrollTo(x, y); 
				} // Keep on drawing until the animation has finished. 
				invalidate(); 
			} else { 
				clearChildrenCache(); 
			} 
		} else { 
			System.out.println("*** computeScroll");
			isScrolling = false;
			clearChildrenCache(); 
		} 
	}
	private void enableChildrenCache() { 
		final int count = getChildCount(); 
		for (int i = 0; i < count; i++) { 
			final View layout = getChildAt(i); 
			layout.setDrawingCacheEnabled(true); 
		} 
	}  
	private void init(){ 
		scroller = new Scroller(getContext()); 
	}  
	@Override 
	public void scrollTo(int x, int y) { 
		super.scrollTo(x, y); postInvalidate(); 
	}  
	 //���ô˷������ù��������ƫ��   
    public void smoothScrollBy(int dx, int dy, int duration) {   
  
        //����mScroller�Ĺ���ƫ����   
    	scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy, duration);   
        invalidate();//����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��   
    }   
	public void smoothScrollTo(int dx, int duration) {  
		int oldScrollX = getScrollX(); 
		scroller.startScroll(oldScrollX, getScrollY(), dx, getScrollY(), duration); 
		isScrolling = true;
		invalidate(); 
	}  
	public void smoothScrollTo(int dx, int dy, int duration) {  
		scroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration); 
		isScrolling = true;
		invalidate(); 
	}  
}
