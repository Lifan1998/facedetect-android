package com.example.facedetection.ui.classroom;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facedetection.R;
import com.example.facedetection.data.vo.ClassRoomItemVO;
import com.example.facedetection.ui.classroom.ClassroomItemFragment.OnListFragmentInteractionListener;
import com.example.facedetection.ui.classroom.dummy.DummyContent.DummyItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ClassroomItemAdapter extends RecyclerView.Adapter<ClassroomItemAdapter.ViewHolder> {

    private final List<ClassRoomItemVO> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ClassroomItemAdapter(List<ClassRoomItemVO> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_classroomitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ClassRoomItemVO classRoomItemVO = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.mName.setText(classRoomItemVO.getName());
        if (classRoomItemVO.getUpdateTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            holder.mTime.setText(simpleDateFormat.format(classRoomItemVO.getUpdateTime()));
        }


        holder.mTotalNum.setText(classRoomItemVO.getTotalNum() + "");
        holder.mIdView.setText(position + 1 + "");
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
        public final TextView mName;
        public final TextView mTotalNum;
        public final TextView mTime;
        public ClassRoomItemVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_classroom_number);
            mName = (TextView) view.findViewById(R.id.item_classroom_name);
            mTotalNum = view.findViewById(R.id.item_classroom_total_num);
            mTime = view.findViewById(R.id.item_classroom_time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
