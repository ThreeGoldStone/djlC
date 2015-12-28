package com.example.androidhardwaretest;

import java.util.ArrayList;
import java.util.Collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * @author DJL E-mail:
 * @date 2015-7-17 下午2:10:07
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_wifi_p2p)
public class WifiP2PActivity extends SimpleActivity {

	@VID(R.id.lv)
	ListView mlv;
	private ArrayList<WifiP2pDevice> mList = new ArrayList<>();
	private BroadcastReceiver mReceiver;
	private WifiP2pManager mP2pManager;
	private Channel mChannel;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		mP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
		mChannel = mP2pManager.initialize(this, getMainLooper(), null);
		registerReciver();

	}

	@Override
	public void handleMessage_(Message msg) {
		// TODO Auto-generated method stub

	}

	private void registerReciver() {
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();

				if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
					// Check to see if Wi-Fi is enabled and notify appropriate
					// activity
					int wifiState = intent.getIntExtra(
							WifiP2pManager.EXTRA_WIFI_STATE, -1);
					DJLUtils.log("wifi state is " + wifiState);
					if (wifiState == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
						discoverPeers();
					}
				} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION
						.equals(action)) {
					DJLUtils.log("WIFI_P2P_PEERS");

					// Call WifiP2pManager.requestPeers() to get a list of
					// current peers
				} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
						.equals(action)) {
					requestPeers();
					// Respond to new connection or disconnections
					DJLUtils.log("WIFI_P2P_CONNECTION ");
				} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
						.equals(action)) {
					DJLUtils.log("WIFI_P2P_THIS_DEVICE ");
					// Respond to this device's wifi state changing
				}
			}

			private void discoverPeers() {
				mP2pManager.discoverPeers(mChannel, new ActionListener() {

					@Override
					public void onSuccess() {
						DJLUtils.log("discoverPeers onSuccess!");

					}

					@Override
					public void onFailure(int reason) {
						String s = null;
						switch (reason) {
						case WifiP2pManager.ERROR:
							s = "error";
							break;
						case WifiP2pManager.P2P_UNSUPPORTED:
							s = "P2P_UNSUPPORTED";
							break;
						case WifiP2pManager.BUSY:
							s = "BUSY";
							break;

						default:
							break;
						}
						DJLUtils.log("discoverPeers onFailure!" + s);

					}
				});
			}

		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		registerReceiver(mReceiver, filter);
	}

	private void requestPeers() {
		mP2pManager.requestPeers(mChannel, new PeerListListener() {

			@Override
			public void onPeersAvailable(WifiP2pDeviceList peers) {
				Collection<WifiP2pDevice> deviceList = peers.getDeviceList();
				mList = new ArrayList<WifiP2pDevice>(deviceList);
				for (WifiP2pDevice wifiP2pDevice : deviceList) {
					DJLUtils.log("deviceName" + wifiP2pDevice.deviceName);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}
}
