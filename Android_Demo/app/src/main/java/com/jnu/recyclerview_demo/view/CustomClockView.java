package com.jnu.recyclerview_demo.view;

import static java.lang.Math.min;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.jnu.recyclerview_demo.R;

public class CustomClockView extends View {

    private Bitmap dialBitmap, hourHandBitmap, minuteHandBitmap, secondHandBitmap;
    public CustomClockView(Context context) {
        super(context);
        init();
    }

    public CustomClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化图片资源
        dialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dial);
        hourHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour_hand_1);
        minuteHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minute_hand_1);
        secondHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_hand_1);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 调整图片的大小
        int screen_size=min(getHeight(),getWidth());
        int hourHand_size=screen_size;
        int minuteHand_size=screen_size*4/3;
        int secondHand_size=screen_size*4/3;

        dialBitmap = Bitmap.createScaledBitmap(dialBitmap, screen_size, screen_size, true);
        hourHandBitmap = Bitmap.createScaledBitmap(hourHandBitmap, hourHand_size, hourHand_size, true);
        minuteHandBitmap = Bitmap.createScaledBitmap(minuteHandBitmap, minuteHand_size, minuteHand_size, true);
        secondHandBitmap = Bitmap.createScaledBitmap(secondHandBitmap, secondHand_size, secondHand_size, true);

        int width = getWidth();
        int height = getHeight();

        // 绘制表盘
        canvas.drawBitmap(dialBitmap, (width- screen_size)/2f, (height- screen_size)/2f, null);

        // 获取当前时间
        long currentTime = System.currentTimeMillis();
        int hours = (int) ((currentTime / (1000 * 60 * 60)) % 12);
        int minutes = (int) ((currentTime / (1000 * 60)) % 60);
        int seconds = (int) ((currentTime / 1000) % 60);

        // 计算时针、分针、秒针的角度
        float hourAngle = (hours + minutes / 60.0f) * 360 / 12;
        float minuteAngle = (minutes + seconds / 60.0f) * 360 / 60;
        float secondAngle = seconds * 360f / 60f;

        // 绘制时针
        canvas.save();
        canvas.rotate(hourAngle, width / 2f, height / 2f);
        canvas.drawBitmap(hourHandBitmap, (width- hourHandBitmap.getWidth())/2f, (height- hourHandBitmap.getHeight())/2f, null);
        canvas.restore();

        // 绘制分针
        canvas.save();
        canvas.rotate(minuteAngle, width / 2f, height / 2f);
        canvas.drawBitmap(minuteHandBitmap, (width- minuteHandBitmap.getWidth())/2f, (height- minuteHandBitmap.getHeight())/2f, null);
        canvas.restore();

        // 绘制秒针
        canvas.save();
        canvas.rotate(secondAngle, width / 2f, height / 2f);
        canvas.drawBitmap(secondHandBitmap, (width- secondHandBitmap.getWidth())/2f,(height- secondHandBitmap.getHeight())/2f, null);
        canvas.restore();

        // 重绘
        postInvalidateDelayed(1000);
    }
}
