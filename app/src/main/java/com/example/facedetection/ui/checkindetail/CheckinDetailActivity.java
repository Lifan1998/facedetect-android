package com.example.facedetection.ui.checkindetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import com.example.facedetection.BuildConfig;
import com.example.facedetection.R;
import com.example.facedetection.data.model.Student;
import com.example.facedetection.data.vo.StudentVO;
import com.example.facedetection.dummy.DummyContent;
import com.example.facedetection.service.http.task.UpdateStudentStatusTask;
import com.example.facedetection.ui.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

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
import java.io.IOException;
import java.text.SimpleDateFormat;

public class CheckinDetailActivity extends AppCompatActivity
                    implements PlaceholderFragment.OnListFragmentInteractionListener {
    private final String TAG = "CheckinDetailActivity";
    private String mFilePath = "";

    private int checkInId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_checkin_detail);
        checkInId = intent.getIntExtra("id", 0);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), checkInId);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });



        String className = intent.getStringExtra("className");
        String time = intent.getStringExtra("time");
        Log.d(TAG, "onCreate: id " + checkInId);

        TextView textView = findViewById(R.id.title_time);
        TextView textView1 = findViewById(R.id.title);
        textView1.setText(className);
        textView.setText(time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //   返回缩略图
        if (requestCode == 100) {
            if (data != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                }
            }
        }
        // 原图
        if (requestCode == 300) {
            FileInputStream inputStream = null;
            try {
//                inputStream = new FileInputStream(mFilePath);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (true) {
//                    show_iv.setImageBitmap(rotateBitmap(bitmap));
//                    System.out.println(bitmap);
                    final ProgressDialog pd5 = new ProgressDialog(this);
                    pd5.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                    pd5.setCancelable(true);// 设置是否可以通过点击Back键取消
                    pd5.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                    pd5.setIcon(R.mipmap.ic_launcher);//设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                    pd5.setTitle("提示");
// dismiss监听
                    pd5.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                        }
                    });
// 监听Key事件被传递给dialog
                    pd5.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return false;
                        }
                    });
// 监听cancel事件
                    pd5.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                        }
                    });
//设置可点击的按钮，最多有三个(默认情况下)
                    pd5.setButton(DialogInterface.BUTTON_POSITIVE, "后台识别",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pd5.dismiss();
                                }
                            });
                    pd5.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    pd5.dismiss();
                                }
                            });
//                    pd5.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // TODO Auto-generated method stub
//                                }
//                            });
                    pd5.setMessage("人脸识别中");
                    pd5.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                // cancel和dismiss方法本质都是一样的，都是从屏幕中删除Dialog,唯一的区别是
                                // 调用cancel方法会回调DialogInterface.OnCancelListener如果注册的话,dismiss方法不会回掉
                                pd5.cancel();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

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
//        if (requestCode == 500) {
//            Uri uri = data.getData();
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }

    // 三星机型拍照的时候照片会旋转90度 所以需要转回来
    public Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap map = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return map;
    }

    @Override
    public void onListFragmentInteraction(StudentVO student) {
        showSingleChoiceDialog(CheckinDetailActivity.this, student);

    }
    int yourChoice;
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
}