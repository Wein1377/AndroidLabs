package com.mirea.zarin.mireaproject.practice8;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirea.zarin.mireaproject.MainActivity;
import com.mirea.zarin.mireaproject.R;
import com.mirea.zarin.mireaproject.practice3.Web;
import com.mirea.zarin.mireaproject.practice8.db.Photos;
import com.mirea.zarin.mireaproject.practice8.db.PhotosDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class photoDBFragment extends Fragment
{
    private LinearLayout linearLayout;
    private FloatingActionButton floatingActionButton;

    private final String url = "https://dog.ceo/api/breeds/image/random";
    private String urll = "";

    public static boolean getImageFinished = false;

    private PhotosDao photosDao;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        photosDao = MainActivity.db.photosDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        this.linearLayout = view.findViewById(R.id.photosLinearLayout);
        this.floatingActionButton = view.findViewById(R.id.addPhoto);

        this.floatingActionButton.setOnClickListener(this::addPhoto);

        loadData();

        return view;
    }


    private void addPhoto(View view)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null)
        {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkinfo != null && networkinfo.isConnected())
        {
            new getImageUrl().execute(url);
            new ImageBitmap().execute(url);
        } else {
            Toast.makeText(getActivity(), "Нет интернета", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearView()
    {
        this.linearLayout.removeAllViewsInLayout();
    }

    public void loadData()
    {
        clearView();

        List<Photos> photos = photosDao.getAll();
        Collections.reverse(photos);

        for (Photos photo : photos)
        {
            Bitmap bitmap = BitmapUtility.getImage(photo.photo);

            ImageView imageView = new ImageView(requireContext());
            imageView.setImageBitmap(bitmap);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 1000);
            layoutParams.setMargins(0, 10, 0, 10);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

            imageView.setLayoutParams(layoutParams);

            this.linearLayout.addView(imageView);
        }
    }

    private class getImageUrl extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                getImageFinished = false;
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
                setURL(dogURL);
                getImageFinished = true;
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    private class ImageBitmap extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            while (!getImageFinished)
            {
                try
                {
                    Thread.sleep(1000);
                    Log.d("AAAAAAAA","wait");
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
                Bitmap bitmap = null;
                try
                {
                    InputStream input = new java.net.URL(getURL()).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    byte[] bytes = BitmapUtility.getBytes(bitmap);
                    photosDao.insert(new Photos(bytes));

                    return downloadIpInfo(urls[0]);
                }
                catch (IOException e)
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
                loadData();

            } catch (Exception e)
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

    public void setURL(String urll)
    {
        this.urll = urll;
    }

    public String getURL()
    {
        return urll;
    }

}