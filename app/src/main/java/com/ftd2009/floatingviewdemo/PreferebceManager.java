package com.ftd2009.floatingviewdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferebceManager {
	private Context mContext;

	public PreferebceManager(Context context) {
		mContext = context;
	}

	/**
	 * get the shared proferences for getting or setting
	 * 
	 * @return
	 */
	private SharedPreferences getSharedPreferences() {
		return mContext.getSharedPreferences(Constants.PERFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * get the editor for saving the key value
	 * 
	 * @return
	 */
	private Editor getEditer() {
		return getSharedPreferences().edit();
	}

	public float getFloatX() {
		SharedPreferences swg = getSharedPreferences();

		return swg.getFloat(Constants.PREF_KEY_FLOAT_X, 0f);
	}

	public void setFloatX(float x) {
		Editor editor = getEditer();
		editor.putFloat(Constants.PREF_KEY_FLOAT_X, x);
		editor.commit();
	}

	public float getFloatY() {
		SharedPreferences swg = getSharedPreferences();

		return swg.getFloat(Constants.PREF_KEY_FLOAT_Y, 0f);
	}

	public void setFloatY(float y) {
		Editor editor = getEditer();
		editor.putFloat(Constants.PREF_KEY_FLOAT_Y, y);
		editor.commit();
	}

	public boolean onlyDisplayOnHome() {
		SharedPreferences swg = getSharedPreferences();

		return swg.getBoolean(Constants.PREF_KEY_DISPLAY_ON_HOME, true);
	}

	public void setDisplayOnHome(boolean onlyDisplayOnHome) {
		Editor editor = getEditer();
		editor.putBoolean(Constants.PREF_KEY_DISPLAY_ON_HOME, onlyDisplayOnHome);
		editor.commit();
	}

	public boolean isDisplayRight() {
		SharedPreferences swg = getSharedPreferences();

		return swg.getBoolean(Constants.PREF_KEY_IS_RIGHT, false);
	}

	public void setDisplayRight(boolean isRight) {
		Editor editor = getEditer();
		editor.putBoolean(Constants.PREF_KEY_IS_RIGHT, isRight);
		editor.commit();
	}

	public boolean getGetGiftClick() {
		SharedPreferences swg = getSharedPreferences();
		return swg.getBoolean(Constants.PREF_KEY_GIFT_CLICK, false);
	}

	public void setGetGiftClick(boolean hasClick) {
		Editor editor = getEditer();
		editor.putBoolean(Constants.PREF_KEY_GIFT_CLICK, hasClick);
		editor.commit();
	}

	public String getGameId() {
		SharedPreferences swg = getSharedPreferences();
		return swg.getString(Constants.PREF_KEY_GAME_ID, "game2345");
	}

	public void setGameId(String gameId) {
		Editor editor = getEditer();
		editor.putString(Constants.PREF_KEY_GAME_ID, gameId);
		editor.commit();
	}

	// 活动红点:活动点过红点消失
	public boolean hasRedPointClicked() {
		SharedPreferences swg = getSharedPreferences();
		return swg.getBoolean(Constants.PREF_KEY_RED_POINT_EVENT, false);
	}

	public void setRedPointClicked(boolean b) {
		Editor editor = getEditer();
		editor.putBoolean(Constants.PREF_KEY_RED_POINT_EVENT, b);
		editor.commit();
	}

	// 游戏包名
	public String getGamePackageName() {
		SharedPreferences swg = getSharedPreferences();
		return swg.getString(Constants.GAME_PACKAGE_NAME, "");
	}

	public void setGamePackageName(String s) {
		Editor editor = getEditer();
		editor.putString(Constants.GAME_PACKAGE_NAME, s);
		editor.commit();
	}
	
	// 是否第一次出现悬浮球
	public boolean getFirstFloatView() {
		SharedPreferences swg = getSharedPreferences();
		boolean firstFloatView=swg.getBoolean(Constants.FIRST_FLOAT_VIEW, true);
		if (firstFloatView) {
			Editor editor = getEditer();
			editor.putBoolean(Constants.FIRST_FLOAT_VIEW, false);
			editor.commit();
		}
		return  firstFloatView;
	}
	
	public void deleteFirstFloatView() {
			Editor editor = getEditer();
			editor.remove(Constants.FIRST_FLOAT_VIEW);
			editor.commit();
	}
}
