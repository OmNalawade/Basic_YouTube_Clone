package com.example.youtubeclone.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class ResizableVideoView extends VideoView {

    private int videoWidth;
    private int videoHeight;

    public ResizableVideoView(Context context) {
        super(context);
    }

    public ResizableVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Save real video size
    public void setVideoSize(int width, int height) {
        this.videoWidth = width;
        this.videoHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (videoWidth == 0 || videoHeight == 0) {
            setMeasuredDimension(parentWidth, parentHeight);
            return;
        }

        float videoRatio = (float) videoWidth / videoHeight;
        float parentRatio = (float) parentWidth / parentHeight;

        int width;
        int height;


        if (parentRatio > videoRatio) {
            width = parentWidth;
            height = (int) (parentWidth / videoRatio);
        } else {
            height = parentHeight;
            width = (int) (parentHeight * videoRatio);
        }

        setMeasuredDimension(width, height);
    }
}

