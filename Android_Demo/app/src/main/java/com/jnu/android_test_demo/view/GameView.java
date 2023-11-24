package com.jnu.android_test_demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.jnu.android_test_demo.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameLoopThread;

    private final float NOT_A_VALID_TOUCH = -1;
    private float touchX = NOT_A_VALID_TOUCH;
    private float touchY = NOT_A_VALID_TOUCH;

    private int score = 0;
    private int countDown = 30;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoopThread = new GameLoopThread();
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    // 处理触摸事件
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = event.getX();
            touchY = event.getY();
        }
        return true;
    }

    // 销毁游戏循环线程
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gameLoopThread.end();
    }


    // 游戏循环线程
    private class GameLoopThread extends Thread {
        private boolean isLive = true;

        // 执行游戏逻辑
        @Override
        public void run() {
            super.run();

            ArrayList<GameSpirit> spirits = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                spirits.add(new GameSpirit(Math.random(), Math.random(), R.drawable.book_1));
                spirits.add(new GameSpirit(Math.random(), Math.random(), R.drawable.book_2));
                spirits.add(new GameSpirit(Math.random(), Math.random(), R.drawable.book_no_name));
            }

            int counter=0;
            while (isLive) {
                Canvas canvas = null;
                try {
                    canvas = GameView.this.getHolder().lockCanvas();
                    canvas.drawColor(Color.rgb(240, 240, 240));

                    Paint paint = new Paint();
                    paint.setColor(Color.rgb(0, 0, 0));
                    paint.setTextSize(50f);
                    canvas.drawText("学习书本数： " + GameView.this.score, 50f, 50f, paint);
                    canvas.drawText("剩余时间： " + GameView.this.countDown + "秒", 50f, 120f, paint);

                    for (GameSpirit spirit : spirits) {
                        spirit.detectTouch();
                        spirit.draw(canvas);
                        spirit.move();
                    }
                    // 重置触摸点
                    touchX = touchY = NOT_A_VALID_TOUCH;

                    // 倒计时结束
                    if(GameView.this.countDown==0)
                        isLive=false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null)
                        GameView.this.getHolder().unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(20);
                    // 计时器
                    counter+=20;
                    if(counter==1000){
                        counter=0;
                        GameView.this.countDown--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void end() {
            isLive = false;
        }

        // 游戏精灵类
        private class GameSpirit {
            private double relatedX, relatedY;
            private double direction;
            private final Bitmap scaledBitmap;
            private float width, height;
            private float X, Y;

            GameSpirit(double relatedX, double relatedY, int imageId) {
                this.relatedX = relatedX;
                this.relatedY = relatedY;
                this.direction = Math.random() * 2 * Math.PI;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            }

            public void draw(@NonNull Canvas canvas) {
                // 确定边界
                width = canvas.getWidth();
                height = canvas.getHeight();
                X = (width - scaledBitmap.getWidth()) / width;
                Y = (height - scaledBitmap.getHeight()) / height;
                // 绘制
                canvas.drawBitmap(scaledBitmap,
                        (float) relatedX * canvas.getWidth(), (float) relatedY * canvas.getHeight(), null);
            }

            public void move() {
                this.relatedX += Math.cos(direction) * 0.008;
                this.relatedY += Math.sin(direction) * 0.008;
                // 边界碰撞检测
                if (relatedX < 0) {
                    relatedX = 0;
                    direction = Math.PI - direction;
                }
                if (relatedX > X) {
                    relatedX = X;
                    direction = Math.PI - direction;
                }
                if (relatedY < 0) {
                    relatedY = 0;
                    direction = -direction;
                }
                if (relatedY > Y) {
                    relatedY = Y;
                    direction = -direction;
                }
                // 5%的概率改变方向
                if (Math.random() < 0.05)
                    direction = Math.random() * 2 * Math.PI;
            }

            public void detectTouch() {
                // 点击检测
                if (touchX >= 0 && touchY >= 0) {
                    // 点击到了
                    if (touchX >= relatedX * width && touchX <= relatedX * width + scaledBitmap.getWidth() &&
                            touchY >= relatedY * height && touchY <= relatedY * height + scaledBitmap.getHeight()) {
                        touchX = -1;
                        touchY = -1;
                        ++GameView.this.score;
                    }
                }
            }
        }
    }
}
