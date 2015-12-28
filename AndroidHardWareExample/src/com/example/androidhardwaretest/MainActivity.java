package com.example.androidhardwaretest;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * 测试并调用Android手机的 :录像,<br>
 * 照相,<br>
 * 录音,<br>
 * 重力传感,<br>
 * 红外线,<br>
 * 蓝牙,<br>
 * Wifi,<br>
 * 红外传感器,<br>
 * 亮度传感器,<br>
 * 耳机孔信号,<br>
 * usb外接信号,<br>
 * 闪光灯,<br>
 * 距离感应器,<br>
 * 气压计,<br>
 * 电子罗盘,<br>
 * 陀螺仪,<br>
 * GPS,<br>
 * AGPS,<br>
 * 北斗定位
 * 
 * @author DJL E-mail:
 * @date 2015-7-10 下午4:22:01
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends SimpleActivity implements OnItemClickListener {
	@VID(R.id.lv)
	ListView mlv;
	private LinkedList<Bean> mDatas;

	@Override
	public void onClick(View v) {
	}

	@Override
	public void initView() {
		mlv.setOnItemClickListener(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initData() {
		mDatas = new LinkedList<>();
		// // TODO add Activity with name
		// datas.add(new Bean("", MainActivity.class));
		// // getPackageManager().geta
		Intent mainIntent = new Intent("com.example.androidhardwaretest", null);
		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
		for (ResolveInfo info : list) {
			Bean bean = new Bean();
			String name = info.activityInfo.name;
			if (name != null) {
				try {
					bean.clazz = (Class<? extends Activity>) Class
							.forName(name);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int labelRes = info.activityInfo.labelRes;
			if (labelRes != 0) {
				bean.name = getResources().getString(labelRes);
			}
			mDatas.add(bean);
		}
		mlv.setAdapter(new MyAdapter<>(mDatas, this));
	}

	@Override
	public void handleMessage_(Message msg) {
	}

	class Bean {

		public Bean(String name, Class<? extends Activity> clazz) {
			this.name = name;
			this.clazz = clazz;
		}

		public Bean() {
		}

		public String name;
		public Class<? extends Activity> clazz;
	}

	/**
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-10 下午12:17:37
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	public class MyAdapter<E> extends BaseAdapter {
		/** 需要填充的数据 */
		private List<E> datas;
		/** 上下文 */
		private Context context;

		/**
		 * 刷新数据
		 * 
		 * @param datas
		 */
		public void setData(List<E> datas) {
			this.datas = datas;
			notifyDataSetChanged();
		}

		public MyAdapter(List<E> datas, Context context) {
			this.datas = datas;
			this.context = context;
		}

		@Override
		public int getCount() {
			return datas != null ? datas.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return datas != null ? datas.get(position) : null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				// 将item的布局文件填充为一个view
				convertView = View.inflate(context,
						android.R.layout.simple_list_item_1, null);
				// 初始化一个ViewHolder
				holder = new ViewHolder();
				// 初始化holder内的控件
				holder.tv = (TextView) convertView
						.findViewById(android.R.id.text1);
				// 将holder放到tag里方便复用
				convertView.setTag(holder);
			} else {
				// (已经走出屏幕的itemview)convertView不为空时直接复用改view
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv.setText(((Bean) getItem(position)).name);
			return convertView;
		}

		/**
		 * 
		 * @author DJL E-mail:
		 * @date 2015-6-10 下午2:07:48
		 * @version 1.0
		 * @parameter
		 * @since
		 */
		class ViewHolder {
			TextView tv;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(this, mDatas.get(position).clazz));
	}
}
