package com.mirea.zarin.mireaproject.practice9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mirea.zarin.mireaproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MapFragment extends Fragment implements View.OnClickListener
{
    MapView map = null;
    IMapController mapController;

    Button setPointsButton;
    Button drawRouteButton;
    EditText startPointEdit;
    EditText destinationPointEdit;
    double startLat;
    double startLon;
    double destinationLat;
    double destinationLon;

    private boolean issr = false;
    private boolean isdr = false;

    private final String KEY = "1accd158b9889187604c5671a272a5ba";
    private String startCity;
    private String destinationCity;

    private String urlStart;
    private String urlDestination;

    private static final String NAME_MSK_78 = "РТУ МИРЭА - Главный кампус";
    private static final String NAME_MSK_86 = "РТУ МИРЭА - МИТХТ";
    private static final String NAME_MSK_20 = "РТУ МИРЭА - МГУПИ";
    private static final String NAME_FRZ = "РТУ МИРЭА - Филиал в Фрязино";
    private static final String NAME_STV = "РТУ МИРЭА - Филиал в Ставрополе";

    private static final String ADDRESS_MSK_78 = "Москва, просп. Вернадского, 78, стр. 4";
    private static final String ADDRESS_MSK_86 = "Москва, просп. Вернадского, 86";
    private static final String ADDRESS_MSK_20 = "Москва, ул. Стромынка, 20";
    private static final String ADDRESS_FRZ = "Фрязино, ул. Вокзальная, 2А, корп. 61";
    private static final String ADDRESS_STV = "Ставрополь, просп. Кулакова, 8, лит.А";

    private static final String ESTABLISHMENT_MSK_78 = "28 мая 1947 г.";
    private static final String ESTABLISHMENT_MSK_86 = "1 июля 1900 г.";
    private static final String ESTABLISHMENT_MSK_20 = "16 сентября 1936 г.";
    private static final String ESTABLISHMENT_FRZ = "19 сентярбря 1962 г.";
    private static final String ESTABLISHMENT_STV = "18 декабря 1996 г.";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        startPointEdit = (EditText) view.findViewById(R.id.startPointEdit);
        destinationPointEdit = (EditText) view.findViewById(R.id.destinationPointEdit);
        setPointsButton = (Button) view.findViewById(R.id.setPointsButton);
        setPointsButton.setOnClickListener(this);
        drawRouteButton = (Button) view.findViewById(R.id.drawRouteButton);
        drawRouteButton.setOnClickListener(this);

        Context ctx = requireContext().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map =(MapView)view.findViewById(R.id.mapView);
        mapController = map.getController();
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController.setZoom((long) 14);
        GeoPoint startPoint = new GeoPoint(55.75222, 37.61556);
        mapController.animateTo(startPoint);

        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(requireContext(),10,10);
        map.getOverlays().add(myScaleBarOverlay);

        GeoPoint COORDINATES_MSK_78 = new GeoPoint(55.669986, 37.480409);
        GeoPoint COORDINATES_MSK_86 = new GeoPoint(55.661445, 37.477049);
        GeoPoint COORDINATES_MSK_20 = new GeoPoint(55.794259, 37.701448);
        GeoPoint COORDINATES_FRZ = new GeoPoint(55.966887, 38.050533);
        GeoPoint COORDINATES_STV = new GeoPoint(45.052213, 41.912660);

        Marker MSK_78 = new Marker(map);
        MSK_78 = addMarker(COORDINATES_MSK_78, NAME_MSK_78+" \n"+ADDRESS_MSK_78,  ESTABLISHMENT_MSK_78, COORDINATES_MSK_78 );

        Marker MSK_86 = new Marker(map);
        MSK_86 = addMarker(COORDINATES_MSK_86, NAME_MSK_86+" \n"+ADDRESS_MSK_86, ESTABLISHMENT_MSK_86, COORDINATES_MSK_86);

        Marker MSK_20 = new Marker(map);
        MSK_20 = addMarker(COORDINATES_MSK_20, NAME_MSK_20+" \n"+ADDRESS_MSK_20, ESTABLISHMENT_MSK_20, COORDINATES_MSK_20 );

        Marker FRZ = new Marker(map);
        FRZ = addMarker(COORDINATES_FRZ, NAME_FRZ+" \n"+ADDRESS_FRZ, ESTABLISHMENT_FRZ, COORDINATES_FRZ);

        Marker STV = new Marker(map);
        STV = addMarker(COORDINATES_STV, NAME_STV+" \n"+ADDRESS_STV,ESTABLISHMENT_STV, COORDINATES_STV);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public Marker addMarker(GeoPoint p, String title, String subTitle, GeoPoint coordinates) {
        Marker marker = new Marker(map);
        marker = new Marker(map);
        marker.setPosition(p);
        map.getOverlays().add(marker);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_menu_place));
        marker.setTitle(title);
        marker.setSnippet(subTitle);
        marker.setSubDescription(String.valueOf(coordinates));
        marker.setInfoWindow(new CustomMarkerInfoWindow(map));
        marker.setInfoWindowAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_TOP);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker m, MapView arg1)
            {
                m.showInfoWindow();
                return true;
            }
        });
        return marker;
    }

    public void setStartPointLat(double point)
    {
        this.startLat = point;
    }

    public void setStartPointLon(double point)
    {
        this.startLon = point;
    }

    public void setDestinationPointLat(double point)
    {
        this.destinationLat = point;
    }

    public void setDestinationPointLon(double point)
    {
        this.destinationLon = point;
    }

    public double getStartPointLat()
    {
        return startLat;
    }

    public double getStartPointLon()
    {
        return startLon;
    }

    public double getDestinationPointLat()
    {
        return destinationLat;
    }

    public double getDestinationPointLon()
    {
        return destinationLon;
    }

    public void setStartCity(String startC)
    {
        this.startCity = startC;
    }

    public void setDestinationCity(String destinationC)
    {
        this.destinationCity = destinationC;
    }

    public String getStartCity()
    {
        return startCity;
    }

    public String getDestinationCity()
    {
        return destinationCity;
    }

    public void setStartUrl()
    {
        urlStart = "https://api.openweathermap.org/geo/1.0/direct?q=" + getStartCity() + "&appid=" + KEY;
    }
    public void setDestinationUrl()
    {
        urlDestination = "https://api.openweathermap.org/geo/1.0/direct?q=" + getDestinationCity() + "&appid=" + KEY;
    }

    public String getUrlStart()
    {
        return urlStart;
    }

    public String getUrlDestination()
    {
        return urlDestination;
    }

    public GeoPoint getZoom()
    {
        GeoPoint zoomPoint = new GeoPoint(getStartPointLat(),getStartPointLon());
        return zoomPoint;
    }

    public void drawRoute()
    {
        RoadManager roadManager = new OSRMRoadManager(requireContext(), "MY_USER_AGENT");

        GeoPoint startRoute = new GeoPoint(getStartPointLat(), getStartPointLon());
        GeoPoint destinationRoute = new GeoPoint(getDestinationPointLat(), getDestinationPointLon());

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();

        waypoints.add(startRoute);
        waypoints.add(destinationRoute);

        Road road = roadManager.getRoad(waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);

        roadOverlay.setWidth(10);

        map.getOverlays().add(roadOverlay);
        map.invalidate();

        Log.i("aaaaaaaaaaaa", String.valueOf(waypoints.size()));

        waypoints.clear();

        Log.i("aaaaaaaaaaaa", String.valueOf(waypoints.size()));

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.setPointsButton:
                setStartCity(startPointEdit.getText().toString());
                setStartUrl();
                setDestinationCity(destinationPointEdit.getText().toString());
                setDestinationUrl();
                new getStartCityCoordinates().execute(getUrlStart());
                new getDestinationCityCoordinates().execute(getUrlDestination());
                map.getOverlays().clear();
                break;
            case R.id.drawRouteButton:
                drawRoute();
                mapController.animateTo(getZoom());
                mapController.setZoom((long) 14);
                break;
        }
    }

    private class getStartCityCoordinates extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                return downloadIpInfo(urls[0]);
            } catch (IOException e)
            {
                e.printStackTrace();
                return "error";
            }
        }
        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                JSONArray responseJson = new JSONArray(result);
                int length = responseJson.length();
                for(int i=0; i<length; i++)
                {
                    JSONObject jsonObj = responseJson.getJSONObject(i);
                    double start_lat = jsonObj.getDouble("lat");
                    double start_lon = jsonObj.getDouble("lon");
                    setStartPointLat(start_lat);
                    setStartPointLon(start_lon);
                    Toast.makeText(requireContext(), "done 1", Toast.LENGTH_LONG).show();
                    issr = true;
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getDestinationCityCoordinates extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                return downloadIpInfo(urls[0]);
            } catch (IOException e)
            {
                e.printStackTrace();
                return "error";
            }
        }
        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                JSONArray responseJson = new JSONArray(result);
                int length = responseJson.length();
                for(int i=0; i<length; i++)
                {
                    JSONObject jsonObj = responseJson.getJSONObject(i);
                    double destination_lat = jsonObj.getDouble("lat");
                    double destination_lon = jsonObj.getDouble("lon");
                    setDestinationPointLat(destination_lat);
                    setDestinationPointLon(destination_lon);
                    Toast.makeText(requireContext(), "done 2", Toast.LENGTH_LONG).show();
                    isdr = true;
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    private String downloadIpInfo(String address) throws IOException
    {
        InputStream inputStream = null;
        String data = "";
        try
        {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            { // 200 OK
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = inputStream.read()) != -1)
                {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();
                data = new String(result);
            }
            else
            {
                data = connection.getResponseMessage() + " . Error Code : " + responseCode;
            }
            connection.disconnect();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        return data;
    }
}