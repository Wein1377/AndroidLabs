package com.mirea.zarin.mireaproject.practice9;

import android.widget.TextView;

import com.mirea.zarin.mireaproject.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class CustomMarkerInfoWindow extends MarkerInfoWindow
{
    public CustomMarkerInfoWindow(MapView mapView)
    {
        super(R.layout.info_window, mapView);
    }


    @Override
    public void onOpen(Object item)
    {
        Marker m = (Marker) item;


        TextView title = (TextView) mView.findViewById(R.id.address);
        title.setText(m.getTitle());

        TextView snippet = (TextView) mView.findViewById(R.id.establishmentDate);
        snippet.setText(m.getSnippet());

        TextView subdescription = (TextView) mView.findViewById(R.id.coordinates);
        subdescription.setText(m.getSubDescription());


    }
}
