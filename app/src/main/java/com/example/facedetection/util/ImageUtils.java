package com.example.facedetection.util;




import android.graphics.Bitmap;
import android.util.Base64;

import java.io.*;
import java.util.List;

/**
 * @author fan.li
 * @date 2020-04-04
 * @description
 */

public class ImageUtils {


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

    public static void main(String args[]) {
        File file = new File("/Users/apple/Downloads/切图/发现/testv3.png");

//        File file2 = handleFileSize(file, 100 * 1000);
//
//        System.out.println(file2.length());

        System.out.println(file.length());
        System.out.println(file.getName());
        System.out.println(encodeFileToBase64Binary(file));


    }


}


