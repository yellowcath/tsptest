package com.example.tsptest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;


public class OPT {

	public static int cities;//城市个数
	public int X[];//城市的坐标
	public int Y[];
	private int distance[][];
	public int length;//路径总长
	public int time;//耗时
	
	private static int[] solution;//最终方案
	
	
	public OPT(int cities)
	{
		this.cities = cities;
		
		X = new int[cities];
		Y = new int[cities];
		
		distance = new int[cities][cities];
		solution = new int[cities];
	}
	//随机产生cities个城市直接的相互距离
	public void init(int width,int height)//生成城市坐标的范围
	{
		
		Random rd = new Random();
		
		//随机生成cities个城市的坐标
		for(int i=0;i<cities;)
		{
			int x,y,flag;
			x=rd.nextInt(width);
			y=rd.nextInt(height);
			//检查重复
			flag=0;
			for(int j=0;j<i;j++)
			{
				//if(X[j]==x && Y[j]==y)
				if(X[j]-x<5 && X[j]-x>-5 && Y[j]-y<5 && Y[j]-y>-5)
				{
					flag=1;
					break;
				}
			}
			if(flag==1)
				continue;
			else
			{
				X[i]=x;
				Y[i]=y;
				i++;
				
			}
			
		}

		for(int i=0;i<cities;i++)
		{
			for(int j=i+1;j<cities;j++)
			{
				//distance[i][j]=rd.nextInt(100);
				distance[i][j] = (int) Math.sqrt(Math.pow(X[i]-X[j],2)+Math.pow(Y[i]-Y[j], 2));
				distance[j][i]=distance[i][j];
			}
		}
		//打印距离矩阵
		/*
		System.out.println("城市的距离矩阵如下");
		for(int i=0;i<cities;i++)
		{
			for(int j=0;j<cities;j++)
			{
				System.out.printf("%5d",distance[i][j]);
				//System.out.printf("\n");				
			}
			System.out.printf("\n");	
			
		}
		*/
		
	}
	
	private void greedy()//随机贪心算法
	{
		  //随机选出一个初始城市
		  Random rd = new Random();
		  
		  //待选城市列表
		  List<Integer> cityList = new ArrayList<Integer>();
		  //初始化列表
		  for(int i=0;i<cities;i++)
		  {
			  cityList.add(i);
		  }
		  
		  solution[0]=rd.nextInt(cities);
		  cityList.remove(solution[0]);
		  
		  int minDisIndex;
		  int minDis;
		  for(int i=1;i<cities;i++)
		  {
			  //选择离上个城市最近的城市
			  minDisIndex = 0;
			  minDis = distance[cityList.get(minDisIndex)][solution[i-1]];			  
			  for(int j=1;j<cityList.size();j++)
			  {
				  if(distance[cityList.get(j)][solution[i-1]]<minDis)
				  {
					  minDis = distance[cityList.get(j)][solution[i-1]];
					  minDisIndex=j;
				  }
			  }
			  solution[i] = cityList.get(minDisIndex);
			  cityList.remove(minDisIndex);
			  
		  }
		  //输出
		  System.out.println("\nSolution:");
		  for(int i=0;i<cities;i++)
			  System.out.printf("%3d", solution[i]);
		  
	}
	
	private int two_opt()
	{
		int i,k=0;
		int a,b,t; //交换a,b
		int N=cities*cities;//循环次数
		int minDis,curDis;
		int[] temp = new int[cities];
		boolean flag = false;//终止循环条件
		Random rd = new Random();
		
	
		
        //计算路径

		while(true)
		{
			minDis = calcuDis(solution);
			//System.out.printf("\nminDis:%d\n",minDis);
			for(i=0;i<cities-2;i++)
			{
				for(k=i+1;k<cities-1;k++)
				{
					//i,k+1进行路径交换
					temp = two_opt_swap(solution,i,k);
					curDis = calcuDis(temp);
					if(curDis<minDis)
					{
						minDis=curDis;
						System.arraycopy(temp, 0, solution,0,cities);
						flag = true;
						break;
					}
				
				}
				if(flag==true)
				{
					break;
				}
			}
            if(flag==true)
            {
            	flag=false;
            	//System.out.printf("找到更好的结果了，重新循环,len:%d\n",minDis);
            	continue;
            }
			if(flag==false)
			{
				System.out.printf("\n没有更好的结果了，退出循环,len:%d\n",minDis);
				break;
			}
		}

		return minDis;
	}
	
	private int[] two_opt_swap(int[] route,int a,int b)
	{
		int[] temp = new int[cities];
		int i,j;
		for(i=0;i<a;i++)
		{
			temp[i]=route[i];
		}
		for(j=b;j>=a;j--)
		{
			temp[i++]=route[j];
		}
		for(;i<cities;i++)
		{
			temp[i]=route[i];
		}
		return temp;
	}
	private int calcuDis(int[] s)
	{
		int dis=0;
		
		for(int i=0;i<cities-1;i++)
		{
			dis+=distance[s[i]][s[i+1]];
		}
		
		dis+=distance[s[cities-1]][s[0]];
		
		return dis;
	}
	
	public int[] calcuLines()
	{
		long s,e;
		s=System.currentTimeMillis();
		this.greedy();		
		length = this.two_opt();
		e=System.currentTimeMillis();
		time = (int) (e-s);
		
		//Log.v("hwLog","路径总长:"+minDis+"   耗时:"+(e-s)+"ms");
		//System.out.printf("\n耗时:%dms",e-s);
		return solution;
	}
	public static void main(String[] args)
	{
		long s,e;
		
		OPT opt = new OPT(100);
		opt.init(1000,2000);
		
		opt.greedy();
		s=System.currentTimeMillis();
		opt.two_opt();
		e=System.currentTimeMillis();
		
		for(int i=0;i<cities;i++)
			System.out.println(solution[i]);
		System.out.printf("\n耗时:%dms",e-s);
		
	}
}
