package com.mirea.zarin.mireaproject.practice4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.View.OnClickListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mirea.zarin.mireaproject.R;

public class MusicFragment extends Fragment implements OnClickListener
{

    ImageView imageView;
    static int i = 0;
    static int[] songs = {R.raw.you_say_run, R.raw.gurenge};
    static int[] covers = {R.drawable.run_cover, R.drawable.gurenge};

    public static int GetSongId()
    {
        return songs[i];
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    ImageButton button_play;
    ImageButton button_pause;
    ImageButton button_next;
    ImageButton button_previous;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        imageView = view.findViewById(R.id.Cover);
        button_play = (ImageButton) view.findViewById(R.id.button_play);
        button_play.setOnClickListener(this);
        button_pause = (ImageButton) view.findViewById(R.id.button_pause);
        button_pause.setOnClickListener(this);
        button_next = (ImageButton) view.findViewById(R.id.button_next);
        button_next.setOnClickListener(this);
        button_previous = (ImageButton) view.findViewById(R.id.button_previous);
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
                getActivity().startService(new Intent(getActivity(), PlayerService.class));
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