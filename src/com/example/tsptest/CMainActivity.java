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
import android.widget.TextView;
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
/*
public class MainActivity extends Activity implements OnClickListener {

	//控件
	Button bt,bt2;
	MyView myView;
    TextView tv;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		//初始化控件
		bt = (Button) this.findViewById(R.id.button1);
		bt2 = (Button) this.findViewById(R.id.button2);
		tv = (TextView) this.findViewById(R.id.textView1);
		bt.setOnClickListener(this);
		bt2.setOnClickListener(this);
		
		myView = (MyView) this.findViewById(R.id.myView);
		
		



	}
	

	@Override
	public void onClick(View arg0) {


		switch(arg0.getId())
		{
			case R.id.button1:
				myView.calLines();
				myView.showLints(true,tv);
				myView.invalidate();

				break;
			case R.id.button2:
				   myView.calPoints();
				   myView.showPoints(true);
				   myView.showLints(false,tv);
				   myView.invalidate();
				   
				break;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


}
*/