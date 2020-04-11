package com.example.facedetection.ui.main;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facedetection.R;
import com.example.facedetection.data.vo.CheckInItemVO;
import com.example.facedetection.ui.main.RecordFragment.OnListFragmentInteractionListener;
import com.example.facedetection.dummy.DummyContent.DummyItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRecordRecyclerViewAdapter extends RecyclerView.Adapter<MyRecordRecyclerViewAdapter.ViewHolder> {

    private final List<CheckInItemVO> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRecordRecyclerViewAdapter(List<CheckInItemVO> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(position +  1 + "");
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        holder.mTime.setText(simpleDateFormat.format(mValues.get(position).getRecentTime()));
        holder.mClassName.setText(mValues.get(position).getClassName());
        holder.studentNum.setText(mValues.get(position).getStudentNum() + "");
        holder.studentTotalNUm.setText("/" + mValues.get(position).getStudentTotalNum() + "人");



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
        public final TextView mIdView;
        public final TextView studentNum;
        public final TextView studentTotalNUm;
        public final TextView mClassName;
        public final TextView mTime;
        public CheckInItemVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            studentNum = (TextView) view.findViewById(R.id.checkin_list_studentNum);
            studentTotalNUm = view.findViewById(R.id.checkin_list_student_totalNum);
            mClassName = view.findViewById(R.id.className);
            mTime = view.findViewById(R.id.time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + studentNum.getText() + "'";
        }
    }
}
