package com.example.democube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.djl.util.androidUtil.DJLUtils;
import com.djl.util.androidUtil.SystemTool;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	private GLSurfaceView msv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		msv = (GLSurfaceView) findViewById(R.id.sv);
		msv.setRenderer(new MyRenderer());
		String phoneIMEI = SystemTool.getPhoneIMEI(this);
		DJLUtils.log(phoneIMEI);
	}

	@Override
	protected void onPause() {
		msv.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		msv.onResume();
		super.onResume();
	}

	public void c(View v) {
		startActivity(new Intent(this, RotationVectorDemo.class));
	}

	class MyRenderer implements Renderer {
		private final float[] mRotationMatrix = new float[16];

		public MyRenderer() {
			mRotationMatrix[0] = 1;
			mRotationMatrix[4] = 1;
			mRotationMatrix[8] = 1;
			mRotationMatrix[12] = 1;
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			gl.glDisable(GL10.GL_DITHER);
			gl.glClearColor(0.5f, 0, 1, 1);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glViewport(0, 0, width, height);
			// set projection matrix
			float ratio = (float) width / height;
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			// clear screen
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			// set-up modelview matrix
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(0, 0, -3.0f);
			gl.glMultMatrixf(mRotationMatrix, 0);

			// draw our object
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		}

	}
}
