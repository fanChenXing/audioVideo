package com.fanchen.audiovideo.week1;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.fanchen.audiovideo.R;

/**
 * 功能描述:在Android平台绘制一张图片，使用至少3种不同的API,ImageView、SurfaceView、自定义View
 * 作者:fanChen
 * 时间:2019/4/14
 * 待处理问题：surfaceView设置为wrap_content时会把屏幕全占了??设置surfaceView的宽高
 * 联系方式：1546479204@qq.com
 */
public class Week1Activity extends AppCompatActivity {

    ImageView iv_img;
    SurfaceView sv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewDrawBitmap();
        surfaceViewDrawBitmap();
    }

    /**
     * 使用ImageView绘制图片
     */
    private void imageViewDrawBitmap() {
        iv_img = findViewById(R.id.iv_img);
        iv_img.setImageResource(R.mipmap.img1);
    }

    /**
     * 使用surfaceView绘制图片
     */
    private void surfaceViewDrawBitmap() {
        sv_img = findViewById(R.id.sv_img);
        SurfaceHolder holder = sv_img.getHolder();
        holder.addCallback(new MyCallback());
    }

    public class MyCallback implements SurfaceHolder.Callback, Runnable {

        Thread thread;
        SurfaceHolder holder;

        public MyCallback() {
            thread = new Thread(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            this.holder = holder;
            thread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        @Override
        public void run() {
            Canvas canvas = holder.lockCanvas();
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.img1);
//            canvas.drawBitmap(bm, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
            Drawable drawable = getResources().getDrawable(R.mipmap.img1);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
