package com.example.facedetection.ui.checkindetail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.facedetection.R;
import com.example.facedetection.data.emus.StudentStatus;
import com.example.facedetection.data.model.Student;
import com.example.facedetection.data.vo.CheckInDetailVO;
import com.example.facedetection.data.vo.StudentVO;
import com.example.facedetection.dummy.StudentContent;
import com.example.facedetection.service.http.task.CheckInDetailTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private OnListFragmentInteractionListener mListener;

    private int index;
    private int checkInId;

    public static PlaceholderFragment newInstance(int index, int checkInId) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        Log.v("tab", index + "");
        fragment.setArguments(bundle);
        fragment.checkInId = checkInId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checkin_detail, container, false);
        final GridView gridView = root.findViewById(R.id.section_label);

        try {
            CheckInDetailVO checkInDetailVO = new CheckInDetailTask().execute(checkInId+"").get();
            List<StudentVO> studentVOList = checkInDetailVO.getStudentVOList();
            Log.v("tab", index + "");

            switch (index) {
                case 1: break;
                case 2: studentVOList = getStudentListByStatus(studentVOList, StudentStatus.NORMAL.getValue());break;
                case 3: studentVOList = getStudentListByStatus(studentVOList, StudentStatus.BE_LATE.getValue());break;
                case 4: studentVOList = getStudentListByStatus(studentVOList, StudentStatus.ABSENCE.getValue());break;
                case 5: studentVOList = getStudentListByStatus(studentVOList, StudentStatus.LEAVE.getValue());break;
            }
            CheckinAdapter checkinAdapter = new CheckinAdapter(studentVOList, mListener, R.layout.item_user);
            gridView.setAdapter(checkinAdapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gridView.setNumColumns(4);
        gridView.setGravity(GridView.AUTO_FIT);

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.v("itemclick", position+"");
//                showSingleChoiceDialog(getActivity().getApplicationContext());
//
//            }
//        });
        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(StudentVO studentVO);
    }

    private List<StudentVO> getStudentListByStatus(List<StudentVO> studentVOList, int status) {
        List<StudentVO> studentVOList1 = new ArrayList<>();
        for (int i = 0; i < studentVOList.size(); i++) {
            if (studentVOList.get(i).getStatus() == status) {
                studentVOList1.add(studentVOList.get(i));
            }
        }
        return studentVOList1;
    }
}