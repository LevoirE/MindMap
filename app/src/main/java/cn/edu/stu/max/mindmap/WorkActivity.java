package cn.edu.stu.max.mindmap;

import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class WorkActivity extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector = null;
    private int mActivePointerId;
    private int INVALID_POINTER_ID;
    private static final String TAG = "WorkActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        mActivePointerId = INVALID_POINTER_ID =
                ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).getId();

        //加入返回箭头
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
    }


    private float mLastTouchX;
    private float mLastTouchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1)
            return scaleGestureDetector.onTouchEvent(event);

        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final float x = MotionEventCompat.getX(event, pointerIndex);
        final float y = MotionEventCompat.getY(event, pointerIndex);
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                // Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                // Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                // Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).scrollBy(-(int)dx, -(int)dy);
                // Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }
        }
        return true;
    }

    //实现返回功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final class ScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float scaleFactor = 1.0f;

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 2.0f));
            Log.d(TAG, "onScale: " + scaleFactor);
            ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setScaleX(scaleFactor);
            ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).setScaleY(scaleFactor);
            return true;
        }
    }

    //活动转换之间都调用沉浸模式全屏
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
