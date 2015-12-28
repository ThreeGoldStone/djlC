package com.example.androidhardwaretest.bluetooth;

import java.io.IOException;
import java.util.UUID;

import com.djl.util.androidUtil.DJLUtils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * @author DJL E-mail:
 * @date 2015-7-22 下午3:45:59
 * @version 1.0
 * @parameter
 * @since
 */
public abstract class BluetoothConnectThread extends Thread {
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	BluetoothAdapter mmAdapter;

	public BluetoothConnectThread(BluetoothDevice device,
			BluetoothAdapter adapter, UUID uuid) {
		this.mmDevice = device;
		if (adapter == null) {
			adapter = BluetoothAdapter.getDefaultAdapter();
		}
		mmAdapter = adapter;
		BluetoothSocket bts = null;
		try {
			bts = mmDevice.createRfcommSocketToServiceRecord(uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mmSocket = bts;
	}

	@Override
	public void run() {
		if (mmAdapter != null) {
			mmAdapter.cancelDiscovery();
		}
		try {
			DJLUtils.log("connect");
			mmSocket.connect();
			DJLUtils.log("connected");
			onconnected(mmSocket);
		} catch (IOException e) {
			DJLUtils.log("connect error");
			e.printStackTrace();
			cancel();
			return;
		}

	}

	public abstract void onconnected(BluetoothSocket socket);

	public void cancel() {
		if (mmSocket != null) {
			try {
				mmSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
