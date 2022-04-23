package com.mirea.zarin.mireaproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.View.OnClickListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MusicFragment extends Fragment implements OnClickListener
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MusicFragment()
    {

    }

    public static MusicFragment newInstance(String param1, String param2)
    {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ImageView imageView;
    static int i = 0;
    static int songs[] = {R.raw.you_say_run, R.raw.gurenge};
    static int covers[] = {R.drawable.run_cover, R.drawable.gurenge};

    public static int GetSongId()
    {
        return songs[i];
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ImageButton button_play;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        imageView = view.findViewById(R.id.Cover);
        button_play = (ImageButton) view.findViewById(R.id.button_play);
        button_play.setOnClickListener(this);
        ImageButton button_pause = view.findViewById(R.id.button_pause);
        button_pause.setOnClickListener(this);
        ImageButton button_stop = view.findViewById(R.id.button_stop);
        button_stop.setOnClickListener(this);
        ImageButton button_next = view.findViewById(R.id.button_next);
        button_next.setOnClickListener(this);
        ImageButton button_previous = view.findViewById(R.id.button_previous);
        button_previous.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button_play:
                getActivity().startService(new Intent(getActivity(), PlayerService.class));
                break;
            case R.id.button_pause:
                getActivity().stopService(new Intent(getActivity(), PlayerService.class));
                break;
            case R.id.button_next:
            {
                getActivity().stopService(new Intent(getActivity(), PlayerService.class));
                i++;
                if (i > 1)
                    i = 0;
                imageView.setImageResource(covers[i]);
            }
                break;
            case R.id.button_previous:
            {
                getActivity().stopService(new Intent(getActivity(), PlayerService.class));
                i--;
                if(i == 0)
                    i=0;
                imageView.setImageResource(covers[i]);
            }
            break;
        }
    }
}