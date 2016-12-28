package cn.lida.chen.suspended_advertising.suspensionwindow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.lida.chen.suspended_advertising.R;
import cn.lida.chen.suspended_advertising.loader.GlideImageLoader;

public class FloatWindowBigView extends LinearLayout {
	private Banner banner_big;
	private List<?> iamges=new ArrayList<>();
	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.banner_big);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		banner_big= (Banner) findViewById(R.id.banner_big);
		String[] urls = getResources().getStringArray(R.array.url);
		List list = Arrays.asList(urls);
		iamges=new ArrayList(list);
		banner_big.setImages(iamges)
				.setImageLoader(new GlideImageLoader())
				.setBannerStyle(BannerConfig.NOT_INDICATOR)
				.setBannerAnimation(Transformer.Default)
				.setDelayTime(2000)
				.isAutoPlay(true)
				.setViewPagerIsScroll(false).
				start().startAutoPlay();


	}


}
