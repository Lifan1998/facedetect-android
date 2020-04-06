package com.example.facedetection.ui.checkindetail;

import androidx.annotation.NonNull;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.facedetection.R;
import com.example.facedetection.data.emus.StudentStatus;
import com.example.facedetection.data.model.Student;

import java.util.List;

public class CheckinAdapter extends BaseAdapter {

    private  List<Student> mValues;
    private PlaceholderFragment.OnListFragmentInteractionListener mListener;
    private int mLayoutRes;

    public CheckinAdapter(List<Student> mValues, PlaceholderFragment.OnListFragmentInteractionListener mListener, int mLayoutRes) {
        this.mValues = mValues;
        this.mListener = mListener;
        this.mLayoutRes = mLayoutRes;
    }

    public CheckinAdapter(List<Student> items, PlaceholderFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public CheckinAdapter(List<Student> mValues, int mLayoutRes) {
        this.mValues = mValues;
        this.mLayoutRes = mLayoutRes;
    }

    public CheckinAdapter(){

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        onBindViewHolder(holder, position);
        return holder.mView;
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Student student = mValues.get(position);
        Glide.with(holder.mView).load(student.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.mAvatarView);
        holder.mNameView.setText(student.getName());
        holder.mStatusView.setText(StudentStatus.fromValue(student.getStatus()).getDesc());
        holder.mStatusView.setBackgroundColor(Color.parseColor("#F2F4F4"));
        if (student.getStatus() == 1) {
            holder.mStatusView.setTextColor(Color.parseColor("#76D7C4"));
        }
        if (student.getStatus() == 2) {
            holder.mStatusView.setTextColor(Color.parseColor("#F7DC6F"));
        }
        if (student.getStatus() == 3) {
            holder.mStatusView.setTextColor(Color.parseColor("#EC7063"));
        }
        if (student.getStatus() == 4) {
            holder.mStatusView.setTextColor(Color.parseColor("#85C1E9"));
        }

//        Glide.with(holder.mView).load(R.drawable.editor).into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(student);
                    Log.v("ITEM: ", holder.mNameView.getText().toString());
                }
            }
        });
    }


    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Student getItem(int i) {
        return mValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        public  View mView;
        public  ImageView mAvatarView;
        public  TextView mNameView;
        public  TextView mStatusView;
        public ImageView imageView;
        public Student mItem;
        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

        //构造方法，完成相关初始化
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            super();
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
            mView = convertView;
            mAvatarView = (ImageView) mView.findViewById(R.id.student_avatar);
            mNameView = (TextView) mView.findViewById(R.id.student_name);
            mStatusView = (TextView) mView.findViewById(R.id.student_status);
            imageView = mView.findViewById(R.id.student_editor_img);
        }

        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;

            return holder;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}


