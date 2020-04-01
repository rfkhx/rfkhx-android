package edu.upc.mishu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.upc.mishu.R;

public class EctFragment extends Fragment implements View.OnClickListener {
    private static EctFragment instance  = null;

    public static EctFragment newInstance(){
        if(instance == null){
            instance = new EctFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_etc,container,false);
        return view;

    }

    @Override
    public void onClick(View v) {

    }
}
