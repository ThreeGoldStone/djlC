package com.example.androidhardwaretest.bluetooth;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.javaUtils.StringUtils;
import com.djl.util.view.annotation.Click;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;
import com.example.androidhardwaretest.BluetoothActivity;
import com.example.androidhardwaretest.R;

/**
 * @author DJL E-mail:
 * @date 2015-7-22 下午5:00:15
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_bluetooth_detail)
public class BluetoothDetailActivity extends SimpleActivity {
	@VID(R.id.tvDetail)
	TextView mtvDetail;
	@Click
	@VID(R.id.btBluetoothMatch)
	Button mbtMatch;
	@Click
	@VID(R.id.btSend)
	Button mbtSend;

	@VID(R.id.lvMessages)
	ListView mlv;
	@VID(R.id.etMessageToSend)
	EditText met;
	public static final String BLUE_TOOTH_DEVICE = "blueToothDevice";
	public static final String BLUE_TOOTH_SOCKET = "blueToothSocket";
	private BluetoothDevice mmDevice;
	private BluetoothSocket mSocket;
	ArrayList<TalkMessage> mMessageList = new ArrayList<>();
	private MyAdapter<TalkMessage> mAdapter;
	// private TalkThread mTalkThread;
	private BluetoothConnectThread mConnectThread;
	private ConnectedThread mDataThread;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btBluetoothMatch:
			// 匹配
			DJLUtils.toastL(this, "匹配");
			match();
			break;
		case R.id.btSend:
			// 发送消息
			sendMessage(met.getText().toString());
			break;

		default:
			break;
		}

	}

	private void match() {
		mConnectThread = new BluetoothConnectThread(mmDevice, null,
				BluetoothActivity.MY_UUID) {

			@Override
			public void onconnected(BluetoothSocket socket) {
				mSocket = socket;
				mHandler.sendEmptyMessage(1);
			}

		};
		mConnectThread.start();

	}

	private void sendMessage(String message) {
		// if (mTalkThread != null && mTalkThread.isAlive()) {
		// DJLUtils.log("send message " + message);
		// mTalkThread.send(message);
		// mMessageList.add(new TalkMessage("", message));
		// mAdapter.setData(mMessageList);
		// }
		if (!StringUtils.isEmpty(message)) {
			if (mDataThread != null && mDataThread.isAlive()) {
				DJLUtils.log("send message " + message);
				mDataThread.send(message);
				mMessageList.add(new TalkMessage("", message));
				mAdapter.setData(mMessageList);
				mlv.setSelection(mMessageList.size() - 1);
				met.setText("");
			}
		}
	}

	@Override
	public void initView() {
		mAdapter = new MyAdapter<TalkMessage>(mMessageList, this);
		mlv.setAdapter(mAdapter);
	}

	@Override
	public void initData() {
		if (getIntent().getBooleanExtra(BLUE_TOOTH_SOCKET, false)) {
			if (BluetoothActivity.mSocket != null) {
				mSocket = BluetoothActivity.mSocket;
				mHandler.sendEmptyMessage(1);
				mmDevice = mSocket.getRemoteDevice();
			} else {
				return;
			}

		} else {
			mmDevice = getIntent().getParcelableExtra(BLUE_TOOTH_DEVICE);
		}
		String device = new StringBuilder().append("address  ")
				.append(mmDevice.getAddress()).append("\n").append("name  ")
				.append(mmDevice.getName()).append("\n").append("BondState  ")
				.append(mmDevice.getBondState()).append("\n")
				// .append("Type  ").append(bd.getType()).append("\n")
				.toString();
		mtvDetail.setText(device);
	}

	@Override
	public void handleMessage_(Message msg) {
		switch (msg.what) {
		case 1:
			mbtMatch.setText("连接成功");
			mbtMatch.setClickable(false);
			startTalkThread();
			break;
		case 200:
			DJLUtils.log("message recived");
			try {
				mMessageList.add(new TalkMessage(new String((byte[]) msg.obj,
						0, msg.arg1, "utf-8"), ""));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mAdapter.setData(mMessageList);
			mlv.setSelection(mMessageList.size() - 1);
			break;

		default:
			break;
		}
	}

	private void startTalkThread() {
		// mTalkThread = new TalkThread(mSocket, mHandler);
		// mTalkThread.start();
		mDataThread = new ConnectedThread(mSocket, mHandler);
		mDataThread.start();
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
				convertView = View
						.inflate(context, R.layout.item_message, null);
				holder = new ViewHolder();
				holder.tvL = (TextView) convertView.findViewById(R.id.tvL);
				holder.tvR = (TextView) convertView.findViewById(R.id.tvR);
				// 将holder放到tag里方便复用
				convertView.setTag(holder);
			} else {
				// (已经走出屏幕的itemview)convertView不为空时直接复用改view
				holder = (ViewHolder) convertView.getTag();

			}
			TalkMessage data = (TalkMessage) getItem(position);
			holder.tvL.setText(data.textL);
			holder.tvR.setText(data.textR);
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
			// TODO 放置itemView的控件
			TextView tvL, tvR;
		}
	}

	class TalkMessage {
		String textL = "";
		String textR = "";

		public TalkMessage() {
		}

		public TalkMessage(String textL, String textR) {
			super();
			this.textL = textL;
			this.textR = textR;
		}
	}

	@Override
	protected void onDestroy() {
		// mTalkThread.cancel();
		if (mDataThread != null) {
			mDataThread.cancel();
		}
		if (mConnectThread != null) {
			mConnectThread.cancel();
		}
		super.onDestroy();
	}
}
