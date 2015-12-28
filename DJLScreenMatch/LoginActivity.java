package com.huhuiying.ssgj;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.djl.androidutils.SimpleActivity;
import com.djl.javaUtils.CipherUtils;
import com.djl.javaUtils.DJLUtils;
import com.djl.javaUtils.StringUtils;
import com.djl.javaUtils.ViewPagerAdapterBannar;
import com.ssjr.bean2.HeadBean;
import com.ssjr.network.NetTaskByVolley;
import com.ssjr.network.SimpleVolleyListener;

/**
 * @author DJL E-mail:
 * @date 2015-10-23 上午9:30:28
 * @version 1.0
 * @parameter
 * @since
 */
public class LoginActivity extends SimpleActivity {

	private boolean nottomain;
	private String pwd2;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivImageVerificationCode:
			initVerifiImage();
			break;
		case R.id.ivTitlebarBack:
			finish_();
			break;
		case R.id.tvLogin:
			String verifiImage = getEditText(R.id.etImageVerificationCode).getText().toString()
					.trim();
			String pwd = getEditText(R.id.etLoginPWD).getText().toString().trim();
			// 登录
			if (StringUtils.isEmpty(pwd)) {
				// 密码为空
				getTextView(R.id.tvLoginErrorPrompt).setText(I._e39ffe99e93cb6cc8d5978ed21660740);
				getEditText(R.id.etLoginPWD).requestFocus();
				return;
			}
			// if (errorTime > 3 && (!VerifiImageVerfi(verifiImage))) {
			// // 密码错误次数大于3次,并且图片验证错误
			// getTextView(R.id.tvLoginErrorPrompt).setText(I._6eaf768e6084f91f1ea6c1b2ca83c2f3);
			// getEditText(R.id.etImageVerificationCode).requestFocus();
			// }
			if (mPhoneVerificationErrorTimes > 3 && StringUtils.isEmpty(getIVContent())) {
				showImageVerifError(false, I._42ae8eb3227929567f7b135724a8469e);
			} else {
				// 有密码，且错误少于3次或图片验证正确
				showImageVerifError(false, I._d41d8cd98f00b204e9800998ecf8427e);
				getButton(R.id.tvLogin).setEnabled(false);
				pwd2 = pwd;
				NetTaskByVolley.login(this, false, getIntent().getStringExtra(I._f7a42fe7211f98ac7a60a285ac3a9e87), pwd,
						getImageVerifID(), verifiImage, new SimpleVolleyListener<String>(mHandler));
			}
			break;
		case R.id.tvForgetPWD:
			Intent intent = new Intent(this, SetPWDActivity.class);
			intent.putExtras(getIntent());
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void onRealSuccess(int code, String body) {
		switch (code) {
		case 2030:
			try {
				JSONObject jb = new JSONObject(body);
				// 保存注册信息
				MyApplication.getInstance().getUser().setUserID(I._d41d8cd98f00b204e9800998ecf8427e + jb.getInt(I._57c7869106fd3be06d0e273a1fd8bbd3))
						.setPhone(getIntent().getStringExtra(I._f7a42fe7211f98ac7a60a285ac3a9e87)).setP(CipherUtils.md5(pwd2))
						.setToken(jb.getString(I._459a6f79ad9b13cbcb5f692d2cc7a94d));
				DJLUtils.log(I._683801c9713f4cffd94e9360c4e405db);
				if (getIntent().getBooleanExtra(I._5113d018ecc322e6ecccfc10f68ab1a1, false)) {
					// 清空手势密码
					getUser().setGesturePWD(I._d41d8cd98f00b204e9800998ecf8427e);
					getUser().setGesturePWDEnable(false);
				}
				if (nottomain) {
					MyApplication.getInstance().getActivityManager()
							.finishActivity(InputPhoneActivity.class);
					finish_();
				} else {
					toMain();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void toMain() {
		startActivity(new Intent(this, MainActivity.class));
		MyApplication.getInstance().getActivityManager().finishOthersActivity(MainActivity.class);
	}

	@Override
	public void onEnmpty(int code) {
	}

	@Override
	public boolean onInnerFail(int code, HeadBean Head) {
		switch (code) {
		case 2030:
			LoginError();

			break;

		default:
			break;
		}
		showError(Head.Message);
		return super.onInnerFail(code, Head);
	}

	private void showError(String error) {
		getTextView(R.id.tvLoginErrorPrompt).setText(error + I._d41d8cd98f00b204e9800998ecf8427e);

	}

	@Override
	public void onFinsh(int code) {
		switch (code) {
		case 2030:
			// 登录
			getButton(R.id.tvLogin).setEnabled(true);
			break;

		default:
			break;
		}
	}

	@Override
	public int initContent() {
		return R.layout.activity_login;
	}

	@Override
	public void initData() {
		nottomain = getIntent().getBooleanExtra(I._64c295cdd83249db6c881e58e114502d, false);
	}

	@Override
	public void initView() {
		getTextView(R.id.tvTitlebar).setText(I._3936b1d80aaa77287f7783ee17c88835);
		List<View> views = new ArrayList<>();
		ImageView iv = new ImageView(this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.test_bannar_1);
		views.add(iv);
		iv = new ImageView(this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.test_bannar_2);
		views.add(iv);
		iv = new ImageView(this);
		iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.test_bannar_1);
		views.add(iv);
		getViewPager(R.id.viewpagerBannar).setAdapter(new ViewPagerAdapterBannar(views, this));
		// 添加下划线
		getTextView(R.id.tvForgetPWD).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		// 设置点击事件
		SetOnClick(R.id.ivTitlebarBack, R.id.tvLogin, R.id.tvForgetPWD,
				R.id.ivImageVerificationCode);
	}

	/************************************** 图片验证码 ***************************************************/
	private String SMS_TYPE = I._d3d9446802a44259755d38e6d163e820;
	private boolean isIVShow;
	private String mIVID;
	private int mPhoneVerificationErrorTimes;

	// 初始化图片验证组件
	private void initVerifiImage() {
		isIVShow = true;
		getLinearLayout(R.id.llImageVerificationCode).setVisibility(View.VISIBLE);
		// if (mVerifyCode == null) {
		// mVerifyCode = new VerifyCode();
		// }
		// // 设置图片验证码
		// getImageView(R.id.ivImageVerificationCode).setImageBitmap(mVerifyCode.createBitmap());
		NetTaskByVolley.getImageVerif(getImageVerifID(), SMS_TYPE, this,
				getImageView(R.id.ivImageVerificationCode));
	}

	private String getImageVerifID() {
		if (mIVID == null) {
			mIVID = StringUtils.getRandom(8);
		}
		return mIVID;
	}

	private void LoginError() {
		if (mPhoneVerificationErrorTimes >= 3) {
			// 短信发送超过三次
			if (isIVShow) {
				showImageVerifError(true, I._d41d8cd98f00b204e9800998ecf8427e);
			} else {
				showImageVerifError(true, I._d41d8cd98f00b204e9800998ecf8427e);
				return;
			}
		}
		mPhoneVerificationErrorTimes++;
	}

	private void showImageVerifError(boolean isRefresh, CharSequence error) {
		// 短信验证码输错超三回,并且图片验证错误
		showError(error + I._d41d8cd98f00b204e9800998ecf8427e);
		if (isRefresh) {
			getEditText(R.id.etImageVerificationCode).requestFocus();
			initVerifiImage();
		}
	}

	/** 获取一输入的图片验证码 */
	private String getIVContent() {
		return getEditText(R.id.etImageVerificationCode).getText().toString().trim();
	}

	/************************************** 图片验证码 ***************************************************/
}