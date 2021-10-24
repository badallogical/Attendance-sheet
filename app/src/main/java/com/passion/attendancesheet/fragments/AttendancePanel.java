package com.passion.attendancesheet.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.ui.graphics.Paint;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.passion.attendancesheet.R;
import com.passion.attendancesheet.adapters.StudentListAdapter;
import com.passion.attendancesheet.databinding.FragmentAttendanceBinding;
import com.passion.attendancesheet.model.AttendanceSheetViewModel;
import com.passion.attendancesheet.model.entity.Attendance_sheet;
import com.passion.attendancesheet.model.entity.Student;
import com.passion.attendancesheet.model.entity.Attendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * It is the panel to take attendance and save, and share  ( as excel )
 */
public class AttendancePanel extends Fragment {

    FragmentAttendanceBinding binding;
    AttendanceSheetViewModel viewModel;
    StudentListAdapter studentListAdapter;
    AttendancePanelArgs args;

    public AttendancePanel() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get( AttendanceSheetViewModel.class );
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAttendanceBinding.inflate( getLayoutInflater() );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = AttendancePanelArgs.fromBundle( getArguments() );

        binding.courseName.setText(args.getCourse());
        binding.lecture.setText(args.getLecture());
        binding.subject.setText(args.getSubject());
        binding.teacher.setText(args.getTeacher().split(",")[1]);

        studentListAdapter = new StudentListAdapter(getContext(), new ArrayList<Student>());
        viewModel.getAllStudent("BBA-5").observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                studentListAdapter.setStudents(students);
                studentListAdapter.notifyDataSetChanged();
            }
        });
        binding.studentList.setAdapter(studentListAdapter);
        binding.studentList.setLayoutManager( new LinearLayoutManager(getContext()));

        // Student list swipe Functioning
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

        }).attachToRecyclerView( binding.studentList );

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.attendance_sheet_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.send :    break;

            case R.id.save:     saveSheet();




        }

        return true;
    }

    private void saveSheet() {
        // create attendance sheet
        viewModel.addAttendanceSheet( new Attendance_sheet( studentListAdapter.getStudents().get(0).course_id , "00:00:00" , Integer.parseInt(args.getLecture()) , Integer.parseInt(args.getTeacher().split(",")[0] ) , args.getSubject()));

        // get Current data and time
        String dateTime = new SimpleDateFormat("MMM dd, yyyy-hh:mm a", Locale.US).format(Calendar.getInstance().getTime());

        // Get Newly inserted sheet id
        viewModel.getAllSheetsByCourseId( args.getCourse()).observe( getViewLifecycleOwner(), sheets -> {
            if( sheets.size() != 0 ){
                int sheet_id = sheets.get(0).id;

                // save all attendance now
                List<Student> studentPresent = studentListAdapter.getStudentPresent();
                for( Student s : studentPresent ){
                    viewModel.addAttendance( new Attendance(sheet_id, s.roll_number, s.name, "Present") );
                }

                studentListAdapter.notifyDataSetChanged();

            }
        });


//        List<Student> studentPresent = studentListAdapter.getStudentPresent();
//        for( Student s : studentPresent ){
//            Timber.d( s.name );
////
//        }
//        Timber.d( studentPresent.size() + "" );

    }
}