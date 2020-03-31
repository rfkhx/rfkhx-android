package edu.upc.mishu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.upc.mishu.R;

public class PasswordFragment extends Fragment {
    private static PasswordFragment instance  = null;

    public static PasswordFragment newInstance(){
        if(instance == null){
            instance = new PasswordFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_password,container,false);
        return view;
    }
}
