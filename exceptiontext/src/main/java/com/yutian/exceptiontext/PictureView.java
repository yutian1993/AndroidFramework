package com.yutian.exceptiontext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by wuwenchuan on 2017/1/19.
 */
public class PictureView extends ImageView {
    private String TAG = "wuwenchuan";

    private Context mContext;
    private int mScaleRadio = 1;
    private int mCurrentScaleRadio = 0;
    private boolean isZoom = false;
    private Matrix mDefaultMatrix;
    private float mDistance = -1;
//    private float mImaegRadioWH;       //Image radio w/h
//    private float mImageWith;
//    private float mImageHeight;
//    private int mDisplayWidth;
//    private int mDisplayHeight;

    private FingerInfo mFirstFinger = new FingerInfo();
//    private FingerInfo mSecondFinger = new FingerInfo();

    private boolean isMoreFingers = false;

    public PictureView(Context context) {
        this(context, null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        mDisplayWidth = getMeasuredWidth();
//        mDisplayHeight = getMaxHeight();
        mDefaultMatrix = getMatrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getPointerCount()) {
            case 1:            //One finger touch
//                Log.e(TAG, "one fingers");

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Log.e(TAG, "Action down");
                        isMoreFingers = false;
                        if (mTouchEventCountThread.mTouchCount == 0) {
                            postDelayed(mTouchEventCountThread, 500);
                        }
                        mFirstFinger.initFingerInfo(event);
                        break;
                    case MotionEvent.ACTION_UP:
//                        Log.e(TAG, "Action up");
                        if (isMoreFingers) {
                            mTouchEventCountThread.resetStatus();
                            mFirstFinger.resetFingerInfo();
                        } else {
                            mTouchEventCountThread.mTouchCount++;
                            if (mTouchEventCountThread.isLongClick) {
                                mTouchEventCountThread.resetStatus();

                                //Send message to handler
                                Message msg = new Message();
                                msg.arg1 = 10000;
                                mTouchEventHandler.sendMessage(msg);
                            }
                        }
                        isMoreFingers = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "Action move");
                        if (isMoreFingers)
                            break;
                        if (mFirstFinger.mFingerID == event.getPointerId(MotionEventCompat.getActionIndex(event))) {
                            translateImage(event.getX() - mFirstFinger.mFingerEndX, event.getY() - mFirstFinger.mFingerEndY);
                            mFirstFinger.mFingerEndX = event.getX();
                            mFirstFinger.mFingerEndY = event.getY();
                        } else {
                            mFirstFinger.mFingerID = -1;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mFirstFinger.mFingerID = -1;
                        break;
                    default:
                        break;
                }
                break;
            case 2:           //Two fingers touch
//                Log.e(TAG, "two fingers with action status: " + event.getAction());
                isZoom = true;
                isMoreFingers = true;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "Two finger move");
//                        if (mFirstFinger.mFingerStartX == -1) {
//                            mFirstFinger.initFingerInfo(event);
//                        } else if (mSecondFinger.mFingerStartX == -1) {
//                            mSecondFinger.mFingerID = 2;
//                            mSecondFinger.mFingerStartX = event.getX(1);
//                            mSecondFinger.mFingerStartY = event.getY(1);
//                            mSecondFinger.mFingerEndX = event.getX(1);
//                            mSecondFinger.mFingerEndY = event.getY(1);
//                        } else {
                            float dx = event.getX(0) - event.getX(1);
                            float dy = event.getY(0) - event.getY(1);
                            dx = (float)Math.sqrt(dx*dx+dy*dy);          //Just don't declare so many argument in loop
                            if (mDistance == -1)
                                mDistance = dx;
                            else {
//                                Log.e(TAG, "mDistance: " + mDistance + " dx: " + dx);
                                dy = dx;
                                dx = dx - mDistance;
                                mDistance = dy;
                                dx /=(float)1000;
                                scaleImage(dx, dx);
                            }

//                        }

