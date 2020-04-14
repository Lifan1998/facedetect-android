package com.example.facedetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.facedetection.util.ImageUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.facedetection", appContext.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeFile("/Users/apple/Downloads/zhoujielun.jpg");
        System.out.println(bitmap.getByteCount());
        System.out.println(ImageUtils.bitmapToBase64(bitmap));
    }
}
