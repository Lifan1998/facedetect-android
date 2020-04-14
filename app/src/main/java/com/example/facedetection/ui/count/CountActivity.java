package com.example.facedetection.ui.count;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.facedetection.R;
import com.example.facedetection.data.vo.StudentCountVO;
import com.example.facedetection.ui.checkindetail.CheckinDetailActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Calendar;

public class CountActivity extends AppCompatActivity
implements StudentCountItemFragment.OnListFragmentInteractionListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CountActivity";
    public static int classId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        LinearLayout linearLayout = findViewById(R.id.time_range_view);
        classId = getIntent().getIntExtra("classroomId", 0);
        Log.v(TAG, classId + "");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CountActivity.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
// If you're calling this from a support Fragment
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        classId = getIntent().getIntExtra("classroomId", 0);
        Log.v(TAG,  "onResume " + classId + "");
    }


    @Override
    public void onListFragmentInteraction(StudentCountVO item) {
        Toast.makeText(CountActivity.this, "这里不可以点击哦", Toast.LENGTH_SHORT);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.v("toTime", date);
//        dateTextView.setText(date);
    }
}
