package com.example.administrator.myviewdraghelper;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class MyViewGroup extends LinearLayout {
    private ViewDragHelper mViewDraghelper;
    private View firstView;
    private View seconedView;
    private Point point=new Point();
    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mViewDraghelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child==getChildAt(0) | child==getChildAt(1);
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild==firstView){
                    mViewDraghelper.settleCapturedViewAt(point.x,point.y);
                    invalidate();
                }else if (releasedChild==seconedView){

                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 2;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 2;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int paddingLeft=getPaddingLeft();
                int width=getWidth()-getChildAt(0).getMeasuredWidth()-getPaddingRight();
                return Math.min(Math.max(left, paddingLeft), width);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int paddingTop=getPaddingTop();
                int bottom=getHeight()-getChildAt(0).getMeasuredHeight()-getPaddingBottom();

                return Math.min(Math.max(paddingTop,top),bottom);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        point.x= (int) firstView.getX();
        point.y= (int) firstView.getY();
        firstView.layout(l, (int) -firstView.getY(),r,t);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDraghelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDraghelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstView=getChildAt(0);
        seconedView=getChildAt(1);
    }

    @Override
    public void computeScroll() {
        if (mViewDraghelper.continueSettling(true)){
            invalidate();
        }
    }
}
