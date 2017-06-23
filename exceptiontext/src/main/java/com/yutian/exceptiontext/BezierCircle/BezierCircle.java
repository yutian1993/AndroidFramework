package com.yutian.exceptiontext.BezierCircle;

import android.graphics.Path;



/**
 *                      *     *1    *
 *
 *              *                           *
 *
 *              *0                          *2
 *
 *              *                           *
 *
 *                       *     *3    *
 *            The above graphic show point index
 * Created by wuwenchuan on 2017/3/13.
 */
public class BezierCircle {

    public enum POINT_ORIENTATION {LEFT, TOP, RIGHT, BOTTOM}

    public Path mPath = new Path();
    public BezierPoint[] mCorePoints = new BezierPoint[4];
    public float radius;
    public float offset;

    public BezierCircle() {
    }

    /**
     * Regenerate main points information
     */
    public void regeneratePoint() {
        generateCircle();
        mCorePoints[0] = new BezierPoint(-radius, 0, offset, true);
        mCorePoints[2] = new BezierPoint(radius, 0, offset, true);
        mCorePoints[1] = new BezierPoint(0, radius, offset, false);
        mCorePoints[3] = new BezierPoint(0, -radius, offset, false);
    }

    /**
     * Update circle point
     * @param orientation point orientation
     * @param x x posisiton
     * @param y y posisiton
     */
    public void updatePoint(POINT_ORIENTATION orientation, float x, float y) {
        int index = 0;
        switch (orientation) {
            case LEFT:
                index = 0;
                break;
            case TOP:
                index = 1;
                break;
            case RIGHT:
                index = 2;
                break;
            case BOTTOM:
                index =3;
                break;
            default:
                index = -1;
                break;
        }
        if (index >= 0)
            mCorePoints[index].updatePoint(x,y);
    }

    /**
     * Reset all point offset
     * @param offset All point offset
     */
    public void resetOffset(float offset) {
        this.offset = offset;
        regeneratePoint();
    }

    /**
     * Default draw circle
     */
    public void generateCircle() {
        if (radius == 0)
            radius = 20;
        if (offset == 0)
            offset = radius / 1.8f;
    }

    /**
     * Base on points to generate line path
     *
     * @return path
     */
    public Path regenerateNewPath() {
        mPath.reset();
        mPath.moveTo(mCorePoints[0].mCenterPoint.x, mCorePoints[0].mCenterPoint.y);
        mPath.cubicTo(mCorePoints[0].mFirstPoint.x, mCorePoints[0].mFirstPoint.y,
                mCorePoints[1].mFirstPoint.x, mCorePoints[1].mFirstPoint.y,
                mCorePoints[1].mCenterPoint.x, mCorePoints[1].mCenterPoint.y);
        mPath.cubicTo(mCorePoints[1].mSecondPoint.x, mCorePoints[1].mSecondPoint.y,
                mCorePoints[2].mFirstPoint.x, mCorePoints[2].mFirstPoint.y,
                mCorePoints[2].mCenterPoint.x, mCorePoints[2].mCenterPoint.y);
        mPath.cubicTo(mCorePoints[2].mSecondPoint.x, mCorePoints[2].mSecondPoint.y,
                mCorePoints[3].mSecondPoint.x, mCorePoints[3].mSecondPoint.y,
                mCorePoints[3].mCenterPoint.x, mCorePoints[3].mCenterPoint.y);
        mPath.cubicTo(mCorePoints[3].mFirstPoint.x, mCorePoints[3].mFirstPoint.y,
                mCorePoints[0].mSecondPoint.x, mCorePoints[0].mSecondPoint.y,
                mCorePoints[0].mCenterPoint.x, mCorePoints[0].mCenterPoint.y);
        return mPath;
    }

//    public float[] regeneratePoints() {
//        float[] points = new float[24];
//        int count = 0;
//        for (BezierPoint point : mCorePoints) {
//            points[count * 6 + 0] = point.mFirstPoint.x;
//            points[count * 6 + 1] = point.mFirstPoint.y;
//            points[count * 6 + 2] = point.mCenterPoint.x;
//            points[count * 6 + 3] = point.mCenterPoint.y;
//            points[count * 6 + 4] = point.mSecondPoint.x;
//            points[count * 6 + 5] = point.mSecondPoint.y;
//            for (int index = 0; index < 6; ++index) {
//                System.out.println("X: " + points[count*4 + index] + "        Y:" + points[count*4 + (++index)]);
//            }
//            System.out.println(count);
//            count++;
//        }
//        for (float point : points) {
//            System.out.println(point);
//        }
//        return points;
//    }

}
