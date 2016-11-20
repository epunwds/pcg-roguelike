package com.pcg.roguelike.world.generator;

import com.badlogic.gdx.graphics.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by BugDeveloper on 20.11.2016.
 */

public class TextureGenerator {

    public TextureGenerator(int width, int height, Random random) {
        this.random = random;
        this.width = width;
        this.height = height;
    }

    private Random random;
    private int width, height;

    private float[][] generateWhiteNoise(int width, int height)
    {
        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                noise[i][j] = (float)random.nextDouble() % 1;
            }
        }
        return noise;
    }

    private float interpolate(float x0, float x1, float alpha)
    {
        return x0 * (1 - alpha) + alpha * x1;
    }

    private float[][] generateSmoothNoise(float[][] baseNoise, int octave)
    {

        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave;
        float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < width; i++)
        {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
            float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++)
            {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
                float vertical_blend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                float top = interpolate(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                //blend the bottom two corners
                float bottom = interpolate(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                //final blend
                smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);

            }
        }

        return smoothNoise;
    }

    public Texture generateTexture(Color gradientStart, Color gradientEnd, int octaveCount) {


        float[][] perlinNoise = generatePerlinNoise(octaveCount);

        Color[][] perlinImage = mapGradient(gradientStart, gradientEnd, perlinNoise);

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                pixmap.setColor(GetColor(gradientStart, gradientEnd, perlinNoise[i][j]));
                pixmap.drawPixel(i, j);
            }
        }
        pixmap.setColor(Color.CYAN);

        return new Texture(pixmap);
    }

    private float[][] generatePerlinNoise(int octaveCount)
    {
        float[][] baseNoise = generateWhiteNoise(width, height);

        float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing

        float persistance = 0.5f;

        //generate smooth noise
        for (int i = 0; i < octaveCount; i++)
        {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--)
        {

            amplitude *= persistance;
            totalAmplitude += amplitude;

            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                perlinNoise[i][j] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    public Color GetColor(Color gradientStart, Color gradientEnd, float t)
    {
        float u = 1 - t;

        Color color = new Color(
                (gradientStart.r * u + gradientEnd.r * t),
                (gradientStart.g * u + gradientEnd.g * t),
                (gradientStart.b * u + gradientEnd.b * t),
                1.0f);
        return color;
    }


    private Color[][] mapGradient(Color gradientStart, Color gradientEnd, float[][] perlinNoise)
    {


        Color[][] image = getColorArray(width, height); //an array of colours

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                image[i][j] = GetColor(gradientStart, gradientEnd, perlinNoise[i][j]);
            }
        }

        return image;
    }

    public Color[][] getColorArray(int width, int height) {

        Color[][] arr = new Color[width][height];

        return arr;
    }

}
