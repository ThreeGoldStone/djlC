package com.example.androidhardwaretest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * @author DJL E-mail:
 * @date 2015-7-14 上午11:46:12
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_sensor)
public class SensorActivity extends SimpleActivity {

	@VID(R.id.lv)
	ListView mlv;

	private SensorManager msm;

	private ArrayList<SensorBean> mDatas;

	private ArrayAdapter<SensorBean> mAdapter;

	private Timer mViewRefreshTimer;
	private static String chname = "1#加速度计/ 2#磁强计/ 14#磁强计未校准/ 4#陀螺仪/ 16#陀螺仪未校准/ 8#近距离传感器/ 5#环境光传感器/ 6#气压计传感器/ 7#温度传感器/ 9#重力/ 10#线性加速度/ 11#旋转矢量/ 18#一步探测器/ 19#步进计数器/ 17#重要的运动检测器/ 15#游戏旋转矢量/ 20#地磁矢量旋转/ 3#取向amd / 33171006#/ 33171007#限制型心肌病/ 33171000#基本的手势/ 33171002#面临/ 33171003#倾斜/ 33171009#计步器/ 33171010# pedestrian-activity-monitor/ 33171011#运动Accel/ 13#温度传感器";

	private HashMap<Integer, String> mChNameMap;

	@Override
	public void onClick(View v) {
	}

	@Override
	public void initView() {
	}

	@SuppressLint("InlinedApi")
	@Override
	public void initData() {
		initCHName();
		msm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// 获取所有的传感器列表
		List<Sensor> sensorList = msm.getSensorList(Sensor.TYPE_ALL);
		mDatas = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (Sensor sensor : sensorList) {
			sb.append(sensor.getType()).append(".").append(sensor.getName())
					.append("\n/");
			SensorBean sensorBean = new SensorBean(sensor, msm);
			mDatas.add(sensorBean);
		}
		DJLUtils.log(sb.toString());
		mAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, mDatas);
		mlv.setAdapter(mAdapter);
		// 定时刷新数据
		initTimer();

	}

	private void initCHName() {
		mChNameMap = new HashMap<>();
		String[] chnames = chname.split("/");
		for (int i = 0; i < chnames.length; i++) {
			String[] c = chnames[i].split("#");
			if (c.length == 2) {
				mChNameMap.put(Integer.parseInt(c[0].trim()), c[1]);
			}
		}
	}

	private void initTimer() {
		mViewRefreshTimer = new Timer();
		mViewRefreshTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				mHandler.sendEmptyMessage(0);
			}
		}, 100, 100);
	}

	@Override
	public void handleMessage_(Message msg) {
		if (mAdapter != null) {
			// 刷新数据
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onDestroy() {
		// 停止UI刷新
		mViewRefreshTimer.cancel();
		// 取消所有的传感器监听器
		if (mDatas != null && mDatas.size() > 0) {
			for (SensorBean s : mDatas) {
				s.stop();
			}
		}
		super.onDestroy();
	}

	/**
	 * 封装传感器及监听器
	 * 
	 * @author DJL E-mail:
	 * @date 2015-7-14 下午4:27:06
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class SensorBean {
		public Sensor sensor;
		public SensorEventListener sensorListener;
		public float[] dataValues;

		public SensorBean(Sensor sensor, SensorManager sm) {
			this.sensor = sensor;
			sensorListener = new SensorEventListener() {
				@Override
				public void onSensorChanged(SensorEvent event) {
					if (event.sensor != null) {
						dataValues = event.values;
					}
				}

				@Override
				public void onAccuracyChanged(Sensor sensor, int accuracy) {
				}
			};
			sm.registerListener(sensorListener, sensor,
					SensorManager.SENSOR_DELAY_NORMAL);

		}

		public void stop() {
			msm.unregisterListener(sensorListener);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(sensor.getName()).append(" ")
					.append(mChNameMap.get(sensor.getType())).append("\n");
			if (dataValues != null) {
				for (int i = 0; i < dataValues.length; i++) {
					sb.append("value").append(i).append(" = ")
							.append(dataValues[i]).append("\n");
				}
			}

			return sb.append("\n").toString();
		}

	}

}
