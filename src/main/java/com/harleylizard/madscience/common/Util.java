package com.harleylizard.madscience.common;

public final class Util {

    public static float blend(float start, float end, float amount) {
        return start + ((end - start) * amount);
    }

    public static int blend(int start, int end, float amount) {
        var startR = (float) ((start >> 16) & 0xFF) / 255.0f;
        var startG = (float) ((start >> 8) & 0xFF) / 255.0f;
        var startB = (float) (start & 0xFF) / 255.0f;

        var endR = (float) ((end >> 16) & 0xFF) / 255.0f;
        var endG = (float) ((end >> 8) & 0xFF) / 255.0f;
        var endB = (float) (end & 0xFF) / 255.0f;

        var r = blend(startR, endR, amount) * 255.0f;
        var g = blend(startG, endG, amount) * 255.0f;
        var b = blend(startB, endB, amount) * 255.0f;

        return (((int) r & 0xFF) << 16) | (((int) g & 0xFF) << 8) | ((int) b & 0xFF);
    }
}
