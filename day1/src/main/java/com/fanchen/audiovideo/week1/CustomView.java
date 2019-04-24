package com.fanchen.audiovideo.week1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fanchen.audiovideo.R;

/**
 * 功能描述:自定义View绘制图片
 * 作者:fanChen
 * 时间:2019/4/14
 * 联系方式：1546479204@qq.com
 */
public class CustomView extends View {

    private Bitmap bm;
//    Drawable drawable;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.img1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bm.getWidth(), bm.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bm, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
//        drawable.draw(canvas);
    }
}
