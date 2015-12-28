package com.example.androidhardwaretest.bluetooth;

import java.io.IOException;
import java.util.UUID;

import com.djl.util.androidUtil.DJLUtils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

/**
 * @author DJL E-mail:
 * @date 2015-7-22 上午11:09:50
 * @version 1.0
 * @parameter
 * @since
 */
public abstract class BluetoothAcceptThread extends Thread {
	private final BluetoothServerSocket bluetoothSSocket;
	BluetoothAdapter mmAdapter;
	private UUID uuid;
	private Handler mHandler;

	public BluetoothAcceptThread(BluetoothAdapter bluetoothAdapter, UUID uuid,
			Handler handler) {
		this.mmAdapter = bluetoothAdapter;
		this.uuid = uuid;
		this.mHandler = handler;
		BluetoothServerSocket bss = null;
		try {
			bss = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name,
					this.uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bluetoothSSocket = bss;
	}

	private String name = "djl";
	private boolean loop;

	@Override
	public void run() {
		if (bluetoothSSocket != null) {
			loop = true;
			BluetoothSocket socket = null;
			while (loop) {
				DJLUtils.log("bluetoothSSocket.accept()...");
				try {
					socket = bluetoothSSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
					DJLUtils.log("bluetoothSSocket.accept() fail");
					break;
				}
				if (socket != null) {
					onConnected(socket);
					try {
						bluetoothSSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}

		}
	}

	public void cancel() {
		loop = false;
		try {
			bluetoothSSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public abstract void onConnected(BluetoothSocket socket);
}
