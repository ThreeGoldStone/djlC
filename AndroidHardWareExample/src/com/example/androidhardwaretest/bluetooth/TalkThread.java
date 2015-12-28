package com.example.androidhardwaretest.bluetooth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.djl.util.androidUtil.DJLUtils;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

/**
 * @author DJL E-mail:
 * @date 2015-7-24 上午11:25:04
 * @version 1.0
 * @parameter
 * @since
 */
public class TalkThread extends Thread {
	private final BluetoothSocket mSocket;
	private Handler mHandler;
	private boolean loop;
	private final BufferedWriter mWriter;
	private final BufferedReader mReader;

	public TalkThread(BluetoothSocket socket, Handler handler) {
		this.mSocket = socket;
		this.mHandler = handler;
		InputStream is = null;
		OutputStream os = null;
		try {
			is = mSocket.getInputStream();
			os = mSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mWriter = new BufferedWriter(new OutputStreamWriter(os));
		mReader = new BufferedReader(new InputStreamReader(is));
	}

	@Override
	public void run() {
		loop = true;
		while (loop) {
			DJLUtils.log("read");
			try {
				String readLine = mReader.readLine();
				mHandler.obtainMessage(200, readLine).sendToTarget();
				DJLUtils.log("readed");
			} catch (IOException e) {
				DJLUtils.log("read error");
				e.printStackTrace();
			}
		}
	}

	public void send(String message) {
		try {
			mWriter.write(message);
		} catch (IOException e) {
			DJLUtils.log("send error");
			e.printStackTrace();
		}
	}

	public void cancel() {
		loop = false;
		if (mSocket != null) {
			try {
				mReader.close();
				mWriter.close();
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mHandler = null;
		}

	}
}