                        break;
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
//                        Log.e(TAG, "Two finger action down");
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    default:
                        mTouchEventCountThread.resetStatus();
                        mFirstFinger.resetFingerInfo();
//                        mSecondFinger.resetFingerInfo();
                        mDistance = -1;
                        break;
                }
                break;
            default:
//                Log.e(TAG, "finger default");
                isMoreFingers = true;
                break;
        }
        return true;
    }

    /**
     * Directly set image byte array to ImageView
     *
     * @param imgByte Image byte
     * @return Right set or not
     */
    public boolean setPictureByte(byte[] imgByte) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        if (bitmap == null)
            return false;
        setImageBitmap(bitmap);
//        mImaegRadioWH = bitmap.getWidth() / bitmap.getHeight();
//        mImageWith = bitmap.getWidth();
//        mImageHeight = bitmap.getHeight();
        return true;
    }

    private TouchEventHandler mTouchEventHandler = new TouchEventHandler();
    private TouchEventCountThread mTouchEventCountThread = new TouchEventCountThread();

    public class TouchEventCountThread implements Runnable {
        public int mTouchCount = 0;
        public boolean isLongClick = false;

        @Override
        public void run() {
            Message msg = new Message();
            if (mTouchCount == 0) {
                isLongClick = true;
            } else {
                msg.arg1 = mTouchCount;
                mTouchEventHandler.sendMessage(msg);
                mTouchCount = 0;
            }
        }

        public void resetStatus() {
            mTouchCount = 0;
            isLongClick = false;
        }
    }

    public class TouchEventHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            Toast.makeText(mContext, "touch " + msg.arg1 + " time", Toast.LENGTH_SHORT).show();
            Message imgMsg = new Message();
            if (msg.arg1 == 2) {
                imgMsg.arg1 = 1;
                mImageHandler.sendMessage(imgMsg);
            }
        }
    }

    private ImageHandler mImageHandler = new ImageHandler();

    public class ImageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
                if (isZoom) {      //Dobule click image will reset image
                    resetImage();
                    isZoom = false;
                } else {           //Normal double click just zoom out image
                    scaleImage((float) 0.2, (float) 0.2);
                }
            }
        }
    }

    protected void scaleImage(float dx, float dy) {
        // Compute the matrix
//        Log.e(TAG + "scaleImage", "dx: " + dx + "dy: " + dy);

        mCurrentScaleRadio += dx;        //Remember the scale radio

        //Too big or small will reset image status
        if (mCurrentScaleRadio > 2 || mCurrentScaleRadio < -1) {
            resetImage();
            mCurrentScaleRadio = 0;
            return;
        }

        Matrix m = this.getImageMatrix();
        m.postScale((float) 1+dx, (float) 1+dy);
        this.setImageMatrix(m);
        invalidate();
    }

    protected void translateImage(float dx, float dy) {
//        Log.e(TAG + "translateImage", "dx: " + dx + " dy: " + dy);

        Matrix m = getImageMatrix();
        m.postTranslate(dx, dy);
        setImageMatrix(m);
        invalidate();
    }

    protected void resetImage() {
//        Log.e(TAG + "resetImage", "No thing!");
        setImageMatrix(mDefaultMatrix);
        invalidate();
    }

    public static class FingerInfo {
        public int mFingerID = -1;
        public float mFingerStartX = -1;
        public float mFingerStartY = -1;
        public float mFingerEndX = -1;
        public float mFingerEndY = -1;
        public FINGER_STATUS mStatus;

        public void resetFingerInfo() {
            mFingerID = -1;
            mFingerStartX = -1;
            mFingerStartY = -1;
            mFingerEndX = -1;
            mFingerEndY = -1;
        }

        public void initFingerInfo(MotionEvent motionEvent) {
            mFingerID = motionEvent.getPointerId(motionEvent.getActionIndex());
            mFingerStartX = motionEvent.getX();
            mFingerStartY = motionEvent.getY();
            mFingerEndX = motionEvent.getX();
            mFingerEndY = motionEvent.getY();
        }

        enum FINGER_STATUS {FINGER_DOWN, FINGER_UP, FINGER_MOVE, FINGER_ERROR}

    }


}
