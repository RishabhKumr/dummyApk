package com.example.theplayschool.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.theplayschool.R;
import com.example.theplayschool.Student_Dashboard;
import com.example.theplayschool.SubjectSelectDialogFragment;
import com.example.theplayschool.TestCreateActivity;
import  com.example.theplayschool.Student_Dashboard;

public class DashboardFragment extends Fragment implements SubjectSelectDialogFragment.SubjectSelectListener  {

    private DashboardViewModel dashboardViewModel;
    //to get the selected subject
    String subjectselected="";
    private  Button create_test_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
       // final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        /*
        Dialog dialog;
        create_test_button = (Button) root.findViewById(R.id.test_create_button);
        create_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment subjectSelectDialog=new SubjectSelectDialogFragment();
                subjectSelectDialog.setCancelable(false);
                subjectSelectDialog.show(getFragmentManager(),"SubjectSelectDialog");
            }
        });

         */
        return root;


    }
    //implement methods from SubjectSelectListener interface for positive button clicked
    @Override
    public void onPositiveButtonClicked(String[] subject_list, int Position) {
        //get the selected subject name and pass it to an intent.
        subjectselected=subject_list[Position];
        Log.d("main", "onPositiveButtonClicked: "+subjectselected);
        Intent intent=new Intent(getActivity(),TestCreateActivity.class);
        intent.putExtra("Subjectname",subjectselected);
        //start the TestCreateActivity to take other details
       startActivity(intent);
    }

    //implement methods from SubjectSelectListener interface negative button clicked
    @Override
    public void onNegativeButtonClicked() {

    }
}
