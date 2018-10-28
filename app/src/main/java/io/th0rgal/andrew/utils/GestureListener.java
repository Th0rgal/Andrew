package io.th0rgal.andrew.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;
import io.th0rgal.andrew.HomeActivity;

/**
 * Created by Jerry on 4/18/2018.
 * Improved by Thomas on 10/25/2018
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private int MIN_SWIPE_DISTANCE_X = 100;
    private int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private int MAX_SWIPE_DISTANCE_X = 4000;
    private int MAX_SWIPE_DISTANCE_Y = 4000;

    // Source activity
    private HomeActivity activity;

    public HomeActivity getActivity() {
        return activity;
    }

    public GestureListener(HomeActivity activity) {
        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe

        if ((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
            //LEFT SWIPE
            if (deltaX > 0)
                this.activity.onGestureMade(GestureType.LEFT_SWIPE);
            //RIGHT SWIPE
            else
                this.activity.onGestureMade(GestureType.RIGHT_SWIPE);

        if ((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
            //UP SWIPE
            if (deltaY > 0)
                this.activity.onGestureMade(GestureType.UP_SWIPE);
            //DOWN SWIPE
            else
                this.activity.onGestureMade(GestureType.DOWN_SWIPE);
        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        this.activity.onGestureMade(GestureType.SINGLE_TAP);
        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.activity.onGestureMade(GestureType.DOUBLE_TAP);
        return true;
    }

}
