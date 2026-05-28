package com.example.youtubeclone.Fragment;

import android.content.Intent; // ⭐ ADDED
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeclone.CustomView.ResizableVideoView;
import com.example.youtubeclone.R;
import com.example.youtubeclone.WatchActivity; // ⭐ ADDED

public class HomeFragment extends Fragment {

    private FrameLayout videoFrame1, videoFrame2, videoFrame3, videoFrame4, videoFrame5;
    private FrameLayout shortsFrame1, shortsFrame2, shortsFrame3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        videoFrame1 = root.findViewById(R.id.video_frame_1);
        videoFrame2 = root.findViewById(R.id.video_frame_2);
        videoFrame3 = root.findViewById(R.id.video_frame_3);
        videoFrame4 = root.findViewById(R.id.video_frame_4);
        videoFrame5 = root.findViewById(R.id.video_frame_5);

        shortsFrame1 = root.findViewById(R.id.shorts_frame_1);
        shortsFrame2 = root.findViewById(R.id.shorts_frame_2);
        shortsFrame3 = root.findViewById(R.id.shorts_frame_3);

        videoFrame1.post(() -> playVideo(videoFrame1, R.raw.video1, R.drawable.video_thumb_1));
        videoFrame2.post(() -> playVideo(videoFrame2, R.raw.video2, R.drawable.video_thumb_2));
        videoFrame3.post(() -> playVideo(videoFrame3, R.raw.video3, R.drawable.video_thumb_3));
        videoFrame4.post(() -> playVideo(videoFrame4, R.raw.video4, R.drawable.video_thumb_4));
        videoFrame5.post(() -> playVideo(videoFrame5, R.raw.video5, R.drawable.video_thumb_5));

        shortsFrame1.post(() -> playShortVideo(shortsFrame1, R.raw.short1, R.drawable.short_thumb_1));
        shortsFrame2.post(() -> playShortVideo(shortsFrame2, R.raw.short2, R.drawable.short_thumb_2));
        shortsFrame3.post(() -> playShortVideo(shortsFrame3, R.raw.short3, R.drawable.short_thumb_3));

        return root;
    }

    private void playVideo(FrameLayout container, int rawVideoId, int thumbRes) {
        container.removeAllViews();

        ImageView thumbnail = new ImageView(requireContext());
        thumbnail.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setImageResource(thumbRes);

        ResizableVideoView videoView = new ResizableVideoView(requireContext());
        videoView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        Uri videoUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + rawVideoId);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());
        });

        // ⭐ OPEN WATCH SCREEN (LIKE YOUTUBE)
        thumbnail.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), WatchActivity.class);
            intent.putExtra("video", rawVideoId);
            startActivity(intent);
        });

        container.addView(videoView);
        container.addView(thumbnail);
    }

    private void playShortVideo(FrameLayout container, int rawVideoId, int thumbRes) {
        container.removeAllViews();

        ImageView thumbnail = new ImageView(requireContext());
        thumbnail.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setImageResource(thumbRes);

        ResizableVideoView videoView = new ResizableVideoView(requireContext());
        videoView.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        Uri videoUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + rawVideoId);
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            videoView.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());
        });

        // ✅ OPEN FULLSCREEN SHORTS (UNCHANGED)
        thumbnail.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ShortsFragment.newInstance(rawVideoId))
                .addToBackStack(null)
                .commit());

        container.addView(videoView);
        container.addView(thumbnail);
    }
}












