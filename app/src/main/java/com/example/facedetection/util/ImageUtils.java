package com.example.facedetection.util;




import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.io.*;
import java.util.List;

import static android.graphics.BitmapFactory.decodeStream;

/**
 * @author fan.li
 * @date 2020-04-04
 * @description
 */

public class ImageUtils {


    private static final String TAG = "ImageUtils";

    public static String encodeFileToBase64Binary(File file){
        String encodedfile = null;
        String filename = file.getName();
        String extension = "";
        // check image's extension

        if (filename.contains("png")) {
            extension = "data:image/jpeg;base64";
        } else if (filename.contains("jpeg")) {
            extension = "data:image/jpeg;base64";
        } else if (filename.contains("jpg")) {
            extension = "data:image/jpg;base64";
        }
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encode(bytes, Base64.DEFAULT));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return extension + "," + encodedfile;
    }
//
//    public static File handleFileSize(File file, int maxSize)  {
//        while (file.length() > maxSize) {
//            System.out.println(file.length());
//
//
//            try {
//                List<File> fileList = Thumbnails.of(file).scale(0.9f).asFiles(Rename.NO_CHANGE);
//                System.out.println(fileList);
//                if (!fileList.isEmpty()) {
//                    file = fileList.get(0);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//    }

    /**
     *
     * @param bitmap
     * @param size 单位k
     * @return
     */
    public static Bitmap compress(Bitmap bitmap, int size) {

        ByteArrayOutputStream baos =  new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,  100 , baos);
        int  options =  100;
        while  ( baos.toByteArray().length/1024 > size ) {
            baos.reset();
            Log.v(TAG, "compress: 1 " + bitmap.getByteCount() + ": " + baos.toByteArray().length);

            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            Log.v(TAG, "compress: 2 " + bitmap.getByteCount() + ": " + baos.toByteArray().length);
            options -= 5;
        }
        ByteArrayInputStream isBm =  new  ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm,  null ,  null );
        Log.v(TAG, "compress: " + bitmap.getByteCount() + ": " + baos.toByteArray().length);
        return bitmap;
    }

    public static Bitmap compress0(Bitmap image, int size) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 85, out);
        float zoom = (float)Math.sqrt(size * 1024 / (float)out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        Bitmap result = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        while(out.toByteArray().length > size * 1024){
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        }

        return result;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapBytes = baos.toByteArray();
                Log.v(TAG, "encodeToString: " + bitmap.getByteCount() + ": " + baos.toByteArray().length);
                result = new String(Base64.encodeToString(bitmapBytes, Base64.DEFAULT).getBytes(), "UTF-8");
                Log.v(TAG, "encodeToString: " + result.replaceAll("\\n", ""));
                System.out.println(result);
                Log.v(TAG, "result size: " + result.length() * 0.75);
                baos.flush();
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String args[]) {
        Bitmap bitmap = BitmapFactory.decodeFile("/Users/apple/Downloads/1586639011825.jpg");
        System.out.println(bitmap.getByteCount());
        System.out.println(bitmapToBase64(bitmap));


    }


}


