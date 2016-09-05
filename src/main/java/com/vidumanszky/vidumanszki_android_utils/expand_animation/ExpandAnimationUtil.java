package com.vidumanszky.vidumanszki_android_utils.expand_animation;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by VBalazs on 2016-09-05.
 */
public class ExpandAnimationUtil implements Parcelable {

    public static final String KEY_EXPAND_ANIMATION_UTIL = "com.vidumanszky.util.key_expand_animation_util";

    private int top;
    private int left;
    private int width;
    private int height;

    public ExpandAnimationUtil(int left, int top, int width, int height) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
    }

    protected ExpandAnimationUtil(Parcel in) {
        top = in.readInt();
        left = in.readInt();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<ExpandAnimationUtil> CREATOR = new Creator<ExpandAnimationUtil>() {
        @Override
        public ExpandAnimationUtil createFromParcel(Parcel in) {
            return new ExpandAnimationUtil(in);
        }

        @Override
        public ExpandAnimationUtil[] newArray(int size) {
            return new ExpandAnimationUtil[size];
        }
    };

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(top);
        dest.writeInt(left);
        dest.writeInt(width);
        dest.writeInt(height);
    }

    public static ExpandAnimationUtil makeExpandAnimationUtil(View v) {
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);

        ExpandAnimationUtil expandAnimationUtil = new ExpandAnimationUtil(
                screenLocation[0],
                screenLocation[1],
                v.getWidth(),
                v.getHeight()
        );

        return expandAnimationUtil;
    }

}
