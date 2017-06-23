package com.yutian.exceptiontext.BezierCircle;

/**
 *     F(First point)   C(Center point)    S(Second point)
 *                      *F    *C    *S
 *
 *              *F                          *F
 *
 *              *C                          *C
 *
 *              *S                          *S
 *
 *                       *F    *C    *S
 *      The above graphic show which is First point which
 *          is center point and which is second point
 * Created by wuwenchuan on 2017/3/13.
 */
public class BezierPoint {

    public enum POINT_INDEX {FIRST, SECOND}

    /**
     * Define self Point Object
     */
    public class Point {
        public float x = 0;        //Point x
        public float y = 0;        //Point y

        public Point() {
        }
    }

    public Point mFirstPoint = new Point();
    public Point mCenterPoint = new Point();
    public Point mSecondPoint = new Point();
    private boolean isXAxis;

    private float mFirstOffset;         //Offset First
    private float mSecondOffset;        //Offset Second

    public BezierPoint() {
        this(0,0,0,true);
    }

    public BezierPoint(float x, float y, float offset, boolean isXAxis) {
        mCenterPoint.x = x;
        mCenterPoint.y = y;
        this.isXAxis = isXAxis;
        updateOffset(offset);
    }

    /**
     * Update center point information
     * @param x
     * @param y
     */
    public void updatePoint(float x, float y) {
        mCenterPoint.x = x;
        mCenterPoint.y = y;
        resetPoints();
    }

    /**
     * Change this point control orientation
     * @param isXAxis horizontal orientation
     */
    void resetAxies(boolean isXAxis) {
        this.isXAxis = isXAxis;
        resetPoints();
    }

    /**
     * Reset all points offect distance
     * @param offset
     */
    void updateOffset(float offset) {
        mFirstOffset = offset;
        mSecondOffset = offset;
        resetPoints();
    }

    /**
     * Reset one index offset distance
     * @param index Point index
     * @param offset Point offset distance
     */
    void updateOffset(POINT_INDEX index, float offset) {
        switch (index) {
            case FIRST:
                mFirstOffset = offset;
                break;
            case SECOND:
                mSecondOffset = offset;
                break;
            default:
                return;
        }
        resetPoints();
    }

    /**
     * Regenerate the bezier points
     */
    void resetPoints() {
        if (isXAxis) {               //horizontal orientation
            mFirstPoint.x = mCenterPoint.x;
            mFirstPoint.y = mCenterPoint.y + mFirstOffset;
            mSecondPoint.x = mCenterPoint.x;
            mSecondPoint.y = mCenterPoint.y - mSecondOffset;
        } else {                     //vertical orientation
            mFirstPoint.y = mCenterPoint.y;
            mFirstPoint.x = mCenterPoint.x - mFirstOffset;
            mSecondPoint.y = mCenterPoint.y;
            mSecondPoint.x = mCenterPoint.x + mSecondOffset;
        }
    }
}
