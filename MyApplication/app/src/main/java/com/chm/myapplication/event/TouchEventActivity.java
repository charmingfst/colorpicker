package com.chm.myapplication.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.chm.myapplication.R;

public class TouchEventActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch_event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.w("eventTest", "Activity | dispatchTouchEvent --> " + com.chm.myapplication.event.TouchEventUtil.getTouchAction(ev.getAction()));
		return super.dispatchTouchEvent(ev);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.w("eventTest", "Activity | onTouchEvent --> " + com.chm.myapplication.event.TouchEventUtil.getTouchAction(event.getAction()));
		return super.onTouchEvent(event);
	}

}