package com.etech.myteam.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView{
	
	/**
     * ��Ҫ���Ƶ�����
     */
    private String mText;
    /**
     * �ı�����ɫ
     */
    private int mTextColor;
    /**
     * �ı��Ĵ�С
     */
    private int mTextSize;
    /**
     * ����ʱ�����ı����Ƶķ�Χ
     */
    private Rect mBound;
    private Paint mPaint;

    public MyTextView(Context context) {
        this(context, null);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
	
	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mText = "hello world";
        mTextColor = Color.BLACK;
        mTextSize = 100;

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //��û����ı��Ŀ�͸�
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }
	
	@Override
    protected void onDraw(Canvas canvas) {
        //��������
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

}
