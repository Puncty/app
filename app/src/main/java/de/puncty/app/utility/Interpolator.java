package de.puncty.app.utility;

public class Interpolator {
    public static int[] interpolate(int startValue, int endValue, int steps) {
        int[] nums = new int[steps];
        int stepSize = (endValue - startValue) / steps;

        for (int i = 0; i < steps; i++) {
            nums[i] = startValue + stepSize * i;
        }

        return nums;
    }
}
