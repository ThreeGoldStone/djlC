package com.android.volley.djlpatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.javaUtils.CipherUtils;

/**
 * 用于异步加载显示图片 图片的网络下载是调用volley的imageLoader 如果想自行设定图片的显示方式可以实现ShowBM接口并传入
 * 图片自动用lrucache缓存,如果想缓存在本地可设置{@link DefaultSDCache}
 * 或者设置setSDCachePath()使用默认的图片缓存方法
 * 
 * @author DJL E-mail:
 * @date 2015-6-17 上午11:31:29
 * @version 1.0
 * @parameter
 * @since
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
@SuppressLint("HandlerLeak")
public class ImageUtils implements ImageCache {
	// private Context context;
	private ImageLoader mImageLoader;
	private RequestQueue mQueue;
	private MainHandler mHandler;
	private int defaultImageResId;
	private ImageCacheSD mSDCache;
	private String mSDCachePath;
	private static final int IMAGE_LOAD_SUCCESSED = 325678121;
	private static final int IMAGE_LOADING = 325678122;
	private static final int IMAGE_LOAD_FAILD = 325678123;
	private LruCache<String, Bitmap> mLruCache;

	public ImageUtils(Context context) {
		DJLUtils.log("ImageUtils create");
		mQueue = Volley.newRequestQueue(context);
		DJLUtils.log("Volley.newRequestQueue(context)");
		mImageLoader = new ImageLoader(mQueue, this);
		mHandler = new MainHandler();
		initLruCache(context);

	}

	/**
	 * 初始化lrucache
	 * 
	 * @param context
	 */
	@SuppressLint("NewApi")
	private void initLruCache(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		int memoryClass = activityManager.getMemoryClass();
		mLruCache = new LruCache<String, Bitmap>(memoryClass * 3 / 5) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	/**
	 * 
	 * @param container
	 * @param url
	 * @param maxWith
	 * @param maxHeigh
	 * @param showBM
	 *            如果想用默认展示方式{@link DefaultShowBM}请传入null
	 */
	public void display(View container, String url, int maxWith, int maxHeigh,
			ShowBM showBM) {
		if (showBM == null) {
			showBM = new DefaultShowBM();
		}
		mImageLoader.get(url, new SimpleImageListener(container, url, showBM),
				maxWith, maxHeigh);
	}

	/**
	 * 适用于AdapterView,使用时需要在container的tag里传入该图片的URL
	 * 
	 * @param container
	 * @param url
	 * @param maxWith
	 * @param maxHeigh
	 * @param showBM
	 *            如果想用默认展示方式{@link DefaultShowBMByTag}请传入null
	 */
	public void displayByTag(View container, String url, int maxWith,
			int maxHeigh, ShowBM showBM) {
		if (showBM == null) {
			showBM = new DefaultShowBM();
		}
		mImageLoader.get(url, new SimpleImageListener(container, url, showBM),
				maxWith, maxHeigh);
	}

	/**
	 * 获取缓存图片的方法(该方法在imageLoader内部调用）
	 */
	@SuppressLint("NewApi")
	@Override
	public Bitmap getBitmap(String cacheKey) {
		DJLUtils.log("getBMFormCache      " + cacheKey);
		Bitmap bitmap = mLruCache.get(cacheKey);
		if (bitmap == null) {
			if (mSDCache != null) {
				bitmap = mSDCache.getBM(cacheKey);
				if (bitmap != null) {
					mLruCache.put(cacheKey, bitmap);
				}
			}
		}
		return bitmap;
	}

	/**
	 * 存入缓存图片的方法(该方法在imageLoader内部调用)
	 */
	@Override
	public void putBitmap(String cacheKey, Bitmap bitmap) {
		DJLUtils.log("putBMFormCache      " + cacheKey);
		mLruCache.put(cacheKey, bitmap);
		if (mSDCache != null) {
			DJLUtils.log("mSDCache.putBM(cacheKey, bitmap);");
			mSDCache.putBM(cacheKey, bitmap);
		}

	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	/**
	 * 设置默认的加载中的图片
	 * 
	 * @param defaultImageResId
	 */
	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	public ImageCacheSD getSDCache() {
		return mSDCache;
	}

	public void setSDCache(ImageCacheSD mSDCache) {
		this.mSDCache = mSDCache;
	}

	public String getSDCachePath() {
		return mSDCachePath;
	}

	/**
	 * 设置本地缓存的路径,默认使用{@link DefaultSDCache }
	 * 
	 * @param sDCachePath
	 */
	public void setSDCachePath(String sDCachePath) {
		setSDCache(new DefaultSDCache());
		mSDCachePath = sDCachePath;
	}

	/**
	 * 程序退出时调用
	 */
	public void quit() {
		if (mQueue != null) {
			mQueue.stop();
		}
		if (mLruCache != null) {
			mLruCache.evictAll();
		}
	}

	/**
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-18 上午8:59:11
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class SimpleImageListener implements ImageListener {

		private String mUrl;
		private View v;
		private ShowBM showBM;

		public SimpleImageListener(View container, String url, ShowBM showBM) {
			this.showBM = showBM;
			DJLUtils.log("SimpleImageListener create ");
			this.v = container;
			this.mUrl = url;
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			onErrorResponseOfUrl(error, mUrl);
		}

		@Override
		public void onResponse(ImageContainer response, boolean isImmediate) {
			onResponseOfUrl(response, isImmediate, mUrl);
		}

		public void onErrorResponseOfUrl(VolleyError error, String url) {
			Message msg = Message.obtain();
			msg.what = IMAGE_LOAD_FAILD;
			msg.obj = error;
			mHandler.sendMessage(msg);
		}

		public void onResponseOfUrl(ImageContainer response,
				boolean isImmediate, String url) {
			Message msg = Message.obtain();
			if (isImmediate) {
				msg.what = IMAGE_LOADING;
				msg.obj = new MyImageContainer(defaultImageResId, response, v,
						showBM);
			} else {
				msg.what = IMAGE_LOAD_SUCCESSED;
				msg.obj = new MyImageContainer(0, response, v, showBM);
			}
			mHandler.sendMessage(msg);
		}

	}

	class MyImageContainer {
		public ImageContainer container;
		public View v;
		private int resId;
		private ShowBM showBM;

		public MyImageContainer(int resId, ImageContainer container, View v,
				ShowBM showBM) {
			this.resId = resId;
			this.container = container;
			this.v = v;
			this.showBM = showBM;
		}

		public void display() {
			DJLUtils.log("MyImageContainer display ");
			if (container == null || container.getBitmap() == null) {
				if (resId != 0) {
					showBM.setBM(v, resId);
				}

			} else {
				DJLUtils.log("MyImageContainer display netBM");
				showBM.setBM(v, container);
			}
		}

	}

	/**
	 * 主线程的handler来执行将回调图片设置到指定view的任务
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-18 上午8:54:55
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class MainHandler extends Handler {
		public MainHandler() {
			super(Looper.getMainLooper());
			DJLUtils.log("MainHandler create");
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IMAGE_LOADING:
				DJLUtils.log("MainHandler recive IMAGE_LOADING");
				if (defaultImageResId != 0) {
					MyImageContainer container = (MyImageContainer) msg.obj;
					if (container != null) {
						container.display();
					}
				}
				break;
			case IMAGE_LOAD_SUCCESSED:
				DJLUtils.log("MainHandler recive IMAGE_LOAD_SUCCESSED");
				MyImageContainer container = (MyImageContainer) msg.obj;
				if (container != null) {
					container.display();
				}
				break;
			case IMAGE_LOAD_FAILD:
				DJLUtils.log("MainHandler recive IMAGE_LOAD_fail");
				break;

			}
		}
	}

	/**
	 * 在view的tag里将image的url放入,在回调加载图片是会自动比对匹配(适用于AdapterView 使用conventview的那种)
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-19 下午2:36:39
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class DefaultShowBMByTag implements ShowBM {

		@Override
		public void setBM(View v, int resId) {
			if (v == null || resId <= 0) {
				return;
			}
			if (v instanceof ImageView) {
				((ImageView) v).setImageResource(resId);
			} else {
				v.setBackgroundResource(resId);
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		public void setBM(View v, ImageContainer bm) {
			if (v == null || bm == null) {
				return;
			}
			String tag = (String) v.getTag();
			if (tag != null) {
				if (bm.getRequestUrl().equals(tag)) {
					if (v instanceof ImageView) {
						((ImageView) v).setImageBitmap(bm.getBitmap());
					} else {
						v.setBackgroundDrawable(new BitmapDrawable(v
								.getResources(), bm.getBitmap()));
					}
				}
			}
		}

	}

	/**
	 * 默认的显示图片的方式一,不适应于AdapterView
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-19 下午12:14:02
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class DefaultShowBM implements ShowBM {

		@SuppressWarnings("deprecation")
		@Override
		public void setBM(View v, ImageContainer container) {
			if (v == null || container == null) {
				return;
			}
			Bitmap bm = container.getBitmap();
			if (v instanceof ImageView) {
				((ImageView) v).setImageBitmap(bm);
			} else {
				v.setBackgroundDrawable(new BitmapDrawable(v.getResources(), bm));
			}
		}

		@Override
		public void setBM(View v, int resId) {
			if (v == null || resId <= 0) {
				return;
			}
			if (v instanceof ImageView) {
				((ImageView) v).setImageResource(resId);
			} else {
				v.setBackgroundResource(resId);
			}

		}

	}

	/**
	 * 默认的sd卡图片缓存
	 * 
	 * @author DJL E-mail:
	 * @date 2015-6-19 下午12:12:58
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	class DefaultSDCache implements ImageCacheSD {

		@Override
		public void putBM(String Key, Bitmap bm) {
			if (Key == null) {
				DJLUtils.log("SDCache putBM Key is Null   return !");
				return;
			}
			if (bm == null) {
				DJLUtils.log("SDCache putBM bm is Null   return !");
				return;
			}
			File fileDir = new File(getSDCachePath());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File fileImage = new File(fileDir, CipherUtils.md5(Key));
			DJLUtils.log("fileImage.getAbsolutePath()>>>>>>>"
					+ fileImage.getAbsolutePath());
			if (fileImage.exists() && fileImage.length() > 1) {
				DJLUtils.log("fileImage.length()>>>>>>>" + fileImage.length());
			} else {
				FileOutputStream fos = null;
				try {
					fileImage.createNewFile();
					fos = new FileOutputStream(fileImage);
					bm.compress(CompressFormat.JPEG, 100, fos);
					DJLUtils.log("bm.compress>>>>>>>" + fileImage.length());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		@Override
		public Bitmap getBM(String Key) {
			DJLUtils.log("getBM   form SD Cache +++++++" + Key);
			return BitmapFactory.decodeFile(getSDCachePath()
					+ CipherUtils.md5(Key));
		}

	}

	/**
	 * @author DJL E-mail:
	 * @date 2015-6-18 上午11:27:02
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	public interface ImageCacheSD {
		void putBM(String Key, Bitmap bm);

		Bitmap getBM(String Key);
	}

	/**
	 * @author DJL E-mail:
	 * @date 2015-6-18 上午11:29:09
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	public interface ShowBM {
		void setBM(View v, ImageContainer bm);

		void setBM(View v, int resId);
	}
}
