package com.example.facedetection;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        File file = new File("/Users/apple/Downloads/切图/发现/头像.png");
        System.out.println(file.length());
    }
}