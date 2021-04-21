package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_repository extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.allpdfbtn).setOnClickListener(this);
        view.findViewById(R.id.allpicturebtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allpdfbtn:
                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent = new Intent(getActivity(), Allpdf.class);
                startActivity(intent);
                break;
            case R.id.allpicturebtn:
                SharedPrefManager.getInstance(getActivity()).clear();
                Intent intent2 = new Intent(getActivity(), Allpicture.class);
                startActivity(intent2);
                break;
        }
    }
}
