package com.vidumanszky.vidumanszki_android_utils.expand_animation;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by VBalazs on 2016-09-05.
 */
public abstract class ExpandAnimationActivity extends AppCompatActivity{

    private static final long ANIMATION_DURATION = 250;

    private ExpandAnimationUtil animationUtil;

    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();

    private int leftDelta;
    private int topDelta;
    private float scaleX;
    private float scaleY;

    public static void startActivity(Activity sourceActivity, ExpandAnimationUtil expandAnimationUtil) {
        Intent i = new Intent(sourceActivity, ExpandAnimationActivity.class);
        i.putExtra(ExpandAnimationUtil.KEY_EXPAND_ANIMATION_UTIL, expandAnimationUtil);

        sourceActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: handle this: initFadeView, initMainView
        getFadeView();
        getMainView();

        animateStart();
    }

    protected abstract View getFadeView();
    protected abstract View getMainView();

    private void animateStart() {
        animationUtil = getIntent().getParcelableExtra(ExpandAnimationUtil.KEY_EXPAND_ANIMATION_UTIL);

        if (animationUtil != null) {
            overridePendingTransition(0, 0);
            getMainView().setAlpha(0);

            getFadeView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    getFadeView().getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    getFadeView().getLocationOnScreen(screenLocation);

                    leftDelta = animationUtil.getLeft() - screenLocation[0];
                    topDelta = animationUtil.getTop() - screenLocation[1];

                    scaleX = (float) animationUtil.getWidth() / getFadeView().getWidth();
                    scaleY = (float) animationUtil.getHeight() / getFadeView().getHeight();

                    getFadeView().setPivotX(0);
                    getFadeView().setPivotY(0);
                    getFadeView().setScaleX(scaleX);
                    getFadeView().setScaleY(scaleY);
                    getFadeView().setTranslationX(leftDelta);
                    getFadeView().setTranslationY(topDelta);

                    getFadeView().animate().setDuration(ANIMATION_DURATION)
                            .scaleX(1).scaleY(1)
                            .translationX(0).translationY(0)
                            .setInterpolator(decelerateInterpolator)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    //nothing here
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    getMainView().animate().setDuration((long) ((double) ANIMATION_DURATION / 2))
                                            .alpha(1)
                                            .setInterpolator(decelerateInterpolator);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    //nothing here
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                    //nothing here
                                }
                            });

                    return true;
                }
            });
        }
    }

    @Override
    public void finish() {
        if (animationUtil != null) {
            getMainView().animate().setDuration((long) ((double ) ANIMATION_DURATION / 2))
                    .alpha(0)
                    .setInterpolator(decelerateInterpolator)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            //nothing here
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            getFadeView().animate().setDuration(ANIMATION_DURATION)
                                    .scaleX(scaleX).scaleY(scaleY)
                                    .translationX(leftDelta).translationY(topDelta)
                                    .setInterpolator(decelerateInterpolator)
                                    .setListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {
                                            //nothing here
                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            ExpandAnimationActivity.super.finish();
                                            overridePendingTransition(0, 0);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {
                                            //nothing here
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {
                                            //nothing here
                                        }
                                    });
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            //nothing here
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            //nothing here
                        }
                    });
        } else {
            super.finish();
            overridePendingTransition(0, 0);
        }
    }

}
