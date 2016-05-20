package com.blogspot.smallshipsinvest.coolcard.helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class XY {

    public double x;
    public double y;

//    constructors

    public XY() {
        x = 0.5d;
        y = 0.5d;
    }

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }


//    patterns

    public static XY[] allInCenterPattern(int num) {
        XY[] pattern = new XY[num];
        for (int i = 0; i < num; i++) {
            pattern[i] = new XY();
        }
        return pattern;
    }

    public static XY[] randomPattern(int num) {
        XY[] pattern = new XY[num];
        for (int i = 0; i < num; i++) {
            pattern[i] = new XY();
            pattern[i].setRandom();
        }
        return pattern;
    }

    public static XY[] oneStartPattern(XY start, int num) {
        XY[] pattern = new XY[num];
        for (int i = 0; i < num; i++) {
            pattern[i] = start;
        }
        return pattern;
    }

    public static XY[] borderRandomPattern(int num, double screenRatio, double objectSize) {
        XY[] pattern = new XY[num];
        for (int i = 0; i < num; i++) {
            pattern[i] = new XY();
            pattern[i].setRandomOnBorder(screenRatio, objectSize);
        }
        return pattern;
    }

    public static XY[] outEllipseRandomPattern(int num, double width, double height, double screenRatio) {
        XY[] pattern = new XY[num];
        for (int i = 0; i < num; i++) {
            pattern[i] = new XY();
            pattern[i].setRandomOutEllipse(width, height, screenRatio);
        }
        return pattern;
    }

    public static XY[] outCircleRandomPattern(int num, double diameter, double screenRatio) {
        return outEllipseRandomPattern(num, diameter, diameter, screenRatio);
    }

    public static XY[] chessPattern(int xNum, double screenRatio) {

        ArrayList<XY> pattern = new ArrayList<>();

        int ySize = Math.round((float) (screenRatio * xNum));

        for (int j = 1; j <= ySize; j++) {
            for (int i = 1; i <= xNum; i++) {

                if (isOdd(i) && isOdd(j)) {
                    pattern.add(new XY(i / (xNum + 1d), j / (ySize + 1d)));
                }

                if (isEven(i) && isEven(j)) {
                    pattern.add(new XY(i / (xNum + 1d), j / (ySize + 1d)));
                }

            }
        }

        return shuffle(pattern.toArray(new XY[pattern.size()]));

    }

    public static XY[] outEllipseChessPattern(int xNum, double width, double height, double screenRatio) {

        ArrayList<XY> pattern = new ArrayList<>();

        int ySize = Math.round((float) (screenRatio * xNum));

        for (int j = 1; j <= ySize; j++) {
            for (int i = 1; i <= xNum; i++) {

                if (isOdd(i) && isOdd(j)) {
                    XY r = new XY(i / (xNum + 1d), j / (ySize + 1d));
                    if (r.isOutEllipse(width, height / screenRatio)) {
                        pattern.add(r);
                    }
                }

                if (isEven(i) && isEven(j)) {
                    XY r = new XY(i / (xNum + 1d), j / (ySize + 1d));
                    if (r.isOutEllipse(width, height / screenRatio)) {
                        pattern.add(r);
                    }
                }

            }
        }

        return shuffle(pattern.toArray(new XY[pattern.size()]));

    }

    public static XY[] outCircleChessPattern(int xNum, double diameter, double screenRatio) {
        return outEllipseChessPattern(xNum, diameter, diameter, screenRatio);
    }

    public static XY[] fireworkPattern(XY start, int num, double radius, double screenRatio) {
        XY[] pattern = new XY[num];
        double[] angles = fireworkDirections(num);
        for (int i = 0; i < num; i++) {
            pattern[i] = new XY();
            pattern[i].setOnCycle(start, radius, angles[i], screenRatio);
        }
        return pattern;
    }


//    points

    public void setRandom() {
        x = Math.random();
        y = Math.random();
    }

    public void setRandomOnBorder(double screenRatio, double objectSize) {
        if (Math.random() < screenRatio / (1 + screenRatio)) {
            y = Math.random();
            if (Math.random() < 0.5d) {
                x = -objectSize/2;
            } else {
                x = 1 + objectSize/2;
            }
        } else {
            x = Math.random();
            if (Math.random() < 0.5d) {
                y = -objectSize/2;
            } else {
                y = 1 + objectSize/2;
            }
        }
    }

    public void setOnCycle(XY center, double radius, double angle, double screenRatio) {
        x = center.x + radius * Math.sin(angle);
        y = center.y - radius * Math.cos(angle) / screenRatio;
    }

    public void setRandomOutEllipse(double width, double height, double screenRatio) {
        while (true) {
            x = Math.random();
            y = Math.random();
            if (isOutEllipse(width, height / screenRatio)) {
                break;
            }
        }
    }


//    transformations

    public static XY[] shiftRandomly(XY[] pattern, double shift, double screenRatio) {
        for (XY p : pattern) {
            p.shiftRandomly(shift, screenRatio);
        }
        return pattern;
    }

    public static XY[] shuffle(XY[] pattern) {

        ArrayList<XY> list = new ArrayList<>(Arrays.asList(pattern));
        ArrayList<XY> answer = new ArrayList<>();

        while (list.size() > 0) {
            int i = (int) (Math.random() * list.size());
            answer.add(list.get(i));
            list.remove(i);
        }

        return answer.toArray(new XY[answer.size()]);

    }

    public void shiftRandomly(double shift, double screenRatio) {
        double direction = 2 * Math.PI * Math.random();
        x += shift * Math.cos(direction);
        y += shift * Math.sin(direction) / screenRatio;
    }


//    math helpers

    public boolean isOutEllipse(double width, double height) {
        return Math.pow((x - 0.5d) / (width / 2), 2) + Math.pow((y - 0.5d) / (height / 2), 2)
                > 1;
    }

    public static boolean isEven(int i) {
        return (i & 1) == 0;
    }

    public static boolean isOdd(int i) {
        return (i & 1) == 1;
    }

    public static double[] fireworkDirections(int num) {
        double[] answer = new double[num];
        answer[0] = Math.random() * 2 * Math.PI / num;
        for (int i = 1; i < num; i++) {
            answer[i] = answer[i - 1] + 2 * Math.PI / num;
        }
        return answer;
    }

}
