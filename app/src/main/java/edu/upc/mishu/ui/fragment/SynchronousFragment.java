package edu.upc.mishu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.upc.mishu.R;

public class SynchronousFragment extends Fragment {
    private static SynchronousFragment instance  = null;

    public static SynchronousFragment newInstance(){
        if(instance == null){
            instance = new SynchronousFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_synchronous,container,false);
    }
}
