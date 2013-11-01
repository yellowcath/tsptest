package com.example.tsptest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import android.view.Menu;

public class MainActivity extends Activity implements OnClickListener {

	//控件
	Button bt;
	
	//路径规划
	List<GeoPoint> tspPoints = new ArrayList<GeoPoint>();
	int distance[][];
	MKDrivingRouteResult routeResult[][];
	
	int pointsNum;
	int pointStart,pointEnd;
	static int getDisFlag=0;
	//百度地图
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	
	MKSearch mMKSearch = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init("1c51af644a15e0c5e43c82c362603fbc", null);  
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_main);
		
		//初始化控件
		bt = (Button) this.findViewById(R.id.button1);
		bt.setOnClickListener(this);
		
		mMapView=(MapView)findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		//设置启用内置的缩放控件
		MapController mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (30.756606 * 1e6),(int) (103.934588 * 1e6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(15);//设置地图zoom级别
		
		//路径检索
		mMKSearch = new MKSearch();
		mMKSearch.init(mBMapMan, mKSearchListener);//注意，MKSearchListener只支持一个，以最后一次设置为准

		//注册地图点击监听

		mMapView.regMapTouchListner(mapTouchListener);

	}
	
	 MKSearchListener mKSearchListener = new MKSearchListener(){

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int arg1) {

			Log.v("hwLog"," onGetDrivingRouteResult");
                if(result!=null)
                {
                	//找出是那两个点的数据返回

                	routeResult[pointStart][pointEnd]=routeResult[pointEnd][pointStart]= result;
                	distance[pointStart][pointEnd]=distance[pointEnd][pointStart]= result.getPlan(0).getDistance();
                	Log.v("hwLog",pointStart+"号与"+pointEnd+"号的距离为"+distance[pointEnd][pointStart]+"米");
                	getDisFlag = 2;
                
                }
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		
	};
	@Override
	public void onClick(View arg0) {


		switch(arg0.getId())
		{
			case R.id.button1:
				
				pointsNum = tspPoints.size();
				distance = new int[pointsNum][pointsNum];
				routeResult = new  MKDrivingRouteResult[pointsNum][pointsNum];
				
				
				Log.v("hwLog","pointsNum:"+pointsNum);
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						for(int i=0;i<pointsNum-1;i++)
						{
							for(int j=i+1;j<pointsNum;)
							{
		    					if(getDisFlag==0)
								{
		    						pointStart=i;pointEnd=j;
		    						MKPlanNode start,end;
									Log.v("hwLog","i:"+i+"  j:"+j+"    ");
									start = new MKPlanNode();
									start.pt = tspPoints.get(i);
									end = new MKPlanNode();
									end.pt = tspPoints.get(j);// 设置驾车路线搜索策略，时间优先、费用最少或距离最短
									mMKSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
									mMKSearch.drivingSearch(null, start, null, end);
									
									getDisFlag=1;
									
									//Log.v("hwLog","Flag0");
									
								}
								else if(getDisFlag==1)
								{
									//Log.v("hwLog","Flag1");
		                          continue;
								}
								else if(getDisFlag==2)
								{
									j++;
									getDisFlag=0;
									//Log.v("hwLog","Flag2");
								}
								
								
							}
						}
						
						Log.v("hwLog","测距完成");
						//路径规划 
						int[] solution = new TSP(pointsNum).calcuLines(distance);

						//展示路径
						 RouteOverlay routeOverlay;
						 BitmapDrawable d = (BitmapDrawable) MainActivity.this.getResources().getDrawable(R.drawable.icon_123);
						for(int k=0;k<tspPoints.size()-1;k++)
						{
					        routeOverlay = new RouteOverlay(MainActivity.this, mMapView);  
					        routeOverlay.setData(routeResult[solution[k]][solution[k+1]].getPlan(0).getRoute(0));
                            routeOverlay.setEnMarker(d);
                            routeOverlay.setStMarker(d);
					        mMapView.getOverlays().add(routeOverlay);
					        mMapView.refresh();
						}
						
				        routeOverlay = new RouteOverlay(MainActivity.this, mMapView);  
				        routeOverlay.setData(routeResult[solution[tspPoints.size()-1]][solution[0]].getPlan(0).getRoute(0));
                        routeOverlay.setEnMarker(d);
                        routeOverlay.setStMarker(d);
				        mMapView.getOverlays().add(routeOverlay);
				        mMapView.refresh();
					}
					
					
					
				}).start();

				

				break;
		}
		
	}
	
	//地图点击监听
	 MKMapTouchListener mapTouchListener = new MKMapTouchListener(){
			@Override
			public void onMapClick(GeoPoint point) {

				
				
				tspPoints.add(point);
				Toast.makeText(getApplicationContext(), "已选"+tspPoints.size()+"个点", Toast.LENGTH_SHORT).show();
				
							
			}

			@Override
			public void onMapDoubleClick(GeoPoint point) {
				//在此处理地图双击事件
			}

			@Override
			public void onMapLongClick(GeoPoint point) {
				//在此处理地图长按事件 
			}
		};
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
                mBMapMan.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
        super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


}

