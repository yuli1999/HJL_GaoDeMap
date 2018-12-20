package com.umeng.soexample.hjl_map;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class MainActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    private MapView mapView;
    private MyLocationStyle myLocationStyle;
    private MapView mMap;
    private AMap aMap;
    private TextView txt;
    private GeocodeSearch geocodeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.map);
       txt =  findViewById(R.id.txt);

        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();

//        初始化定位蓝点样式类LOCATION_TYPE_LOCATION_ROTATE
        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();

        //定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);

        //连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（默认1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);

        // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        // myLocationStyle.interval(2000);

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置为true表示启动显示定位蓝点
        aMap.setMyLocationEnabled(true);


        //监听
        aMap.setOnMyLocationChangeListener(this);

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);

    }


    @Override
    public void onMyLocationChange(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLonPoint latLonPoint=new LatLonPoint(latitude,longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint,200,GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        RegeocodeQuery regeocodeQuery = regeocodeResult.getRegeocodeQuery();

        String country = regeocodeAddress.getCountry();
        String formatAddress = regeocodeAddress.getFormatAddress();
        String city = regeocodeAddress.getCity();
        txt.setText(formatAddress.toString());

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
