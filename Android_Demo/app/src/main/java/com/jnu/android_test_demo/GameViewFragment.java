package com.jnu.android_test_demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameViewFragment extends Fragment {



    public GameViewFragment() {
        // Required empty public constructor
    }

    public static GameViewFragment newInstance(String param1, String param2) {
        GameViewFragment fragment = new GameViewFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_view, container, false);
    }
}