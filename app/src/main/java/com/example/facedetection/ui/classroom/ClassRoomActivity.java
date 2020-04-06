package com.example.facedetection.ui.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.facedetection.R;
import com.example.facedetection.ui.classroom.dummy.DummyContent;
import com.example.facedetection.ui.count.CountActivity;

public class ClassRoomActivity extends AppCompatActivity
                                implements ClassroomItemFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(ClassRoomActivity.this, CountActivity.class);
        startActivity(intent);
    }
}
