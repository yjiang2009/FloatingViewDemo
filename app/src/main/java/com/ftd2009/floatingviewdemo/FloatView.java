package com.ftd2009.floatingviewdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;



import java.util.Timer;
import java.util.TimerTask;

public class FloatView extends ImageView {
	private float mTouchX;
	private float mTouchY;
	private float x;
	private float y;
	private OnClickListener mClickListener;

	private WindowManager windowManager = (WindowManager) getContext()
			.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	// 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
	private LayoutParams windowManagerParams;

	//保存当前是否为移动模式
	private boolean  isMove = false;
	//保存当前悬浮球在左边还是右边
	private boolean  isRight = false;


	private int defaultResource = Utils.getDrawable(getContext(),"zhangyu");
	private int focusLeftResource = Utils.getDrawable(getContext(),"zhangyu");
	private int focusRightResource =Utils.getDrawable(getContext(),"zhangyu");
	private int leftResource =Utils.getDrawable(getContext(),"zhangyu");
	private int rightResource =Utils.getDrawable(getContext(),"zhangyu");
	private int leftHideResource = Utils.getDrawable(getContext(),"arrowleft");
	private int rightHideResource =Utils.getDrawable(getContext(),"arrowright");
	
	private PreferebceManager	mPreferenceManager = null;

	//是否触摸悬浮窗
	private boolean isTouch = false;
	private Timer timer;
	//定时器取消
	private boolean isCancel;
	private TimerTask timerTask;

	private static final int KEEP_TO_SIDE = 0;
	private static final int HIDE = 1;
	
	public FloatView(Context context,LayoutParams windowManagerParams) {
		super(context);
		isMove = false;
		isRight = false;
		this.windowManagerParams = windowManagerParams;
		mPreferenceManager = new PreferebceManager(getContext());
		//windowManagerParams.type = LayoutParams.TYPE_PHONE; // 设置window type
		windowManagerParams.type = LayoutParams.TYPE_PHONE;
		//windowManagerParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
		//windowManagerParams.type = LayoutParams.TYPE_APPLICATION_PANEL;
		windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE
				| LayoutParams.FLAG_FULLSCREEN;
		//LayoutParams
		/*
		 * 注意，flag的值可以为：
		 * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE   不可聚焦
		 * LayoutParams.FLAG_NOT_TOUCHABLE   不可触摸
		 */
		// 调整悬浮窗口至左上角，便于调整坐标
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值
		windowManagerParams.x = (int)mPreferenceManager.getFloatX();
		windowManagerParams.y = (int)mPreferenceManager.getFloatY();
		// 设置悬浮窗口长宽数据
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		
		
		//设置红点
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean clickRedPoint=false;
		if (!clickRedPoint) {
			defaultResource= Utils.getDrawable(getContext(),"zhangyured");
			focusLeftResource = Utils.getDrawable(getContext(),"zhangyured");
			 focusRightResource =Utils.getDrawable(getContext(),"zhangyured");
			 leftResource =Utils.getDrawable(getContext(),"zhangyured");
			 rightResource =Utils.getDrawable(getContext(),"zhangyured");
		}else {
			defaultResource= Utils.getDrawable(getContext(),"zhangyu");
			 focusLeftResource = Utils.getDrawable(getContext(),"zhangyu");
			 focusRightResource =Utils.getDrawable(getContext(),"zhangyu");
			 leftResource =Utils.getDrawable(getContext(),"zhangyu");
			 rightResource =Utils.getDrawable(getContext(),"zhangyu");
		}
		
		
		isRight = mPreferenceManager.isDisplayRight();
		if(isRight) {
			setImageResource(rightResource);
		}else {
			setImageResource(leftResource);
		}
		
		if (mPreferenceManager.getFirstFloatView()) {
			//第一次登陆出现悬浮球
			startTimerCount(6000);
		}else {
			startTimerCount(3000);
		}
		
	}

