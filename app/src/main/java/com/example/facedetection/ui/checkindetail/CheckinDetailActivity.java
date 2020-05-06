package com.example.facedetection.ui.checkindetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.example.facedetection.BuildConfig;
import com.example.facedetection.R;
import com.example.facedetection.data.Result;
import com.example.facedetection.data.model.Student;
import com.example.facedetection.data.vo.CheckInDetailVO;
import com.example.facedetection.data.vo.StudentVO;
import com.example.facedetection.dummy.DummyContent;
import com.example.facedetection.service.http.request.FaceDetectMultifaceRequest;
import com.example.facedetection.service.http.task.CheckInDetailTask;
import com.example.facedetection.service.http.task.CheckInTask;
import com.example.facedetection.service.http.task.UpdateStudentStatusTask;
import com.example.facedetection.ui.main.MainActivity;
import com.example.facedetection.util.ImageUtils;
import com.example.facedetection.util.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CheckinDetailActivity extends AppCompatActivity
                    implements PlaceholderFragment.OnListFragmentInteractionListener {
    private final String TAG = "CheckinDetailActivity";
    private String mFilePath = "";

    private int checkInId;

    int yourChoice;

    private CheckInDetailVO checkInDetailVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_checkin_detail);
        checkInId = Integer.parseInt(intent.getStringExtra("checkInId"));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), checkInId);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showListDialog(CheckinDetailActivity.this);
            }
        });

        Log.d(TAG, "onCreate: id " + checkInId);

        try {
            checkInDetailVO = new CheckInDetailTask().execute(checkInId+"").get();
            TextView textView = findViewById(R.id.title_time);
            TextView textView1 = findViewById(R.id.title);
            textView1.setText(checkInDetailVO.getClassName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            textView.setText(simpleDateFormat.format(checkInDetailVO.getUpdateTime()));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // 原图
        if (requestCode == 300) {
            Optional<Bitmap> bitmapOptional = null;
            FileInputStream inputStream = null;
            try {
                try {
                    inputStream = new FileInputStream(mFilePath);
                    bitmapOptional = Optional.of(BitmapFactory.decodeStream(inputStream));
                    Toast.makeText(getParent(), "获取图片成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "图片获取失败", Toast.LENGTH_SHORT).show();
                }

                if (bitmapOptional.isPresent()) {
                    Bitmap bitmap = bitmapOptional.get();

                    final ProgressDialog pd5 = new ProgressDialog(this);
                    pd5.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                    pd5.setCancelable(true);// 设置是否可以通过点击Back键取消
                    pd5.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                    pd5.setIcon(R.drawable.app_icon);//设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                    pd5.setTitle("创建打卡");

                    pd5.setMessage("人脸识别中");
                    pd5.show();

                    try {

                        FaceDetectMultifaceRequest request = new FaceDetectMultifaceRequest();

                        request.setCreateCheckIn(false);
                        request.setUserId(Integer.parseInt(SharedPreferencesUtils.getString(SharedPreferencesUtils.USER_ID)));
                        request.setCheckInId(checkInId);
                        Bitmap bitmap1 = ImageUtils.compress0(bitmap, 200);
                        String imageBase64 = ImageUtils.bitmapToBase64(bitmap1);
                        request.setImage(imageBase64);

                        Result result = new CheckInTask().execute(JSON.toJSONString(request)).get();
                        if (result.isSuccess()) {
                            Toast.makeText(getApplicationContext(), "更新打卡成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    pd5.cancel();

                }

            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        // 相册

        if (requestCode == 500) {


            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(CheckinDetailActivity.this, "取消从相册选择", Toast.LENGTH_LONG).show();
                return;
            }

            Bitmap bitmap = null;
            Uri imageUri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(CheckinDetailActivity.this.getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final ProgressDialog pd5 = new ProgressDialog(this);
            pd5.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            pd5.setCancelable(true);// 设置是否可以通过点击Back键取消
            pd5.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            pd5.setIcon(R.drawable.app_icon);//设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
            pd5.setTitle("更新打卡");
            pd5.setMessage("人脸识别中");
            pd5.show();

            try {

                FaceDetectMultifaceRequest request = new FaceDetectMultifaceRequest();

                String imageBase64 = ImageUtils.bitmapToBase64(bitmap);

                request.setCreateCheckIn(false);
                request.setCheckInId(checkInId);
                request.setUserId(Integer.parseInt(SharedPreferencesUtils.getString(SharedPreferencesUtils.USER_ID)));
                request.setImage(imageBase64);

                Result result = new CheckInTask().execute(JSON.toJSONString(request)).get();
                if (result.isSuccess()) {

                    Toast.makeText(getApplicationContext(), "更新打卡成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            pd5.cancel();


        }

    }

    @Override
    public void onListFragmentInteraction(StudentVO student) {
        showSingleChoiceDialog(CheckinDetailActivity.this, student);

    }


    private void showSingleChoiceDialog(Context context, StudentVO studentVO){
        final String[] items = { "正常","迟到","缺勤","事假" };
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog;
        singleChoiceDialog = new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle(studentVO.getName());
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, studentVO.getStatus() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            new UpdateStudentStatusTask().execute(checkInId + "", studentVO.getId() + "", yourChoice + 1 + "");
                            Toast.makeText(context,
                                    "更新状态" + items[yourChoice],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        singleChoiceDialog.show();
    }


    private void showListDialog(Context context) {
        final String[] items = { "拍照","相册"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(CheckinDetailActivity.this);
        listDialog.setTitle("选择照片");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (which == 0) {
                    Intent intent = new Intent();
                    // 3 .返回原图
                    mFilePath =
                            Environment.getExternalStorageDirectory().getAbsolutePath() +
                                    "/picture.png";

                    Uri uri = FileProvider.getUriForFile(CheckinDetailActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(mFilePath));

                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                    //  指定路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 300);


                } else {
                    Intent intent = new Intent();
                    // 4. 打开系统相册
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 500);
                }
            }
        });
        listDialog.show();
    }
}