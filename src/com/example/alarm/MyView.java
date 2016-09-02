package com.example.alarm;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	private Paint mPaint;
	private int longRadius = 50;
	private int mRadius = 300;
	private int shortRadius = 30;
	private int distance = 9;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(8);
		mPaint.setStyle(Style.STROKE);
		mPaint.setDither(true);
		mPaint.setTextSize(25);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 画刻度
		drawKeDu(canvas);
		// 画时间数字
		// drawText(canvas);
		// 画指针
		drawHands(canvas);
		postInvalidateDelayed(1000);

	}

	private void drawKeDu(Canvas canvas) {
		float x = getWidth() / 2;
		float y = getHeight() / 2;
		canvas.translate(x, y);
		float degree = 0;
		mPaint.setColor(Color.BLUE);
		canvas.drawCircle(0, 0, mRadius, mPaint);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);

		for (int i = 0; i < 360; i = i + 6) {
			if (i % 30 == 0) {
				canvas.drawLine(0, 0 + mRadius - distance, 0, 0 + mRadius
						- longRadius, mPaint);

			} else {
				canvas.drawLine(0, 0 + mRadius - distance, 0, 0 + mRadius
						- shortRadius, mPaint);

			}
			canvas.rotate(6);
		}
	}

	/**
	 * 画指针，时分秒
	 * 
	 * @param canvas
	 */
	private void drawHands(Canvas canvas) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // 时
		int minute = calendar.get(Calendar.MINUTE); // 分
		int second = calendar.get(Calendar.SECOND); // 秒
		System.out.println("现在是" + hour + ":" + minute + ":" + second);
		mPaint.setTextSize(25);
		mPaint.setStrokeWidth(2);
		canvas.drawText("现在是" + hour + ":" + minute + ":" + second, -300, -400,
				mPaint);
		int angleHour = 180 + (hour % 12) * 360 / 12; // 时针转过的角度
		int angleMinute = 180 + minute * 360 / 60; // 分针转过的角度
		int angleSecond = 180 + second * 360 / 60; // 秒针转过的角度

		// 时针
		canvas.save();
		canvas.rotate(angleHour);
		drawlinnes(canvas, 120, 12, Color.BLUE);
		canvas.restore();
		// 分针
		canvas.save();
		canvas.rotate(angleMinute);
		drawlinnes(canvas, 180, 8, Color.BLACK);
		canvas.restore();

		// 秒针
		canvas.save();
		canvas.rotate(angleSecond);
		drawlinnes(canvas, 250, 5, Color.RED);
		canvas.restore();
		canvas.drawCircle(0, 0, 5, mPaint);
		canvas.restore();
	}

	/**
	 * 画时间，数字
	 * 
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		canvas.save();
		int time = 6;
		for (int i = 1; i < 13; i++) {
			if (time > 12) {
				time -= 12;
			}
			canvas.drawText(String.valueOf(time), 0, mRadius - longRadius - 20,
					mPaint);
			// canvas.drawRect(left, top, right, bottom, paint);
			canvas.rotate(30);
			time++;

		}
		canvas.restore();
	}

	/**
	 * 画指针
	 * 
	 * @param canvas
	 *            画布
	 * @param length
	 *            指针长度
	 * @param size
	 *            画笔 线宽
	 * @param color
	 *            画笔颜色
	 */
	private void drawlinnes(Canvas canvas, int length, int size, int color) {
		mPaint.setStrokeWidth(size);
		mPaint.setColor(color);
		canvas.drawLine(0, -30, 0, length - 10, mPaint);
	}

}
