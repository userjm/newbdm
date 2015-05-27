package com.example.newbmloc;

import java.util.logging.Handler;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
//import com.example.shishibdm.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.R.drawable;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {
	
	@ViewInject(R.id.bmapView)
	MapView mMapView;

	@ViewInject(R.id.result)
	public TextView tv;
	@ViewInject(R.id.CB)
	public CheckBox cb;
	@ViewInject(R.id.start)
	public Button b;
	@ViewInject(R.id.wei)
	public View wei;
	@ViewInject(R.id.re)
	public View re;
	@ViewInject(R.id.jiao)
	public View jiao;
	@ViewInject(R.id.startstart)
	public View start2;
	
	private Drawable dwei;
	private Drawable dre;
	private Drawable djiao;
	private Drawable dstart2;
	private Drawable dcb;

	
	private boolean makeThreadRun=true;
	
	private LocationClient lc;

	private BaiduMap mBaiduMap;

	private Thread thread=new Thread(){
		public void run() {
			
			
			while(makeThreadRun){
				
			}
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
		// ע��÷���Ҫ��setContentView����֮ǰʵ��
		SDKInitializer.initialize(getApplicationContext());
		

		setContentView(R.layout.activity_main);

		ViewUtils.inject(this);
		// ((Appppppppp)getApplication()).mLocationResult=tv;
		// lc=((Appppppppp)getApplication()).mLocationClient;

		dwei=wei.getBackground();
		dre=re.getBackground();
		djiao=jiao.getBackground();
		dstart2=start2.getBackground();
		dcb=cb.getBackground();
		
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// ��ͨ
		lc = new LocationClient(this.getApplicationContext());
		lc.registerLocationListener(new MyLocationListener());

	}
	
	
	
	void showLocPoint(LatLng loc, int drawable) {
		BitmapDescriptor bitmap;
		bitmap = BitmapDescriptorFactory.fromResource(drawable == 0 ? R.drawable.ic_launcher : drawable);
		// ����Markerͼ��

		// ����MarkerOption�������ڵ�ͼ�����Marker
		OverlayOptions option = new MarkerOptions().position(loc).icon(bitmap);
		// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.addOverlay(option);
	}

	void showLocPoint(LatLng loc) {
		showLocPoint(loc, 0);
	}
	
	
	

	@OnClick({R.id.start,R.id.wei,R.id.re,R.id.jiao,R.id.startstart})
	public void bClick(View v) {
		
		
		switch (v.getId()) {
		
				
		case R.id.startstart:
			if (start2.getBackground().equals(dstart2)) {
				start2.setBackground(dcb);
				makeThreadRun=true;
				thread.start();
			} else {
				start2.setBackground(dstart2); 
				makeThreadRun=false;

			}
			
			break;
		

		case R.id.start:
			InitFindMyLocation();
			if (b.getText().equals("on")) {
				lc.start();
				b.setText("off");
			} else {
				lc.stop();
				tv.setText("");
				b.setText("on");
			}
			
			break;

		case R.id.wei:
			if (wei.getBackground().equals(dwei)) {
				wei.setBackground(dcb);
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//����
			} else {
				wei.setBackground(dwei); 
				mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// ��ͨ
			}
			
			break;
		case R.id.re:
			if (re.getBackground().equals(dre)) {
				re.setBackground(dcb);
				mBaiduMap.setBaiduHeatMapEnabled(true);// ����ͼ
			} else {
				re.setBackground(dre); 
				mBaiduMap.setBaiduHeatMapEnabled(false);// ����ͼ
			}
			
			break;
		case R.id.jiao:
			if (jiao.getBackground().equals(djiao)) {
				jiao.setBackground(dcb);
				mBaiduMap.setTrafficEnabled(true);// ��ͨ״��
			} else {
				jiao.setBackground(djiao); 
				mBaiduMap.setTrafficEnabled(false);// ��ͨ״��
			}
			
			break;
		}

	}
	
	
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        }  

	private void InitFindMyLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ//Hight_Accuracy//Battery_Saving//Device_Sensors
															// ����
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵ//bd09ll//gcj02//bd09
										// //����ϵ
		option.setScanSpan(500);// ���÷���λ����ļ��ʱ��Ϊ500ms
		option.setIsNeedAddress(cb.isChecked());
		lc.setLocOption(option);

	}

	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			Toast.makeText(MainActivity.this, "onReceiveLocation", 0).show();
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
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			// logMsg(sb.toString());

			if (tv != null)
				tv.setText(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
			
			
			
			showLocPoint(new LatLng(location.getLatitude(), location.getLongitude()));
			
			
			
			
		}

	}

}
