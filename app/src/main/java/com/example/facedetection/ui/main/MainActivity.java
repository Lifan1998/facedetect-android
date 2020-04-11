package com.example.facedetection.ui.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.facedetection.BuildConfig;
import com.example.facedetection.R;
import com.example.facedetection.data.vo.CheckInItemVO;
import com.example.facedetection.data.vo.User;
import com.example.facedetection.ui.classroom.ClassRoomActivity;
import com.example.facedetection.ui.setting.SettingsActivity;
import com.example.facedetection.dummy.DummyContent;
import com.example.facedetection.ui.checkindetail.CheckinDetailActivity;
import com.example.facedetection.ui.login.LoginActivity;
import com.example.facedetection.util.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements RecordFragment.OnListFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private ImageView show_iv;
    ImageView userAvatar;
    TextView username;
    TextView userEmail;

    private String mFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化数据中心
        SharedPreferencesUtils.init(MainActivity.this);

        View view = View.inflate(this, R.layout.activity_main, null);
        View viewLeft = (View) View.inflate(this, R.layout.nav_header_main, null);

        setContentView(view);
        Toolbar toolbar = findViewById(R.id.toolbar);
         userAvatar = viewLeft.findViewById(R.id.menu_user_avatar);
         username = viewLeft.findViewById(R.id.menu_username);
         userEmail = viewLeft.findViewById(R.id.menu_user_email);

        boolean isLogin = SharedPreferencesUtils.getBoolean(SharedPreferencesUtils.IS_LOGIN);

        if (isLogin) {


            Log.d(TAG, "onCreate: " + username.getText());

            User user = SharedPreferencesUtils.getUser();
            Log.d(TAG, "onCreate: " + username + user);
            username.setText(user.getUsername());
            Log.d(TAG, "onCreate: " + username.getText());
            userEmail.setText(user.getEmail());
            Glide.with(userAvatar).load(user.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userAvatar);
        }
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent();
                // 1. 直接调用系统相机 没有返回值
//       intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
//       startActivity(intent);
                // 2 调用系统相机 有返回值 但是返回值是 缩略图
//       intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//       startActivityForResult(intent, 100);
                // 3 .返回原图
                mFilePath =
                        Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/picture.png";

                Uri uri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(mFilePath));

                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                //  指定路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, 300);

                // 4. 打开系统相册
//      intent.setAction(Intent.ACTION_PICK);
//      intent.setType("image/*");
//      startActivityForResult(intent, 500);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_login) {
            // TODO 判断登录没有
            boolean isLogin = SharedPreferencesUtils.getBoolean(SharedPreferencesUtils.IS_LOGIN);

            if (isLogin) {
                Toast.makeText(MainActivity.this, "已登录", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            // TODO 判断登录没有
            boolean isLogin = SharedPreferencesUtils.getBoolean(SharedPreferencesUtils.IS_LOGIN);

            if (isLogin) {


                Log.d(TAG, "onNavigationItemSelected: " + username.getText());

                User user = SharedPreferencesUtils.getUser();
                Log.d(TAG, "onNavigationItemSelected: " + username + user);
                username.setText(user.getUsername());
                Log.d(TAG, "onNavigationItemSelected: " + username.getText());
                userEmail.setText(user.getEmail());
                Glide.with(userAvatar).load(user.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userAvatar);

                Toast.makeText(MainActivity.this, "已登录", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, ClassRoomActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(MainActivity.this, "功能暂未开放", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_tools) {
            Toast.makeText(MainActivity.this, "功能暂未开放", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "功能暂未开放", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            SharedPreferencesUtils.putBoolean(SharedPreferencesUtils.IS_LOGIN, false);
            SharedPreferencesUtils.setUser(null);
            Toast.makeText(MainActivity.this, "已注销", Toast.LENGTH_SHORT).show();

            username.setText("");
            userEmail.setText("");
            Glide.with(userAvatar).load(R.drawable.app_icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(userAvatar);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(CheckInItemVO item) {
        Intent intent = new Intent(MainActivity.this, CheckinDetailActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("content", item.getStudentNum());
        intent.putExtra("className", item.getClassName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        intent.putExtra("time", simpleDateFormat.format(item.getRecentTime()));
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(TAG, requestCode + ": " + requestCode + data);
        //   返回缩略图
        if (requestCode == 100) {
            if (data != null) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                if (bitmap != null) {
//                    show_iv.setImageBitmap(bitmap);
//                }
            }
        }
        // 原图
        if (requestCode == 300) {
            FileInputStream inputStream = null;
            try {
//                try {
//                    inputStream = new FileInputStream(mFilePath);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                Bitmap bitmap = null;
//                bitmap = (Bitmap) data.getExtras().get("data");
                Log.v(TAG, bitmap +"");

                Uri uri = data.getData();

                Log.v(TAG, bitmap + " " + uri);

                try {
                    bitmap = null;
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Log.v(TAG, bitmap + "");


                bitmap = null;

                Log.v(TAG, bitmap + "");



                bitmap = BitmapFactory.decodeStream(inputStream);

                bitmap = null;
                Log.v(TAG, bitmap + "");
                if (bitmap != null) {

                    Log.v(TAG, bitmap.toString());
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
//                    pd5.setButton(DialogInterface.BUTTON_POSITIVE, "后台识别",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // TODO Auto-generated method stub
//                                    Intent intent = new Intent(MainActivity.this, CheckinDetailActivity.class);
//                                    intent.putExtra("id", 1);
//                                    intent.putExtra("content", "创建后");
//                                    startActivity(intent);
//                                }
//                            });
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
                                Intent intent = new Intent(MainActivity.this, CheckinDetailActivity.class);
                                intent.putExtra("id", 1);
                                intent.putExtra("content", "");
                                intent.putExtra("className", "网嵌162");
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                intent.putExtra("time", simpleDateFormat.format(new Date()));
                                startActivity(intent);
                                // dialog.dismiss();
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
        if (requestCode == 500) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // 三星机型拍照的时候照片会旋转90度 所以需要转回来
    public Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap map = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return map;
    }

}
