package com.example.youtubeclone.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.youtubeclone.CustomView.ResizableVideoView;
import com.example.youtubeclone.R;

public class ShortsFragment extends Fragment {

    private static final String ARG_VIDEO = "video";

    public static ShortsFragment newInstance(int videoRes) {
        ShortsFragment fragment = new ShortsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIDEO, videoRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().hide();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shorts, container, false);

        ResizableVideoView videoView = view.findViewById(R.id.shortsVideoView);

        int videoRes = getArguments().getInt(ARG_VIDEO);

        Uri uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + videoRes);

        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.start();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().show();
            }
        }
    }
}


