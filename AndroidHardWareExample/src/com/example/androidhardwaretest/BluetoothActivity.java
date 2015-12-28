package com.example.androidhardwaretest;

import java.util.ArrayList;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;
import com.example.androidhardwaretest.bluetooth.BluetoothAcceptThread;
import com.example.androidhardwaretest.bluetooth.BluetoothDetailActivity;

/**
 * @author DJL E-mail:
 * @date 2015-7-15 上午10:05:17
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_buletooth)
public class BluetoothActivity extends SimpleActivity implements
		OnItemClickListener {

	public static final UUID MY_UUID = UUID.fromString("1-2-4-1-2");
	public static final String NAME = "djl";
	@VID(R.id.lv)
	ListView mlv;
	private ArrayList<BluetoothDevice> mList = new ArrayList<>();

	BroadcastReceiver mReceiver = null;
	private ArrayAdapter<String> mAdapter;
	private BluetoothAdapter mBluetoothAdapter;
	private ArrayList<String> sl = new ArrayList<>();

	@Override
	public void handleMessage_(Message msg) {
		switch (msg.what) {
		case 0:
			Intent intent = new Intent(this, BluetoothDetailActivity.class);
			intent.putExtra(BluetoothDetailActivity.BLUE_TOOTH_SOCKET, true);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	public static BluetoothSocket mSocket;
	private BluetoothAcceptThread mAcceptThread;

	@Override
	public void onClick(View v) {
		mAcceptThread = new BluetoothAcceptThread(mBluetoothAdapter, MY_UUID,
				mHandler) {

			@Override
			public void onConnected(BluetoothSocket socket) {
				mSocket = socket;
				mHandler.sendEmptyMessage(0);
			}

		};
		mAcceptThread.start();
	}

	@Override
	public void initView() {
		mlv.setOnItemClickListener(this);

	}

	@Override
	protected void onDestroy() {
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
		if (mAcceptThread != null) {
			mAcceptThread.cancel();
		}
		super.onDestroy();
	}

	@Override
	public void initData() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			DJLUtils.log("不支持蓝牙");
			return;
		}
		if (!mBluetoothAdapter.isEnabled()) {
			DJLUtils.log("bluetooth is noEnabled ");
			mBluetoothAdapter.enable();
		}
		DJLUtils.log("start bluetooth scanning ");
		mBluetoothAdapter.startDiscovery();
		registerReciver();
	}

	private void registerReciver() {
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// ListView
				if (device != null) {
					DJLUtils.log("bb find  " + device.getName());
					mList.add(device);
					sl.add(new StringBuilder().append("address  ")
							.append(device.getAddress()).append("\n")
							.append("name  ").append(device.getName())
							.append("\n").append("BondState  ")
							.append(device.getBondState()).append("\n")
							// .append("Type  ").append(bd.getType()).append("\n")
							.toString());
					showData();
				}
			}

		};

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
	}

	private void showData() {
		if (mAdapter == null) {
			mAdapter = new ArrayAdapter<>(this,
					android.R.layout.simple_list_item_1, sl);
			mlv.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DJLUtils.log("position   " + position);
		BluetoothDevice device = mList.get(position);
		intoDevice(device);
	}

	private void intoDevice(BluetoothDevice device) {
		Intent intent = new Intent(this, BluetoothDetailActivity.class);
		intent.putExtra(BluetoothDetailActivity.BLUE_TOOTH_DEVICE, device);
		startActivity(intent);
	}

}
