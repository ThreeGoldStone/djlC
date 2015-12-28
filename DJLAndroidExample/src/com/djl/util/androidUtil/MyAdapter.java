package com.djl.util.androidUtil;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/** 
 * @author  DJL E-mail: 
 * @date 2015-9-7 上午10:48:43 
 * @version 1.0 
 * @parameter  
 * @since  
 */
/**
 * 
 * @author DJL E-mail:
 * @date 2015-6-10 下午12:17:37
 * @version 1.0
 * @parameter
 * @since
 */
public abstract class MyAdapter<E> extends BaseAdapter {
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
	public E getItem(int position) {
		return datas != null ? datas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<Integer, View> holder = null;
		if (convertView == null) {
			convertView = initConvertView().view;
			Log.i("djl", "initConvertView");
		}
		holder = (HashMap<Integer, View>) convertView.getTag();
		setView(holder, getItem(position), position);
		return convertView;
	}

	/**
	 * 初始化带tag的itemView
	 * 
	 * @param context
	 * @param layoutid
	 *            itemView的layout布局文件
	 * @param viewIds
	 *            控件的ids
	 * @return
	 */
	protected ViewWithTag getViewWithTag(int layoutid, int... viewIds) {
		View layout = View.inflate(context, layoutid, null);
		HashMap<Object, Object> map = new HashMap<>();
		for (int i = 0; i < viewIds.length; i++) {
			map.put(viewIds[i], layout.findViewById(viewIds[i]));
		}
		layout.setTag(map);
		return new ViewWithTag(layout);
	};

	/**
	 * 设置contentView里面空间与数据的显示关系
	 * 
	 * @param holder
	 *            存放控件的Map
	 * @param item
	 *            数据
	 * @param position
	 *            位置
	 */
	protected abstract void setView(HashMap<Integer, View> holder, E item, int position);

	/**
	 * 
	 * @see 例如： return setLayout(context, R.layout.item, R.id.tvage,
	 *      R.id.tvLover, R.id.tvName);
	 * 
	 * @return 返回带有初始tag的itemView
	 */
	protected abstract ViewWithTag initConvertView();

	/**
	 * 创建方式：getViewWithTag();
	 * 
	 * @author DJL E-mail:
	 * @date 2015-9-18 下午4:47:27
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	protected class ViewWithTag {
		private ViewWithTag(View view) {
			this.view = view;
		}

		public View view;
	}
}