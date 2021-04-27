package com.example.NoSound;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInfo extends Fragment {
    private OnDataPass dataPasser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText firstNameText;
    private TextInputEditText lastNameText;
    private TextInputEditText departmentText;
    private TextInputEditText birthNumberText;

    public PersonalInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalInfo newInstance(String param1, String param2) {
        PersonalInfo fragment = new PersonalInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        firstNameText= requireView().findViewById(R.id.firstName);
        lastNameText = requireView().findViewById(R.id.lastName);
        departmentText = requireView().findViewById(R.id.department);
        birthNumberText = requireView().findViewById(R.id.birthNumber);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee employee = new Employee(firstNameText.getText().toString(),lastNameText.getText().toString(),departmentText.getText().toString(), birthNumberText.getText().toString());
                passData(employee);
                saveInfo(view);
                NavHostFragment.findNavController(PersonalInfo.this)
                        .navigate(R.id.action_personalInfo_to_vy3);
            }
        });
    }
    private void saveInfo(View v){
        ((MainActivity) requireActivity()).saveEmployeePublicly(v);
    }
    private void passData(Employee employee) {
        dataPasser.onEmployeePass(employee);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
}