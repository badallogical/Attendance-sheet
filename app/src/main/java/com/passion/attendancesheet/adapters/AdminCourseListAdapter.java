package com.passion.attendancesheet.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.passion.attendancesheet.AdminActivity;
import com.passion.attendancesheet.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdminCourseListAdapter extends RecyclerView.Adapter<AdminCourseListAdapter.CourseListViewHolder> {

    ArrayList<String> courseList;
    Context context;
    CourseListClick courseListClick;

    public AdminCourseListAdapter( ArrayList<String> courseList , Context context, CourseListClick courseListClick){
        this.courseList = courseList;
        this.context = context;
        this.courseListClick = courseListClick;
    }

    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_course_card, parent, false );
        return new CourseListViewHolder( context, view );
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {
        holder.text.setText( courseList.get(position ));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseListViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public CourseListViewHolder( Context context, @NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.course_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    courseListClick.openCrPanel();
                }
            });




        }
    }

}
