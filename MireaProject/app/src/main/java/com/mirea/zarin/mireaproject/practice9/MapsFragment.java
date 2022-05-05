package com.mirea.zarin.mireaproject.practice9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mirea.zarin.mireaproject.BuildConfig;
import com.mirea.zarin.mireaproject.R;
import com.mirea.zarin.mireaproject.practice6.StoryItem;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment
{

    private GoogleMap map;
    private ApiInterface apiInterface;
    private List<LatLng> polylinelist;
    private PolylineOptions polylineOptions;
    private LatLng origion, destenation;

    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {

        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            map = googleMap;
            map.getUiSettings().setZoomControlsEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng mainBuilding = new LatLng(55.670005, 37.479894);
            googleMap.addMarker(new MarkerOptions().position(mainBuilding).title("РТУ МИРЭА, г. Москва, " +
                    "Проспект Вернадского, д. 78"));

            LatLng chemistryBuilding = new LatLng(55.661445, 37.477049);
            googleMap.addMarker(new MarkerOptions().position(chemistryBuilding).title("РТУ МИРЭА, г. Москва," +
                    " Проспект Вернадского, д. 86"));

            LatLng strominkaBuilding = new LatLng(55.794259, 37.701448);
            googleMap.addMarker(new MarkerOptions().position(strominkaBuilding).title("РТУ МИРЭА, " +
                    "г. Москва,\n ул. Стромынка, д.20"));

            LatLng stavropol = new LatLng(45.049513, 41.912041);
            googleMap.addMarker(new MarkerOptions().position(stavropol).title("Ставропольский край\n" +
                    "г. Ставрополь,\n пр. Кулакова, д. 8"));

            LatLng phrazefo = new LatLng(55.966853, 38.050774);
            googleMap.addMarker(new MarkerOptions().position(phrazefo).title("Московская область," +
                    " г. Фрязино, ул. Вокзальная, д. 2а"));

            origion = new LatLng(55.75222,37.61556);
            destenation = new LatLng(54.74306,55.96779);
            getDirection("55.75222" + "37.61556" , "54.74306" + "55.96779");
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null)
        {
            mapFragment.getMapAsync(callback);

            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://maps.googleapis.com/").build();
            apiInterface = retrofit.create(ApiInterface.class);
        }
    }

    @SuppressLint("CheckResult")
    private void getDirection(String origin, String destination)
    {
        apiInterface.getDirection("driving","less driving", origin, destination,
                BuildConfig.GMP_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Result>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onSuccess(Result result)
                    {
                        polylinelist = new ArrayList<>();
                        List<Route> routeList = result.getRoutes();
                        for(Route route:routeList)
                        {
                            String polyline = route.getOverviewPolyLine().getPoints();
                            polylinelist.addAll(decodePoly(polyline));
                        }
                        polylineOptions = new PolylineOptions();
                        polylineOptions.color(ContextCompat.getColor(requireContext().getApplicationContext(), com.google.android.material.R.color.design_default_color_primary));
                        polylineOptions.width(8);
                        polylineOptions.startCap(new ButtCap());
                        polylineOptions.jointType(JointType.ROUND);
                        polylineOptions.addAll(polylinelist);
                        map.addPolyline(polylineOptions);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(origion);
                        builder.include(destenation);
                        map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),100));
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }
                });
    }

    private List<LatLng> decodePoly(String encoded)
    {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng =0;

        while (index < len)
        {
            int b , shift = 0, result = 0;
            do
            {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~ (result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)) , (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

}