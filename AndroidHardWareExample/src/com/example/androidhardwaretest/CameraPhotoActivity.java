package com.example.androidhardwaretest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.djl.util.activity.SimpleActivity;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.view.annotation.ContentView;
import com.djl.util.view.annotation.VID;

/**
 * @author DJL E-mail:
 * @date 2015-7-10 下午5:02:13
 * @version 1.0
 * @parameter
 * @since
 */
@ContentView(R.layout.activity_camera_photo)
public class CameraPhotoActivity extends SimpleActivity implements Callback,
		PictureCallback {
	@VID(R.id.svPhoto)
	SurfaceView msv;
	@VID(R.id.ivThumbnail)
	ImageView miv;
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private Bundle bundle;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivThumbnail:

			break;
		case R.id.btPhoto:
			// mCamera.takePicture(null, null, this);
			mCamera.takePicture(new ShutterCallback() {

				@Override
				public void onShutter() {
					// TODO Auto-generated method stub

				}
			}, null, this);
			break;

		default:
			break;
		}

	}

	@Override
	public void initView() {
		mHolder = msv.getHolder();
		// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.setKeepScreenOn(true);
		mHolder.addCallback(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMessage_(Message msg) {
		// TODO Auto-generated method stub

	}

	@SuppressLint("NewApi")
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		int nc = Camera.getNumberOfCameras();
		DJLUtils.log("getNumberOfCameras   is " + nc);
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.setDisplayOrientation(getPreviewDegree(this));
			// mCamera.getParameters().setPictureFormat(PixelFormat.JPEG);
			// 设置图片格式
			// mCamera.getParameters().setPreviewSize(1840, 3264); // 设置预览大小
			// mCamera.getParameters().setPreviewFrameRate(5); // 设置每秒显示4帧
			List<Size> ss = mCamera.getParameters().getSupportedPictureSizes();
			Size s = ss.get(0);
			for (Size size : ss) {
				if (s.width < size.width) {
					s = size;
				}
			}
			mCamera.getParameters().setPictureSize(s.width, s.height); // 设置保存的图片尺寸
			// mCamera.getParameters().setJpegQuality(100); // 设置照片质量
			mCamera.startPreview();
			mCamera.autoFocus(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}

	}

	public static int getPreviewDegree(Activity activity) {
		// 获得手机的方向
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		// 根据手机的方向计算相机预览画面应该选择的角度
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 90;
			break;
		case Surface.ROTATION_90:
			degree = 0;
			break;
		case Surface.ROTATION_180:
			degree = 270;
			break;
		case Surface.ROTATION_270:
			degree = 180;
			break;
		}
		return degree;
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		try {
			bundle = new Bundle();
			bundle.putByteArray("bytes", data); // 将图片字节数据保存在bundle当中，实现数据交换
			DJLUtils.log("onPictureTaken     " + data.length);
			saveToSDCard(data); // 保存图片到sd卡中
			Toast.makeText(getApplicationContext(), "Success!",
					Toast.LENGTH_SHORT).show();
			camera.startPreview(); // 拍完照后，重新开始预览

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将拍下来的照片存放在SD卡中
	 * 
	 * @param data
	 * @throws IOException
	 */
	@SuppressLint("SimpleDateFormat")
	public static void saveToSDCard(byte[] data) throws IOException {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
		String filename = format.format(date) + ".jpg"
				+ new Random().nextInt(100);
		File fileFolder = new File(Environment.getExternalStorageDirectory()
				+ "/finger/");
		if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
			fileFolder.mkdir();
		}
		File jpgFile = new File(fileFolder, filename);
		FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
		outputStream.write(data); // 写入sd卡中
		outputStream.close(); // 关闭输出流
	}
}
