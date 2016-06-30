package com.example.svilupposw.ToEat;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by root on 30/06/16.
 */

public class ColorGenerator {

    public static String[] colors = {
            "#ffff296a",
            "#ffd99ff6",
            "#ff95ca88",
            "#FFFFBB33",
            "#ffed494c",
            "#FF0099CC",
            "#ffd787ff",
            "#ff00baac",
            "#ffed8f2e",
            "#ffbfd47f"
    };

    public static int getColor() {

        Random random = new Random();
        String color = ColorGenerator.colors[random.nextInt(9 + 1)];

        return Color.parseColor(color);


    };

}
