package com.example.firstgame.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class PullLeftAnimation extends Animation {
    private ImageView center_view, left_view;
    private Transformation transformation;

    public PullLeftAnimation(ImageView center_view, ImageView left_view) {
        this.center_view = center_view;
        this.left_view = left_view;
        transformation = new Transformation();
    }

}
