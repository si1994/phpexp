package com.quirktastic.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.quirktastic.utility.AppContants;
import com.quirktastic.utility.Logger;
import com.quirktastic.utility.ScrollerCustomDuration;
import com.quirktastic.utility.SendRequestListener;

import java.lang.reflect.Field;

public class SwipViewpager extends ViewPager {

    private float initialXValue;
    private SwipeDirection direction;
    private SendRequestListener sendRequestListener;

    public SwipViewpager(Context context) {
        super(context);
        postInitViewPager();
    }

    public SwipViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.all;
        AppContants.IS_SEND_FRIEND_REQUEST=true;
        postInitViewPager();
    }

    private ScrollerCustomDuration mScroller = null;

    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if (this.direction == SwipeDirection.all) return true;

        if (direction == SwipeDirection.none)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;


                Logger.e("DIS",""+diffX);


                if (diffX > 50) {

                    if(AppContants.IS_SEND_FRIEND_REQUEST)
                    {
                        AppContants.IS_SEND_FRIEND_REQUEST=false;
                        sendRequestListener.sendRequestCallback();
                    }
                }


                if (diffX > 0 && direction == SwipeDirection.right) {

                    // swipe from left to right detected

                    return false;
                } else if (diffX < 0 && direction == SwipeDirection.left) {


                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }

    public void setSendRequestListener(SendRequestListener sendRequestListener) {
        this.sendRequestListener = sendRequestListener;
    }

    public enum SwipeDirection {
        all, left, right, none ;
    }
}