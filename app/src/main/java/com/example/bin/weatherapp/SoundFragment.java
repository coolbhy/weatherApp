package com.example.bin.weatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author: Bhy
 * @Date: 2018/11/23
 */
public class SoundFragment extends Fragment{
    public SoundFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sound_fragment, container, false);
        return view;
    }
}