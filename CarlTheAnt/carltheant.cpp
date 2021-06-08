#include<stdio.h>
#include<string.h>  
#include<stdlib.h>
struct info
{
	int x,y,len,wait,dir;
};
const int MaxN=50;
const int MaxM=100;
const int MaxL=250;
const int Add[4][2]={-1,0, 0,1, 1,0, 0,-1};
int N,cases,n,m,D,Rs,fin,sta,T,ex,ey;

int route[MaxN*MaxL];
int map[MaxL+1][MaxL+1];
int ants[MaxL+1][MaxL+1][4];

int list[MaxM];
info ant[MaxM];

void init()
{
	int i,j,k,t,x1,y1,x2,y2;
	scanf("%d %d %d",&n,&m,&D);
	x1=0;
	y1=0;
	Rs=0;
	for(i=0;i<n;i++)
	{
		scanf("%d %d",&x2,&y2);
		if(x1>x2)
			t=0;
		if(y1<y2)
			t=1;
		if(x1<x2)
			t=2;
		if(y1>y2)
			t=3;
		for(;x1!=x2||y1!=y2;)
		{
			route[Rs]=t;
			Rs++;
			x1+=Add[t][0];
			y1+=Add[t][1];
		}
	}
	fin=0;
	sta=0;
	for(i=0;i<=MaxL;i++)
		for(j=0;j<=MaxL;j++)
			for(k=0;k<4;k++)
				ants[i][j][k]=-1;
}


void work()
{
	bool ok;
	int i,j,x,y,d,p,tmp;
	int go[MaxM];
	memset(go,0,sizeof(go));
	ok=0;
	for(i=0;i<sta;i++)
		if(ant[i].x<0)
			ok++;
	for(i=0;i<sta;i++)
		if(ant[i].x>=0)
		{
			x=ant[i].x;
			y=ant[i].y;
			for(j=0;j<4;j++)
				if(ants[x][y][j]>=0)
				{
					tmp=ants[x][y][j];
					if(ant[tmp].wait>ant[i].wait||(ant[tmp].wait==ant[i].wait&&ant[tmp].len>ant[i].len))
					{
						p=1;
						go[i]=1;
						ok++;
						break;
					}
				}
		}

	do{
		ok=0;
		for(i=0;i<sta;i++)
			if(!go[i]&&ant[i].x>=0)
			{
				d=map[ant[i].x][ant[i].y];
				x=ant[i].x+Add[d][0];
				y=ant[i].y+Add[d][1];
				if(ants[x][y][d]>=0&&go[ants[x][y][d]]==1)
				{
					go[i]=1;
					ok++;
					continue;
				}
			}
	}while(ok);

	for(i=0;i<sta;i++)
		if(!go[i]&&ant[i].x>=0)
		{
			ant[i].len++;
			ant[i].wait=0;
			ants[ant[i].x][ant[i].y][ant[i].dir]=-1;
		}
		else if(ant[i].x>0)
			ant[i].wait++;

	for(i=0;i<sta;i++)
		if(!go[i]&&ant[i].x>=0)
		{
			x=ant[i].x;
			y=ant[i].y;
			if(x==100&&y==100&&i!=sta-1)
			{
				ants[100][100][map[100][100]]=i+1;
			}
			d=map[x][y];
			ant[i].x+=Add[d][0];
			ant[i].y+=Add[d][1];
			ant[i].dir=d;
			if(ant[i].x==ex&&ant[i].y==ey)
			{
				list[fin]=i;
				fin++;
				ant[i].x=-1;
			}
			else
				ants[ant[i].x][ant[i].y][d]=i;
		}
}

void write()  
{  
    int i;  
    printf("Case %d:\n",cases);  
    printf("Carl finished the path at time %d\n",ant[0].len+1);  
    printf("The ants finished in the following order:\n");  
    printf("%d",list[0]);  
    for(i=1;i<m;i++)  
        printf(" %d",list[i]);  
    printf("\n");  
    printf("The last ant finished the path at time %d\n",T+1);  
    if(cases<N)  
        printf("\n");  
}  


int main()
{
	int X,Y;
	scanf("%d",&N);
	for(cases=1;cases<=N;cases++)
	{
		init();
		X=100;
		Y=100;
		ex=-1;
		ey=-1;
		for(T=0;fin<m;T++)
		{
			if(T<Rs)
			{
				map[X][Y]=route[T];
				X+=Add[route[T]][0];
				Y+=Add[route[T]][1];
				if(T==Rs-1)
				{
					ex=X;
					ey=Y;
				}
			}
			if(T%D==0 && sta<m)
			{
				if(ants[100][100][map[100][100]]<0)
					ants[100][100][map[100][100]]=sta;
				ant[sta].x=100;
				ant[sta].y=100;
				ant[sta].len=0;
				ant[sta].wait=0;
				ant[sta].dir=map[100][100];
				sta++;
			}
			work();
		}
		write();
	}
}