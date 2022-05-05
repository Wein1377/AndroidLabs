package com.mirea.zarin.mireaproject.practice7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mirea.zarin.mireaproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class dogFragment extends Fragment
{
    WebView dogView;
    ImageButton refreshButton;

    private final String url = "https://dog.ceo/api/breeds/image/random";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dog, container, false);

        this.dogView = (WebView) view.findViewById(R.id.dogView);
        this.refreshButton = (ImageButton) view.findViewById(R.id.refreshButton);
        this.refreshButton.setOnClickListener(this::onClick);
        load();

        return view;
    }

    public void load()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null)
        {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkinfo != null && networkinfo.isConnected())
        {
            new DownloadPageTask().execute(url);
        } else {
            Toast.makeText(getActivity(), "Нет интернета", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view)
    {
        load();
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadPageTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
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
                JSONObject responseJson = new JSONObject(result);
                String dogURL = responseJson.getString("message");
                dogView.loadUrl(dogURL);

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