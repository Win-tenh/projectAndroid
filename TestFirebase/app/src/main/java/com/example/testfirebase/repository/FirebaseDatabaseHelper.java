package com.example.testfirebase.repository;

import com.example.testfirebase.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mRefStudents;

    public FirebaseDatabaseHelper() {
        String databaseUrl = "https://test-firebase-ecfde-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl);
        mRefStudents = mDatabase.getReference("students");
    }

    public void addStudent(Student student) {
        String id = mRefStudents.push().getKey();
        student.setId(id);
        mRefStudents.child(id).setValue(student);
    }

    public void updateStudent(String id, Student student) {
        mRefStudents.child(id).setValue(student);
    }

    public void deleteStudent(String id) {
        mRefStudents.child(id).removeValue();
    }

    public DatabaseReference getRef() {
        return mRefStudents;
    }
}
