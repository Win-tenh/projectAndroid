package com.example.testfirebase.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testfirebase.model.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private final List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Student student = students.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(student.getName() + " - " + student.getEmail());

        return convertView;
    }
}
