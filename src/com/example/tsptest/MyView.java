package com.example.tsptest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MyView extends View {

    private TSP opt;
    int[] solution,path ;
    int points=200;
    
    private boolean drawpoints=false,drawlines=false;
	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void calPoints()
	{
		opt = new TSP(points);
		opt.init(getWidth(), getHeight());
		path = opt.greedy();
		
	}
	public void calLines()
	{
		//solution = opt.calcuLines(path);
	}
	public void showPoints(boolean bool)
	{
		drawpoints = bool;
	}
	public void showLints(boolean bool,TextView tv)
	{
		drawlines = bool;
		if(bool==true)
		tv.setText("points:"+points+"  len:"+opt.length+"  time:"+opt.time+"ms");
	}
	private Canvas drawPoints(Canvas canvas)
	{

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		

		
		for(int i=0;i<opt.cities;i++)
		{
			canvas.drawCircle(opt.X[i],opt.Y[i], 4, paint);
		}
		
		return canvas;
	}

	private void drawLine(Canvas canvas)
	{

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		
		
		for(int i=0;i<opt.cities-1;i++)
		{
			canvas.drawLine(opt.X[solution[i]], opt.Y[solution[i]], opt.X[solution[i+1]], opt.Y[solution[i+1]], paint);

		}
		
		canvas.drawLine(opt.X[solution[opt.cities-1]], opt.Y[solution[opt.cities-1]], opt.X[solution[0]],opt.Y[solution[0]], paint);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		if(drawpoints)
		 this.drawPoints(canvas);
		if(drawlines)
			this.drawLine(canvas);

	}
	/*
	@Override
	protected void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		
		TSP tsp = new TSP();
		tsp.init(getWidth(), getHeight());
		
		for(int i=0;i<tsp.cities;i++)
		{
			canvas.drawCircle(tsp.X[i],tsp.Y[i], 2, paint);
		}
		
		int solution[] = tsp.calcu();

		 
		
		for(int i=0;i<tsp.cities-1;i++)
		{
			canvas.drawLine(tsp.X[solution[i]], tsp.Y[solution[i]], tsp.X[solution[i+1]], tsp.Y[solution[i+1]], paint);
			 Log.v("hwLog",solution[i]+"");
		}
		
		canvas.drawLine(tsp.X[solution[tsp.cities-1]], tsp.Y[solution[tsp.cities-1]], tsp.X[solution[0]], tsp.Y[solution[0]], paint);
		//canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint); 
		//canvas.drawPoint(1, 1, paint);
		//canvas.drawPoint(11, 11, paint);

		//super.onDraw(canvas);
	}
*/
}
