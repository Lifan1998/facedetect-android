package com.example.facedetection.ui.count;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facedetection.R;
import com.example.facedetection.data.vo.ClassRoomVO;
import com.example.facedetection.data.vo.StudentCountVO;
import com.example.facedetection.ui.count.StudentCountItemFragment.OnListFragmentInteractionListener;
import com.example.facedetection.ui.count.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudentCountItemRecyclerViewAdapter extends RecyclerView.Adapter<MyStudentCountItemRecyclerViewAdapter.ViewHolder> {

    private final List<StudentCountVO> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyStudentCountItemRecyclerViewAdapter(List<StudentCountVO> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_studentcountitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        StudentCountVO studentCountVO = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.number.setText(position + 1 + "");
        holder.normal.setText(studentCountVO.getNormal() + "");
        holder.leave.setText(studentCountVO.getLeave() + "");
        holder.absence.setText(studentCountVO.getAbsence() + "");
        holder.beLate.setText(studentCountVO.getBeLate() + "");
        holder.studentNo.setText("学号：" + studentCountVO.getStudentNo());
        holder.studentName.setText(studentCountVO.getName());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView number, studentName, studentNo, normal, absence, beLate, leave;

//        public final TextView mIdView;
//        public final TextView mContentView;
        public StudentCountVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
//            mContentView = (TextView) view.findViewById(R.id.content);
            number = view.findViewById(R.id.item_count_number);
            studentName = view.findViewById(R.id.item_count_student_name);
            studentNo = view.findViewById(R.id.item_count_student_no);
            normal = view.findViewById(R.id.item_count_normal);
            absence = view.findViewById(R.id.item_count_absence);
            beLate = view.findViewById(R.id.item_count_be_late);
            leave = view.findViewById(R.id.item_count_leave);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
