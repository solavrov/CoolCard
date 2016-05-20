package com.blogspot.smallshipsinvest.coolcard.helpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Studio {

    public static class Firework extends Scene {

        private float[] inflatorPath = {1, 1};

        public Firework(RelativeLayout screen, XY start, XY[] targetPattern) {
            super(screen, XY.oneStartPattern(start, targetPattern.length));
            super.setTarget(targetPattern);
            super.setAlpha(0f);
        }

        public void setInflatorPath(float[] inflatorPath) {
            this.inflatorPath = inflatorPath;
        }

        public void start() {
            for (Actor s : super.cast) {
                s.flyLoop(true, inflatorPath);
            }
        }

    }

    public static class Sky extends Scene {

        public Sky(RelativeLayout screen, XY[] pattern) {
            super(screen, pattern);
        }

        public void flashSize() {
            for (Actor s : super.cast) {
                s.flashSize();
            }
        }

        public void inflate() {
            for (Actor s : super.cast) {
                s.inflate();
            }
        }

    }

    public static class Scene {

        private Actor[] cast;

        public Scene(RelativeLayout screen, XY[] pattern) {
            cast = new Actor[pattern.length];
            for (int i = 0; i < pattern.length; i++) {
                cast[i] = new Actor(screen, pattern[i]);
            }
        }

        public void setDuration(long duration) {
            for (Actor s : cast) {
                s.setDuration(duration);
            }
        }

        public void setDuration2(long duration2) {
            for (Actor s : cast) {
                s.setDuration2(duration2);
            }
        }

        public void setDelay(long minDelay, long maxDelay, boolean isDifferent) {
            if (isDifferent) {
                for (Actor s : cast) {
                    s.setDelay(minDelay, maxDelay);
                }
            } else {
                long delay = (long) (Math.random() * (maxDelay - minDelay) + minDelay);
                for (Actor s : cast) {
                    s.setDelay(delay);
                }
            }
        }

        public void setDelay(long delay) {
            for (Actor s : cast) {
                s.setDelay(delay);
            }
        }

        public void setInflator(float inflator) {
            for (Actor s : cast) {
                s.setInflator(inflator);
            }
        }

        public void setTarget(XY[] pattern) {
            int i = 0;
            for (Actor s : cast) {
                s.setTarget(pattern[i++]);
            }
        }

        public void setImage(int imageID) {
            for (Actor s : cast) {
                s.setImage(imageID);
            }
        }

        public void setImage(int[] imageIDs) {
            for (Actor s : cast) {
                s.setImage(imageIDs);
            }
        }

        public void setAlpha(float alpha) {
            for (Actor s : cast) {
                s.setAlpha(alpha);
            }
        }

        public void setImageInOrder(int[] imageIDs) {
            int i = 0;
            for (Actor s : cast) {
                s.setImage(imageIDs[i++]);
                if (i == imageIDs.length) i = 0;
            }
        }

        public void setSize(float relativeSize) {
            for (Actor s : cast) {
                s.setSize(relativeSize);
            }
        }

        public void end() {
            for (Actor s : cast) {
                s.end();
            }
        }

    }

    public static class Actor {

        private boolean isAnimating = false;

        private long duration = 1_000;
        private long duration2 = 0;
        private long delay = 0;
        private float inflator = 1;
        private XY target = new XY();

        public int sizeX;
        public int sizeY;
        public int centerX;
        public int centerY;
        public ImageView view;
        private RelativeLayout screen;

        //        constructors
        public Actor(RelativeLayout screen, XY point) {

            this.screen = screen;

            final float DEFAULT_RELATIVE_SIZE = 0.1f;

            DisplayMetrics dm = screen.getContext().getResources().getDisplayMetrics();
            sizeX = (int) (DEFAULT_RELATIVE_SIZE * dm.widthPixels);
            sizeY = (int) (DEFAULT_RELATIVE_SIZE * dm.widthPixels);

            centerX = (int) (point.x * dm.widthPixels);
            centerY = (int) (point.y * dm.heightPixels);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sizeX, sizeY);
            params.leftMargin = marginLeft();
            params.topMargin = marginTop();

            view = new ImageView(screen.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            view.setBackgroundColor(Color.TRANSPARENT);
            screen.addView(view, params);

        }

        //        sets
        public void setAlpha(float alpha) {
            view.setAlpha(alpha);
        }

        public void setSize(float relativeSize) {
            DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
            sizeX = (int) (relativeSize * dm.widthPixels);
            sizeY = (int) (relativeSize * dm.widthPixels);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sizeX, sizeY);
            params.leftMargin = marginLeft();
            params.topMargin = marginTop();
            view.setLayoutParams(params);
        }

        public void setSize(float relativeSizeX, float relativeSizeY) {
            DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
            sizeX = (int) (relativeSizeX * dm.widthPixels);
            sizeY = (int) (relativeSizeY * dm.widthPixels);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sizeX, sizeY);
            params.leftMargin = marginLeft();
            params.topMargin = marginTop();
            view.setLayoutParams(params);
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public void setDuration2(long duration2) {
            this.duration2 = duration2;
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public void setDelay(long minDelay, long maxDelay) {
            delay = (long) (Math.random() * (maxDelay - minDelay) + minDelay);
        }

        public void setInflator(float inflator) {
            this.inflator = inflator;
        }

        public void setTarget(XY target) {
            this.target = target;
        }

        public void setImage(int imageID) {
            view.setImageResource(imageID);
        }

        public void setImage(int[] imageIDs) {
            view.setImageResource(imageIDs[(int) (Math.random() * imageIDs.length)]);
        }

        //        animations
        public void flyLoop(boolean isDimOnBack, float[] inflatorPath) {

            isAnimating = true;

            DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

            int targetX = (int) (target.x * displayMetrics.widthPixels);
            int targetY = (int) (target.y * displayMetrics.heightPixels);

            ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "x", positionX(), positionX(targetX));
            ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "y", positionY(), positionY(targetY));
            ObjectAnimator moveXBack = ObjectAnimator.ofFloat(view, "x", positionX(targetX), positionX());
            ObjectAnimator moveYBack = ObjectAnimator.ofFloat(view, "y", positionY(targetY), positionY());

            ObjectAnimator inflateX = ObjectAnimator.ofFloat(view, "scaleX", inflatorPath);
            ObjectAnimator inflateY = ObjectAnimator.ofFloat(view, "scaleY", inflatorPath);
            ObjectAnimator deflateX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
            ObjectAnimator deflateY = ObjectAnimator.ofFloat(view, "scaleY", 1f);

            moveX.setDuration(duration);
            moveY.setDuration(duration);
            inflateX.setDuration(duration);
            inflateY.setDuration(duration);

            moveXBack.setDuration(duration2);
            moveYBack.setDuration(duration2);
            deflateX.setDuration(duration2);
            deflateY.setDuration(duration2);

            AnimatorSet set1 = new AnimatorSet();
            AnimatorSet set2 = new AnimatorSet();
            AnimatorSet set3 = new AnimatorSet();
            AnimatorSet set4 = new AnimatorSet();
            AnimatorSet set = new AnimatorSet();

            set1.play(moveX).with(moveY).with(inflateX).with(inflateY);
            set2.play(moveXBack).with(moveYBack).with(deflateX).with(deflateY);

            if (isDimOnBack) {

                ObjectAnimator dim = ObjectAnimator.ofFloat(view, "alpha", 0f);
                ObjectAnimator light = ObjectAnimator.ofFloat(view, "alpha", 1f);

                dim.setDuration(0);
                light.setDuration(0);

                light.setStartDelay(delay);

                set3.play(light).before(set1);
                set4.play(set3).before(dim);
                set.play(set4).before(set2);

            } else {

                set1.setStartDelay(delay);
                set.play(set1).before(set2);

            }

            set.setInterpolator(new LinearInterpolator());
            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        public void flyLoop(boolean isDimOnBack) {

            isAnimating = true;

            DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

            int targetX = (int) (target.x * displayMetrics.widthPixels);
            int targetY = (int) (target.y * displayMetrics.heightPixels);

            ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "x", positionX(), positionX(targetX));
            ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "y", positionY(), positionY(targetY));
            ObjectAnimator moveXBack = ObjectAnimator.ofFloat(view, "x", positionX(targetX), positionX());
            ObjectAnimator moveYBack = ObjectAnimator.ofFloat(view, "y", positionY(targetY), positionY());

            ObjectAnimator inflateX = ObjectAnimator.ofFloat(view, "scaleX", inflator);
            ObjectAnimator inflateY = ObjectAnimator.ofFloat(view, "scaleY", inflator);
            ObjectAnimator deflateX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
            ObjectAnimator deflateY = ObjectAnimator.ofFloat(view, "scaleY", 1f);

            moveX.setDuration(duration);
            moveY.setDuration(duration);
            inflateX.setDuration(duration);
            inflateY.setDuration(duration);

            moveXBack.setDuration(duration2);
            moveYBack.setDuration(duration2);
            deflateX.setDuration(duration2);
            deflateY.setDuration(duration2);

            AnimatorSet set1 = new AnimatorSet();
            AnimatorSet set2 = new AnimatorSet();
            AnimatorSet set3 = new AnimatorSet();
            AnimatorSet set4 = new AnimatorSet();
            AnimatorSet set = new AnimatorSet();

            if (inflator == 1) {
                set1.play(moveX).with(moveY);
                set2.play(moveXBack).with(moveYBack);
            } else {
                set1.play(moveX).with(moveY).with(inflateX).with(inflateY);
                set2.play(moveXBack).with(moveYBack).with(deflateX).with(deflateY);
            }

            if (isDimOnBack) {

                ObjectAnimator dim = ObjectAnimator.ofFloat(view, "alpha", 0f);
                ObjectAnimator light = ObjectAnimator.ofFloat(view, "alpha", 1f);

                dim.setDuration(0);
                light.setDuration(0);

                light.setStartDelay(delay);

                set3.play(light).before(set1);
                set4.play(set3).before(dim);
                set.play(set4).before(set2);

            } else {

                set1.setStartDelay(delay);
                set.play(set1).before(set2);

            }

            set.setInterpolator(new LinearInterpolator());
            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        public void fly() {

            DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();

            int targetX = (int) (target.x * displayMetrics.widthPixels);
            int targetY = (int) (target.y * displayMetrics.heightPixels);

            ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "x", positionX(), positionX(targetX));
            ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "y", positionY(), positionY(targetY));

            moveX.setDuration(duration);
            moveY.setDuration(duration);

            moveX.setStartDelay(delay);

            AnimatorSet set = new AnimatorSet();

            set.setInterpolator(new LinearInterpolator());
            set.play(moveX).with(moveY);

            set.start();

        }

        public void flashSize() {

            isAnimating = true;

            ObjectAnimator inflateX = ObjectAnimator.ofFloat(view, "scaleX", inflator);
            ObjectAnimator inflateY = ObjectAnimator.ofFloat(view, "scaleY", inflator);

            ObjectAnimator deflateX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
            ObjectAnimator deflateY = ObjectAnimator.ofFloat(view, "scaleY", 1f);

            inflateX.setDuration(duration);
            inflateY.setDuration(duration);

            inflateX.setStartDelay(delay);

            deflateX.setDuration(duration);
            deflateY.setDuration(duration);

            AnimatorSet lightSet = new AnimatorSet();
            lightSet.play(inflateX).with(inflateY);

            AnimatorSet dimSet = new AnimatorSet();
            dimSet.play(deflateX).with(deflateY);

            AnimatorSet set = new AnimatorSet();

            set.play(lightSet).before(dimSet);

            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        public void flashAlpha() {

            isAnimating = true;

            ObjectAnimator light = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            ObjectAnimator dim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

            light.setDuration(duration);

            light.setStartDelay(delay);

            dim.setDuration(duration);

            AnimatorSet set = new AnimatorSet();
            set.play(light).before(dim);

            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        public void spin(boolean isClockWise) {

            isAnimating = true;

            float angle;

            if (isClockWise) {
                angle = 360f;
            } else {
                angle = -360f;
            }

            ObjectAnimator spin = ObjectAnimator.ofFloat(view, "rotation", 0f, angle);
            ObjectAnimator spinBack = ObjectAnimator.ofFloat(view, "rotation", 0f, -angle);

            spin.setDuration(duration);
            spinBack.setDuration(0);

            spin.setStartDelay(delay);

            AnimatorSet set = new AnimatorSet();

            set.play(spin).before(spinBack);

            set.setInterpolator(new LinearInterpolator());
            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        public void inflate() {

            ObjectAnimator inflateX = ObjectAnimator.ofFloat(view, "scaleX", inflator);
            ObjectAnimator inflateY = ObjectAnimator.ofFloat(view, "scaleY", inflator);

            inflateX.setDuration(duration);
            inflateY.setDuration(duration);

            inflateX.setStartDelay(delay);

            AnimatorSet set = new AnimatorSet();
            set.play(inflateX).with(inflateY);

            set.setInterpolator(new LinearInterpolator());
            set.start();

        }

        public void swing(float angle) {

            isAnimating = true;

            ObjectAnimator swing1 = ObjectAnimator.ofFloat(view, "rotation", 0f, angle / 2);
            ObjectAnimator swingBack = ObjectAnimator.ofFloat(view, "rotation", angle / 2, -angle / 2);
            ObjectAnimator swing2 = ObjectAnimator.ofFloat(view, "rotation", -angle / 2, 0f);

            swing1.setDuration(duration / 2);
            swingBack.setDuration(duration);
            swing2.setDuration(duration / 2);

            swing1.setStartDelay(delay);

            AnimatorSet set1 = new AnimatorSet();
            AnimatorSet set = new AnimatorSet();

            set1.play(swing1).before(swingBack);
            set.play(set1).before(swing2);

            set.setInterpolator(new LinearInterpolator());
            set.start();

            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isAnimating) {
                        animation.start();
                    } else {
                        super.onAnimationEnd(animation);
                    }
                }
            });

        }

        //        managing
        public void end() {
            isAnimating = false;
        }

        //        supportive
        public int marginLeft() {
            return (int) (centerX - sizeX / 2f - screen.getPaddingLeft());
        }

        public int marginTop() {
            return (int) (centerY - sizeY / 2f - screen.getPaddingTop());
        }

        public int positionX() {
            return (int) (centerX - sizeX / 2f);
        }

        public int positionY() {
            return (int) (centerY - sizeY / 2f);
        }

        public int positionX(int centerX) {
            return (int) (centerX - sizeX / 2f);
        }

        public int positionY(int centerY) {
            return (int) (centerY - sizeY / 2f);
        }

    }

    public static class Script {

        //    fonts
        public static final String NORMAL_FONT = "normal";
        public static final String BOLD_FONT = "bold";
        public static final String ITALIC_FONT = "italic";
        public static final String BOLD_ITALIC_FONT = "bold_italic";

        //        public static final String ROBOTO_REGULAR_FONT = "Roboto-Regular.ttf";
//        public static final String ROBOTO_BOLD_FONT = "Roboto-Bold.ttf";
//        public static final String ROBOTO_ITALIC_FONT = "Roboto-Italic.ttf";
        public static final String ROBOTO_BOLD_ITALIC_FONT = "Roboto-BoldItalic.ttf";


        //        attributes
        private float inflator = 1f;
        private long duration = 1_000;
        private long delay = 0;
        public TextView view;

        public Script(RelativeLayout screen) {
            view = new TextView(screen.getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setGravity(Gravity.CENTER);
            screen.addView(view, params);
        }

        public Script(RelativeLayout screen, float relativeTopMargin, float relativeBottomMargin) {

            DisplayMetrics dm = screen.getContext().getResources().getDisplayMetrics();

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, (int) (relativeTopMargin * dm.heightPixels), 0, (int)
                    (relativeBottomMargin * dm.heightPixels));

            view = new TextView(screen.getContext());
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setGravity(Gravity.CENTER);
            screen.addView(view, params);

        }

        public void setText(String text) {
            view.setText(text);
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public void setFont(String font) {
            switch (font) {
                case NORMAL_FONT:
                    view.setTypeface(null, Typeface.NORMAL);
                    break;
                case BOLD_FONT:
                    view.setTypeface(null, Typeface.BOLD);
                    break;
                case ITALIC_FONT:
                    view.setTypeface(null, Typeface.ITALIC);
                    break;
                case BOLD_ITALIC_FONT:
                    view.setTypeface(null, Typeface.BOLD_ITALIC);
                    break;
                case ROBOTO_BOLD_ITALIC_FONT:
                    view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),
                            ROBOTO_BOLD_ITALIC_FONT));
                    break;
//                case  ROBOTO_REGULAR_FONT:
//                    view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),
//                            ROBOTO_REGULAR_FONT));
//                    break;
//                case ROBOTO_BOLD_FONT:
//                    view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),
//                            ROBOTO_BOLD_FONT));
//                    break;
//                case ROBOTO_ITALIC_FONT:
//                    view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),
//                            ROBOTO_ITALIC_FONT));
//                    break;
            }
        }

        public void setTextSize(float relativeSize) {
            DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, dm.widthPixels * relativeSize);
        }

        public void setLineSpacing(float multiplier) {
            view.setLineSpacing(0, multiplier);
        }

        public void setTextColor(int colorID, float alpha) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), colorID));
            view.setTextColor(view.getTextColors().withAlpha((int) (255 * alpha)));
        }

        public void setInflator(float inflator) {
            this.inflator = inflator;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public void inflate() {

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    view.setTextColor(view.getTextColors().withAlpha(255));
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, delay);

            ObjectAnimator deflateX = ObjectAnimator.ofFloat(view, "scaleX", 1 / inflator);
            ObjectAnimator deflateY = ObjectAnimator.ofFloat(view, "scaleY", 1 / inflator);
            deflateX.setDuration(0);
            deflateY.setDuration(0);

            ObjectAnimator inflateX = ObjectAnimator.ofFloat(view, "scaleX", 1);
            ObjectAnimator inflateY = ObjectAnimator.ofFloat(view, "scaleY", 1);

            inflateX.setDuration(duration);
            inflateY.setDuration(duration);

            inflateX.setStartDelay(delay);

            AnimatorSet set1 = new AnimatorSet();
            set1.play(deflateX).with(deflateY);

            AnimatorSet set2 = new AnimatorSet();
            set2.play(inflateX).with(inflateY);

            AnimatorSet set = new AnimatorSet();
            set.playSequentially(set1, set2);

            set.setInterpolator(new LinearInterpolator());

            set.start();

        }

    }

    public static void animateBackground(View view, int colorStartID, int colorFinishID, long duration) {

        int colorStart = ContextCompat.getColor(view.getContext(), colorStartID);
        int colorFinish = ContextCompat.getColor(view.getContext(), colorFinishID);

        ObjectAnimator changeColor = ObjectAnimator.ofObject(view, "backgroundColor", new ArgbEvaluator(),
                colorStart, colorFinish);

        changeColor.setDuration(duration);

        changeColor.start();

    }

}
