package com.passion.attendancesheet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.passion.attendancesheet.R;
import com.passion.attendancesheet.model.entity.Attendance_sheet;
import com.passion.attendancesheet.utils.Accessory_tool;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>{

    Context context;
    List<Attendance_sheet> sheets = new ArrayList<>();

    HistoryItemClick historyItemClick;

    public HistoryListAdapter(Context context, HistoryItemClick historyItemClick){
        this.context = context;
        this.historyItemClick = historyItemClick;
    }

    @NonNull
    @Override
    public HistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false );
        return new HistoryListViewHolder(v , context, historyItemClick );
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListViewHolder holder, int position) {
        Attendance_sheet sheetDetail = sheets.get(position);

        String[] date_time = sheetDetail.data_and_time.split("-",3);
        holder.date.setText( date_time[0] );
        holder.time.setText( date_time[1] );

        holder.sheet_id = sheetDetail.id;
        String formattedCourseName = sheetDetail.course_id.split("-")[0] + " " + Accessory_tool.getRomanFromInt( Integer.parseInt(sheetDetail.course_id.split("-")[1]));
        holder.course.setText( formattedCourseName  );
        holder.lecture.setText( "Lecture " + sheetDetail.lecture );
        holder.date.setTag( sheetDetail );
    }

    @Override
    public int getItemCount() {
        return sheets.size();
    }

    public void setHistories( List<Attendance_sheet> sheets ){
        this.sheets = sheets;
        notifyDataSetChanged();     // initiate recycler view item recreate to reflect latest data
    }

    public int getSheetId( int pos ){
        return sheets.get(pos).id;
    }


    public static class HistoryListViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        TextView course;
        TextView lecture;
        TextView time;
        int sheet_id;

        public HistoryListViewHolder(@NonNull View itemView, Context context , HistoryItemClick historyItemClick) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            course = itemView.findViewById(R.id.att_course_name);
            lecture = itemView.findViewById(R.id.today_lecture_no);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: open editAttendanceActivity
//                    Intent intent = new Intent( context, AttendanceActivity.class );
//
//                    intent.putExtra( AttendanceActivity.HISTORY_DETAILS, (SheetDetailView)date.getTag() );
//                    intent.putExtra( AttendanceActivity.MODE, context.getResources().getString(R.string.edit));
//                    intent.putExtra( AttendanceActivity.COURSE, sheet_id);
//                    context.startActivity(intent);

                      historyItemClick.openEditAttendance( ( Attendance_sheet)date.getTag(), context.getResources().getString(R.string.edit)  );

                }
            });
        }

        public int getSheet_id() {
            return sheet_id;
        }
    }


    public static interface HistoryItemClick {
        void openEditAttendance(Attendance_sheet sheet, String mode);
    }
}

