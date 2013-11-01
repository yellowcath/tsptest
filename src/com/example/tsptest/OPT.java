package com.example.tsptest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;


public class OPT {

	public static int cities;//���и���
	public int X[];//���е�����
	public int Y[];
	private int distance[][];
	public int length;//·���ܳ�
	public int time;//��ʱ
	
	private static int[] solution;//���շ���
	
	
	public OPT(int cities)
	{
		this.cities = cities;
		
		X = new int[cities];
		Y = new int[cities];
		
		distance = new int[cities][cities];
		solution = new int[cities];
	}
	//�������cities������ֱ�ӵ��໥����
	public void init(int width,int height)//���ɳ�������ķ�Χ
	{
		
		Random rd = new Random();
		
		//�������cities�����е�����
		for(int i=0;i<cities;)
		{
			int x,y,flag;
			x=rd.nextInt(width);
			y=rd.nextInt(height);
			//����ظ�
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
		//��ӡ�������
		/*
		System.out.println("���еľ����������");
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
	
	private void greedy()//���̰���㷨
	{
		  //���ѡ��һ����ʼ����
		  Random rd = new Random();
		  
		  //��ѡ�����б�
		  List<Integer> cityList = new ArrayList<Integer>();
		  //��ʼ���б�
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
			  //ѡ�����ϸ���������ĳ���
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
		  //���
		  System.out.println("\nSolution:");
		  for(int i=0;i<cities;i++)
			  System.out.printf("%3d", solution[i]);
		  
	}
	
	private int two_opt()
	{
		int i,k=0;
		int a,b,t; //����a,b
		int N=cities*cities;//ѭ������
		int minDis,curDis;
		int[] temp = new int[cities];
		boolean flag = false;//��ֹѭ������
		Random rd = new Random();
		
	
		
        //����·��

		while(true)
		{
			minDis = calcuDis(solution);
			//System.out.printf("\nminDis:%d\n",minDis);
			for(i=0;i<cities-2;i++)
			{
				for(k=i+1;k<cities-1;k++)
				{
					//i,k+1����·������
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
            	//System.out.printf("�ҵ����õĽ���ˣ�����ѭ��,len:%d\n",minDis);
            	continue;
            }
			if(flag==false)
			{
				System.out.printf("\nû�и��õĽ���ˣ��˳�ѭ��,len:%d\n",minDis);
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
		
		//Log.v("hwLog","·���ܳ�:"+minDis+"   ��ʱ:"+(e-s)+"ms");
		//System.out.printf("\n��ʱ:%dms",e-s);
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
		System.out.printf("\n��ʱ:%dms",e-s);
		
	}
}
