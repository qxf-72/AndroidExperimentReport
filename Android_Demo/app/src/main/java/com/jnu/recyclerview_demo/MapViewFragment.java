package com.jnu.recyclerview_demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jnu.recyclerview_demo.data.DataDownload;
import com.jnu.recyclerview_demo.data.MyLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TextureMapView;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewFragment extends Fragment {

    private TextureMapView mapView = null;
    private ArrayList<MyLocation> locations = new ArrayList<>();
    private TencentMap tencentMap = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public class DataDownloadTask extends AsyncTask<String, Void, String> {

        // 线程任务
        @Override
        protected String doInBackground(String... urls) {
            return new DataDownload().download(urls[0]);
        }

        // 任务完成后的回调函数
        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            if (content == null)
                return;

            locations = new DataDownload().parseJsonObjects(content);
            for (int i = 0; i < locations.size(); i++) {
                LatLng position = new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude());
                tencentMap.addMarker(new MarkerOptions(position)
                        .title(locations.get(i).getName())
                        .snippet(locations.get(i).getMemo())
                );
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        tencentMap = mapView.getMap();
        tencentMap.setMapType(TencentMap.MAP_TYPE_SATELLITE);

        LatLng init_position = new LatLng(22.250093, 113.534267); //地图中心点坐标(暨南大学珠海校区)
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        init_position,   //坐标
                        14,         //缩放级别
                        45f,         //目标倾斜角
                        0f)         //目标旋转角
                );
        //移动地图
        tencentMap.moveCamera(cameraSigma);

        DataDownloadTask dataDownloadTask = new DataDownloadTask();
        dataDownloadTask.execute("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }


    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }
}