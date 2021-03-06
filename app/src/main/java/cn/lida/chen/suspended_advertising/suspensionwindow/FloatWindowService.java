package cn.lida.chen.suspended_advertising.suspensionwindow;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class FloatWindowService extends Service {

	/**
	 * 用于在线程中创建或移除悬浮窗。
	 */
	private Handler handler = new Handler();

	/**
	 * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
	 */
	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 开启定时器，广告的持续时间=间隔时间+间隔多久启动
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 10000);//间隔多久启动一次

            timer.scheduleAtFixedRate(new StopRefreshTask(), 0, 10000);


		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		timer.cancel();
		timer = null;
	}

	class RefreshTask extends TimerTask {

		@Override
		public void run() {
			// 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
			if ( !MyWindowManager.isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
                        MyWindowManager.createBigWindow(getApplicationContext());
                    }
				});
			}

		}

	}
    class StopRefreshTask extends TimerTask {

        @Override
        public void run() {
            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
            if (MyWindowManager.isWindowShowing()) {
                try {
                    Thread.sleep(5000);//广告的间隔时间
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            MyWindowManager.removeBigWindow(getApplicationContext());

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }




	/**
	 * 判断当前界面是否是桌面
	 */
	//private boolean isHome() {
		//ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		//List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		//return getHomes().contains(rti.get(0).topActivity.getPackageName());
	//}

	/**
	 * 获得属于桌面的应用的应用包名称
	 *
	 * @return 返回包含所有包名的字符串列表
	 */
	//private List<String> getHomes() {
		//List<String> names = new ArrayList<String>();
	//	PackageManager packageManager = this.getPackageManager();
	//	Intent intent = new Intent(Intent.ACTION_MAIN);
	//	intent.addCategory(Intent.CATEGORY_HOME);
	//	List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
	//			PackageManager.MATCH_DEFAULT_ONLY);
	//	for (ResolveInfo ri : resolveInfo) {
	//		names.add(ri.activityInfo.packageName);
	//	}
	//	return names;
//	}
}
