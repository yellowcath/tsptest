package com.example.tsptest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TSP {
	
	public static int cities;//城市个数
	public int X[];//城市的坐标
	public int Y[];
	private int distance[][];
	
	private int[] solution;//最终方案
	public int length,time;
	
	class Node{
		
		int pos;
		Node next;
		Node prior;
	}
	
	
	
	public TSP(int cities)
	{
		this.cities = cities;
		
		X = new int[cities];
		Y = new int[cities];
		
		distance = new int[cities][cities];
		solution = new int[cities];
	}
	public  Node ToLinkList(int[] array)
	{
		Node head = null,node,p=null;
		for(int i=0;i<array.length;i++)
		{
			node = new Node();
			node.pos=array[i];
			if(head==null)
			{
				head=node;
				head.next=head;
				head.prior=head;
				p=head;
			}
			else
			{
				p.next=node;
				node.next=head;
				node.prior=p;
				p=node;
			}
			
		}
		head.prior=p;
		return head;
	}
	public int[] ToIntArray(Node head)
	{
		Node p=head;
		int[] array = new int[cities];
		for(int i=0;i<cities;i++)
		{
			array[i]=p.pos;
			p=p.next;
		}
		return array;
		
	}
	public int ArcCost(int a,int b)
	{
		return distance[a][b];
	}

	private int[] three_opt_move(int[] solution)
	{
		Node head = this.ToLinkList(solution);
		int cost,beforeCost;
		int count,index,pindex,sindex,j,k;
		int D1,D2,D3,D4;
		Node First,Last,Kth,Jth,Save,Reverse;
		
		beforeCost = this.calcuDis(solution);

		count = 1;
		First = head;
		

		do{
			Last = First.prior;
			Kth = First.next;
			do{
				Jth = Kth.next;
				do{
					D1 = this.ArcCost(Kth.pos,Jth.next.pos)
							+this.ArcCost(First.pos,Jth.pos);
					D2 = ArcCost(First.pos,Jth.next.pos)
							+ArcCost(Kth.pos,Jth.pos);
					D3 = ArcCost(Kth.next.pos,Last.pos);
					D4 = ArcCost(First.pos,Last.pos)
							+ArcCost(Kth.pos,Kth.next.pos)
							+ArcCost(Jth.pos,Jth.next.pos);
					
					if(((D1+D3)<D4) || ((D2+D3)<D4))
					{
						Last.next = Kth.next;
						Kth.next.prior = Last;
						if(D1<=D2)
						{
							Kth.next = Jth.next;
							Kth.next.prior=Kth;
							Jth.next=First;
							First.prior = Jth;
						}
						else
						{
							for(Reverse = First;Reverse!=Kth;Reverse=Save)
							{
							    Save = Reverse.next;
							    Reverse.next=Reverse.prior;
							    Reverse.prior=Save;
							}
							First.next = Jth.next;
							Jth.next.prior = First;
							Kth.next = Kth.prior;
							Kth.prior = Jth;
							Jth.next = Kth;
						}
						count = 0;
						First = Last.next;
						Kth = First.next;
						Jth = Kth.next;
					}
					else
					{
						Jth = Jth.next;
					}
				  
				}while((Jth!=Last.prior) && (count != 0));
				Kth = Kth.next;
			}while((Kth != Last.prior.prior.prior) && (count != 0));
			if(count != 0)
			{
				First = First.next;
			}
			count++;
		}while(count<cities);
		Last = First.prior;
		cost = ArcCost(Last.pos,First.pos);
		for(Kth = First;Kth != Last; Kth=Kth.next)
		{
			cost += ArcCost(Kth.pos,Kth.next.pos);
		}
		System.out.printf("\nbefore:%d,after:%d\n",beforeCost,cost);
		if(cost < beforeCost)
			return this.ToIntArray(head);
		else 
			return solution;
		
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
	
	public int[] greedy()//随机贪心算法
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
		  System.out.printf("\ndis:%d,Solution:",this.calcuDis(solution));
		  for(int i=0;i<cities;i++)
			  System.out.printf("%5d", solution[i]);
		  return solution;
		  
	}
	
	private void Best3OptSwap(int[] solution)
	{
		int i,j,k;
		int curdis,newdis;
		boolean gotBetter = false;
		for(i=0;i<cities-3;i++)
		{
			for(j=i+1;j<cities-2;j++)
			{
				for(k=j+1;k<cities-1;k++)
				{
	
					if(three_opt_swap(solution,i,j,k))
						gotBetter = true;
				}
			}
			
			if(gotBetter)
			{
				i=0;
				gotBetter=false;
				continue;
			}
		}
	}
	private boolean three_opt_swap(int[] solution,int i,int j,int k)
	{
		int dis,tempdis;
		int flag=0;
		int[] temp1 = new int[cities];
		int[] temp2 = new int[cities];
		int[] temp3 = new int[cities];
		
		System.arraycopy(solution, 0, temp1,0, cities);
		System.arraycopy(solution, 0, temp2,0, cities);
		System.arraycopy(solution, 0, temp3,0, cities);
        
		dis = this.calcuDis(solution);


		//swap(i+1,j) (j+1,k)
		
		  swap(temp1,i+1,j);
		  swap(temp1,j+1,k);
		  tempdis = this.calcuDis(temp1);


		if(tempdis<dis)
		{
			flag=1;
			dis=tempdis;
		}
		
		//swap(i+1,j+1) swap(j,k) swap(j+1,k)
		  swap(temp2,i+1,j+1);
		  swap(temp2,j,k);
		  swap(temp2,j+1,k);
		  tempdis = this.calcuDis(temp2);

		if(tempdis<dis)
		{
			flag=2;	
			dis=tempdis;

		}

		//swap(k,i+1) (j,j+1) (j+1,k)
		  swap(temp3,k,i+1);
		  swap(temp3,j,j+1);
		  swap(temp3,j+1,k);	
		tempdis=this.calcuDis(temp3); 
		if(tempdis<dis)
		{
			flag=3;	
			dis=tempdis;

		}
       
		switch(flag)
		{
		  case 0:
			  break;
		  case 1:
			  System.arraycopy(temp1, 0,solution,0, cities);
			  break;
		  case 2:
			  System.arraycopy(temp2, 0,solution,0, cities);
			  break;
		  case 3:
			  System.arraycopy(temp3, 0,solution,0, cities);			  
			  break;
		}
		if(flag!=0)
		{
			 System.out.printf("\ndis:%d:",dis);
		for(i=0;i<cities;i++)
			System.out.printf("%5d",solution[i]);
		  return true;
		
		}
		return false;
		
	}
	private static void swap(int[] solution,int a,int b)
	{
		int t;
		t=solution[a];
		solution[a]=solution[b];
		solution[b]=t;
	}
	private int calDis(int a,int b,int c,int d,int e,int f)
	{
		return distance[a][b]+distance[c][d]+distance[e][f];
	}
	
	private void three_opt()
	{
		int a,b,t; //交换a,b
		int N=cities*cities;//循环次数
		int minDis,curDis;
		int[] temp = new int[cities];
		boolean flag = false;//终止循环条件

		
	
		
        //计算路径
		minDis = calcuDis(solution);
		System.out.printf("\nminDis:%d",minDis);
		while(true)
		{
			for(int i=0;i<cities-2;i++)
			{
				for(int k=i+1;k<cities-1;k++)
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
					i=0;
					flag=false;
					System.out.printf("找到更好的结果了，重新循环,len:%d\n",minDis);
					continue;
				}
			}
			
			if(flag==false)
			{
				System.out.printf("没有更好的结果了，退出循环,len:%d\n",minDis);
				break;
			}
		}

		
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
	
	public int[] calcuLines(int[][] distance)
	{
		long s,e;
        int[] path = new int[cities];
        for(int i=0;i<cities;i++)
        	path[i]=i;
        System.out.printf("\n开始3opt");
		s=System.currentTimeMillis();
		solution = this.three_opt_move(path);
		e=System.currentTimeMillis();
		
		System.out.printf("\n3opt耗时:%dms",e-s);
		time = (int) (e-s);
		length = this.calcuDis(solution);
		return solution;
	}
	public static void main(String[] args)
	{
		long s,e;
		

         
		TSP tsp = new TSP(140);
		tsp.init(1000,2000);
		int[] sol = tsp.greedy();
		int[] temp = new int[sol.length];
				System.arraycopy(sol,0, temp,0,sol.length);
				s=System.currentTimeMillis();
        tsp.three_opt_move(sol);
        e=System.currentTimeMillis();


		
		System.out.printf("\n%dms\n",e-s );
	}

}