	private android.os.Handler handler = new android.os.Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				/*case KEEP_TO_SIDE:
					setImageResource(leftResource);
					cancelTimerCount();
					break;*/
				case HIDE:
					if(isRight){
						setImageResource(rightHideResource);
					}
					else{
						setImageResource(leftHideResource);
					}
					cancelTimerCount();
					break;
				/*case 2:
					windowManager.updateViewLayout(image, windowManagerParams);// 刷新显示
					break;*/
			}
		};
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		isTouch = true;
		int statusBarHeight = getStatusHeight(getContext());
		
		System.out.println("statusBarHeight:"+statusBarHeight);
		// 获取相对屏幕的坐标，即以屏幕左上角为原点
		x = event.getRawX();
		y = event.getRawY()- statusBarHeight; // statusBarHeight是系统状态栏的高度
		Log.i("tag", "currX" + x + "====currY" + y);

		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
			// 获取相对View的坐标，即以此View左上角为原点
			mTouchX = event.getX();
			mTouchY = event.getY();
			isMove = false;
			Log.i("tag", "startX" + mTouchX + "====startY"
					+ mTouchY);
			if(isRight) {
				setImageResource(focusRightResource);
			} else {
				setImageResource(focusLeftResource);
			}
			cancelTimerCount();
			break;

		case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
			int xMove = Math.abs((int) (event.getX() - mTouchX));
			int yMove = Math.abs((int) (event.getY() - mTouchY));
			if(xMove > 100 || yMove >100) {
				isMove = true;
				if(onMoveListener!=null) {
					onMoveListener.onMove();
				}
				setImageResource(defaultResource);
				updateViewPosition();
			}
			Log.e("fv", "isMove:"+isMove);
			
			if (x<100&&(event.getX() - mTouchX)<-10){
				isMove = true;
				if(onMoveListener!=null) {
					onMoveListener.onMove();
				}
			}
			if (x>(screenWidth-100)&&(event.getX() - mTouchX)>50){
				isMove = true;
				if(onMoveListener!=null) {
					onMoveListener.onMove();
				}
			}
			break;
		case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
			isTouch = false;
			if(isMove) {
				isMove = false;
				float halfScreen = screenWidth/2;
				if(x <= halfScreen) {
					if (x<100&&(event.getX() - mTouchX)<-10){
						setImageResource(leftHideResource);
					}else {
						setImageResource(leftResource);
					}
					x = 0 ;
					isRight = false;
				} else {
					if (x>(screenWidth-100)&&(event.getX() - mTouchX)>50){
						setImageResource(rightHideResource);
					}else {
						setImageResource(rightResource);
					}
					
					//x = screenWidth+Math.abs((int)mTouchX);
					x = screenWidth+Math.abs((int)mTouchX)+getNavigationHeight(getContext());
					//x = screenWidth+getNavigationHeight(getContext());
					isRight = true;
				}
				updateViewPosition();
				mPreferenceManager.setFloatX(x);
				mPreferenceManager.setFloatY(y);
				mPreferenceManager.setDisplayRight(isRight);
				startTimerCount(3000);
			} else {
				if(isRight) {
					setImageResource(rightResource);
				}else {
					setImageResource(leftResource);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(mClickListener!=null) {
					mClickListener.onClick(this);
				}
			}

			mTouchX = mTouchY = 0;
			break;
		}
		return true;
	}
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.mClickListener = l;
	}
	public interface OnMoveListener{
		public abstract void onMove();
	}
	private OnMoveListener onMoveListener;
	public void setOnMoveListener(OnMoveListener m){
		this.onMoveListener = m;
	}
	private void updateViewPosition() {
		// 更新浮动窗口位置参数
		windowManagerParams.x = (int) (x - mTouchX);
		windowManagerParams.y = (int) (y - mTouchY);
		windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
	}
	
	public void startTimerCount(long delay){
		isCancel = false;
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if(!isTouch&&!isCancel){
					handler.sendEmptyMessage(HIDE);
				}
			}
		};
		timer.schedule(timerTask, delay);
	}

	public void cancelTimerCount(){
		isCancel = true;
		if(timer!=null){
			timer.cancel();
			timer =null;
		}
		if(timerTask!=null){
			timerTask.cancel();
			timerTask = null;
		}
	}

	/**
	 * 获得状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)  {
		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}
	
	
	/**
	 * 获得导航栏的高度
	 * @param context
	 * @return
	 */
	public static int getNavigationHeight(Context context)  {
		int statusHeight = 0;
		int resourceId=context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");;
		if (resourceId>0) {
			statusHeight=context.getResources().getDimensionPixelSize(resourceId);
		}
		return statusHeight;
	}
}
