package com.pcg.roguelike.world.generator;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;
import java.util.Random;

/**
 * Created by BugDeveloper on 20.11.2016.
 */

public class TextureGenerator {

    private Random random;
    private int width, height;
    private float baseNoise[][];

    public TextureGenerator(int width, int height, Random random) {
        this.random = random;
        this.width = width;
        this.height = height;
    }

    private void generateWhiteNoise() {
        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = (float) random.nextDouble() % 1;
            }
        }
        baseNoise = noise;
    }

    public Texture generateTurbulenceTexture(Color gradientStart, Color gradientEnd, int turbSize) {
        generateWhiteNoise();

        float[][] blueprint = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float value = turbulence(i, j, turbSize);
                blueprint[i][j] = value;
            }
        }
        return getTexture(gradientStart, gradientEnd, blueprint);
    }

    private float turbulence(double x, double y, float size) {
        float value = 0.0f, initialSize = size;

        while (size >= 1) {
            value += smoothNoise(x / size, y / size) * size;
            size /= 2.0;
        }

        return (value / initialSize);
    }

    public Texture generateWoodTexture(Color gradientStart, Color gradientEnd, float xyPeriod, float turbPower, float turbSize) {
        //number of rings float xyPeriod;
        //makes twists float turbPower;
        //initial size of the turbulence float turbSize;

        float[][] blueprint = new float[width][height];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                float xValue = (x - width / 2) / (float) width;
                float yValue = (y - height / 2) / (float) height;
                float distValue = (float) Math.sqrt(xValue * xValue + yValue * yValue) + turbPower * turbulence(x, y, turbSize);
                float sineValue = (float) Math.abs(Math.sin(2 * xyPeriod * distValue * Math.PI));
                blueprint[x][y] = sineValue;
            }

        return getTexture(gradientStart, gradientEnd, blueprint);
    }

    public Texture generateMetalTexture(Color gradientStart, Color gradientEnd, float xyPeriod, float turbPower, float turbSize) {
        float[][] blueprint = new float[width][height];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                double xValue = (i - width / 2) / (float) width + turbPower * turbulence(i, j, turbSize);
                double yValue = (j - height / 2) / (float) height + turbPower * turbulence(height - j, width - i, turbSize);
                float sineValue = (float) Math.abs(Math.sin(xyPeriod * xValue * Math.PI) + Math.sin(xyPeriod * yValue * Math.PI));
                blueprint[i][j] = sineValue;
            }

        return getTexture(gradientStart, gradientEnd, blueprint);
    }

    public Texture generateSomethingStrangeTexture(float xyPeriod, float turbPower, float turbSize) {
        Color[][] blueprint = new Color[width][height];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                double xValue = (i - width / 2) / (float) width + turbPower * turbulence(i, j, turbSize);
                double yValue = (j - height / 2) / (float) height + turbPower * turbulence(height - j, width - i, turbSize);
                float sineValue = (float) Math.abs(Math.sin(xyPeriod * xValue * Math.PI) + Math.sin(xyPeriod * yValue * Math.PI));
                blueprint[i][j] = new Color(java.awt.Color.HSBtoRGB(sineValue, 1, 1));
            }

        return getTexture(blueprint);
    }

    private Texture getTexture(Color[][] blueprint) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                pixmap.setColor(blueprint[i][j]);
                pixmap.drawPixel(i, j);
            }

        return new Texture(pixmap);
    }

    private Float smoothNoise(Double x, Double y) {
        //get fractional part of x and y
        double fractX = x - x.intValue();
        double fractY = y - y.intValue();

        //wrap around
        int x1 = (x.intValue() + width) % width;
        int y1 = (y.intValue() + height) % height;

        //neighbor values
        int x2 = (x1 + width - 1) % width;
        int y2 = (y1 + height - 1) % height;

        //smooth the noise with bilinear interpolation
        float value = 0.0f;
        value += fractX * fractY * baseNoise[y1][x1];
        value += (1 - fractX) * fractY * baseNoise[y1][x2];
        value += fractX * (1 - fractY) * baseNoise[y2][x1];
        value += (1 - fractX) * (1 - fractY) * baseNoise[y2][x2];
        return value;
    }

    private Texture getTexture(Color gradientStart, Color gradientEnd, float[][] blueprint) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = getColor(gradientStart, gradientEnd, blueprint[i][j]);
                pixmap.setColor(color);
                pixmap.drawPixel(i, j);
            }
        }
        return new Texture(pixmap);
    }

    public Texture generatePerlinTexture(Color gradientStart, Color gradientEnd, int octaveCount, double diviser) {

        generateWhiteNoise();

        float[][] perlinNoise = generatePerlinNoise(octaveCount, diviser);

        return getTexture(gradientStart, gradientEnd, perlinNoise);
    }

    public Texture generateMarbgeTexture(Color gradientStart, Color gradientEnd, float turbPower, float turbSize, float xPeriod, float yPeriod) {

        generateWhiteNoise();

        float[][] blueprint = new float[width][height];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                double xyValue = i * xPeriod / width + j * yPeriod / height + turbPower * turbulence(i, j, turbSize);
                float sineValue = (float) Math.abs(Math.sin(xyValue * Math.PI));
                blueprint[i][j] = sineValue;
            }

        return getTexture(gradientStart, gradientEnd, blueprint);
    }

    private float[][] generatePerlinNoise(int octaveCount, double diviser) {
        float[][][] smoothNoise = new float[octaveCount][width][height]; //an array of 2D arrays containing

        float persistance = 0.5f;

        //generate smooth noise
        for (int i = 0; i < octaveCount; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    smoothNoise[i][j][k] = smoothNoise(j / diviser, k / diviser);
                }
            }
            diviser *= octaveCount;
        }

        float[][] perlinNoise = new float[width][height];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    public Color getColor(Color gradientStart, Color gradientEnd, float t) {
        float u = 1 - t;

        Color color = new Color(
                (gradientStart.r * u + gradientEnd.r * t),
                (gradientStart.g * u + gradientEnd.g * t),
                (gradientStart.b * u + gradientEnd.b * t),
                1.0f);
        return color;
    }

    private Color[][] mapGradient(Color gradientStart, Color gradientEnd, float[][] perlinNoise) {

        Color[][] image = new Color[width][height]; //an array of colours

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image[i][j] = getColor(gradientStart, gradientEnd, perlinNoise[i][j]);
            }
        }

        return image;
    }

}
