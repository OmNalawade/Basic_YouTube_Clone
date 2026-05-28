package com.example.youtubeclone.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.youtubeclone.CustomView.ResizableVideoView;
import com.example.youtubeclone.R;

public class WatchFragment extends Fragment {

    private static final String ARG_VIDEO = "video";

    private boolean isSubscribed = false;

    // 🔹 handler for progress update
    private Handler handler = new Handler();

    public static WatchFragment newInstance(int videoRes) {
        WatchFragment fragment = new WatchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIDEO, videoRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity())
                    .getSupportActionBar()
                    .hide();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity())
                    .getSupportActionBar()
                    .show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watch, container, false);

        view.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setPadding(
                    v.getPaddingLeft(),
                    insets.getSystemWindowInsetTop(),
                    v.getPaddingRight(),
                    v.getPaddingBottom()
            );
            return insets;
        });

        FrameLayout videoContainer = view.findViewById(R.id.video_container);
        SeekBar seekBar = view.findViewById(R.id.video_seekbar);

        ResizableVideoView videoView = new ResizableVideoView(requireContext());
        ImageView playIcon = new ImageView(requireContext());

        playIcon.setImageResource(android.R.drawable.ic_media_play);
        playIcon.setColorFilter(getResources().getColor(android.R.color.white));
        playIcon.setLayoutParams(new FrameLayout.LayoutParams(120, 120));
        playIcon.setVisibility(View.GONE);

        int videoRes = getArguments().getInt(ARG_VIDEO);

        Uri uri = Uri.parse("android.resource://" +
                requireContext().getPackageName() + "/" + videoRes);

        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            videoView.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());
            seekBar.setMax(videoView.getDuration());
            videoView.start();
            updateSeekBar(videoView, seekBar);
        });

        // 🔹 Seek when user drags bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        videoContainer.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                playIcon.setVisibility(View.VISIBLE);
            } else {
                videoView.start();
                playIcon.setVisibility(View.GONE);
                updateSeekBar(videoView, seekBar);
            }
        });

        // 🔹 ADD VIEWS
        videoContainer.addView(videoView);
        videoContainer.addView(playIcon);

        // ✅ ONLY NECESSARY ADDITION (VERY IMPORTANT)
        seekBar.bringToFront();

        TextView titleView = view.findViewById(R.id.watch_title);
        TextView metaView = view.findViewById(R.id.watch_meta);

        titleView.setText(getVideoTitle(videoRes));
        metaView.setText(getVideoMeta(videoRes));

        TextView channelNameView = view.findViewById(R.id.watch_channel_name);
        TextView subscriberView = view.findViewById(R.id.watch_subscriber);

        channelNameView.setText(getChannelName(videoRes));
        subscriberView.setText(getSubscriberCount(videoRes));

        TextView subscribeBtn = view.findViewById(R.id.btn_subscribe);
        subscribeBtn.setOnClickListener(v -> {
            if (!isSubscribed) {
                isSubscribed = true;
                subscribeBtn.setText("SUBSCRIBED");
                subscribeBtn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                isSubscribed = false;
                subscribeBtn.setText("SUBSCRIBE");
                subscribeBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        });

        return view;
    }

    // 🔹 update seekbar continuously
    private void updateSeekBar(ResizableVideoView videoView, SeekBar seekBar) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView.isPlaying()) {
                    seekBar.setProgress(videoView.getCurrentPosition());
                    handler.postDelayed(this, 500);
                }
            }
        }, 500);
    }

    // ---- existing methods unchanged ----

    private String getVideoTitle(int videoRes) {
        if (videoRes == R.raw.video1)
            return "Gada Electronics Ki Nayi Scheme | Taarak Mehta Ka Ooltah Chashmah | Full Episode 4521";
        else if (videoRes == R.raw.video2)
            return "KAUN TUJHE Lyrical | M.S. DHONI -THE UNTOLD STORY | Amaal Mallik Palak | Sushant Singh Disha Patani";
        else if (videoRes == R.raw.video3)
            return "Motu Patlu | मोटू पतलू S1 | Dr. Jhatka Ki Fighting Machine | Episode 241 Part 2 | Voot Kids";
        else if (videoRes == R.raw.video4)
            return "SHOORVEER 3 - A Tribute to छत्रपति शिवaji महारaj | Rapperiya Baalam Ft. Shambho I Meetu Solanki";
        else if (videoRes == R.raw.video5)
            return "295 (Official Audio) | Sidhu Moose Wala | The Kidd | Moosetape";
        else
            return "Your Video Title";
    }

    private String getVideoMeta(int videoRes) {
        if (videoRes == R.raw.video1) return "3.6M views •  3 months ago";
        else if (videoRes == R.raw.video2) return "760M views •  9 years ago";
        else if (videoRes == R.raw.video3) return "25M views •  4 years ago";
        else if (videoRes == R.raw.video4) return "198M views •  3 years ago";
        else if (videoRes == R.raw.video5) return "720M views •  4 years ago";
        else return "0 views • Unknown date";
    }

    private String getChannelName(int videoRes) {
        if (videoRes == R.raw.video1 || videoRes == R.raw.video3)
            return "Sony SAB";
        else if (videoRes == R.raw.video2)
            return "T-Series";
        else if (videoRes == R.raw.video4)
            return "Rapperiya Baalam";
        else if (videoRes == R.raw.video5)
            return "Sidhu Moose Wala";
        else
            return "Unknown Channel";
    }

    private String getSubscriberCount(int videoRes) {
        if (videoRes == R.raw.video1 || videoRes == R.raw.video3)
            return "105M subscribers";
        else if (videoRes == R.raw.video2)
            return "309M subscribers";
        else if (videoRes == R.raw.video4)
            return "1.16M subscribers";
        else if (videoRes == R.raw.video5)
            return "29.7M subscribers";
        else
            return "0 subscribers";
    }
}











