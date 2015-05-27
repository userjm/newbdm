package com.example.newbmloc;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

public class Appppppppp extends Application {
	public TextView mLocationResult;
	
	public LocationClient mLocationClient;
	
	@Override
	public void onCreate() {
		super.onCreate();
//		mLocationClient = new LocationClient(this.getApplicationContext());
//		mLocationClient.registerLocationListener(new MyLocationListener());
//		SDKInitializer.initialize(getApplicationContext());
	}

	
	
	
	
	
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

	

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
//			logMsg(sb.toString());
			
			
			if (mLocationResult != null)
				mLocationResult.setText(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}


	}

}
