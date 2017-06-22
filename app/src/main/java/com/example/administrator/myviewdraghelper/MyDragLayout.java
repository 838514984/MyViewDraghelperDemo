package com.example.administrator.myviewdraghelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class MyDragLayout extends FrameLayout {
    ViewDragHelper viewDragHelper;
    View childView;


    public MyDragLayout(@NonNull Context context) {
        super(context);
    }

    public MyDragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        viewDragHelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (Math.abs(releasedChild.getTop())>releasedChild.getMeasuredHeight()/2){
                    viewDragHelper.settleCapturedViewAt(0,-releasedChild.getMeasuredHeight());
                }else{
                    viewDragHelper.settleCapturedViewAt(0,0);
                }
                invalidate();
            }



            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                viewDragHelper.captureChildView(getChildAt(0),pointerId);
            }


            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int maxheight=child.getMeasuredHeight();
                return Math.min(Math.min(0,top),maxheight);
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //int[] locs=new int[2];
        //this.getLocationOnScreen(locs);
        Log.e("xxxx","parentTop: "+top+"\nparentleft: "+left+"\nparentright: "+right+"\nparentbttom: "+bottom);
        //Log.e("xxxx","parentTop: "+top+"  childView.getY()"+childView.getY()+"   childview.getHeight: "+childView.getHeight()+" framelayout.getRawX: "+locs[1]);
        //因为在xml中设置了margintop，所以在最后一个参数换成了0，如果换成top的话还会显示一部分。
        childView.layout(left,top-childView.getHeight(),right,0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childView=getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }


    public void close(){

        boolean b=viewDragHelper.smoothSlideViewTo(childView,0,-childView.getMeasuredHeight());
        invalidate();
        Log.e("xxx","boolean close: "+b);
    }

    public void open(){

        boolean b=viewDragHelper.smoothSlideViewTo(childView,0,0);
        invalidate();
        Log.e("xxx","boolean open: "+b);
    }

}
