#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<iostream>
#define MAX_VERTEX_NUM 10	//最多所拥有的点数
#define MAX_NAME 20			//名称字符的最大值
const int inf = 0x3f3f3f3f;//无限大的值，表示两路不可通
using namespace std;
//采用邻接表存储图结构
typedef struct _Road {
	int adjvex;//该弧指向的顶点的位置
	struct _Road *nextarc;//指向下一条弧的指针
	float *cost;//路程耗时
}Road;
typedef struct _SpotNode {
	int spotId;//点编号
	char name[20];//名称
	float longitude;//经度
	float latitude;//纬度
	float altitude;//高程
	Road *firstarc;//指向第一条依附该顶点的弧的指针
}SpotNode, AdjList[MAX_VERTEX_NUM];
typedef struct _ALGraph
{
	AdjList vertices;	//邻接链表
	int vexnum, arcnum;//图当前的顶点数和弧数
}ALGraph;
//点操作函数声明
void AddSpot(ALGraph &G);
void UpdateSpot(ALGraph &G);
void DeleteSpot(ALGraph &G);
int FindSpot(ALGraph G, int u);//u为输入的所需要定位的城市编号
							   //边操作函数声明
void AddRoad(ALGraph &G);
void UpdateRoad(ALGraph &G);
void DeleteRoad(ALGraph &G);
//迪杰斯特拉算法函数声明
void GetShortestPath(ALGraph &G);
//文件读取地图信息函数声明
void LoadMapByFile(ALGraph &G);
/**********函数具体实现方法**********/
//返回G中spotId为u的点的位置
int FindSpot(ALGraph G, int u) {
	int i;
	for (i = 0; i < G.vexnum; i++)
	{
		if (u == G.vertices[i].spotId)
			return i;
	}
	return -1;//找不到返回-1
}
//用文件加载地图信息
void LoadMapByFile(ALGraph &G) {
	int i, j;
	Road *p;
	float w;//权值
	int va, vb;//用于输入边所连接的两个点在邻接表中的存储位置
	FILE *fp;
	fp = fopen("map.txt", "r");
	while (!feof(fp))
	{
		fscanf(fp, "%d %d", &G.vexnum, &G.arcnum);
		for (int i = 0; i < G.vexnum; i++)
		{
			fscanf(fp, "%d %s %f %f %f\n", &G.vertices[i].spotId, &G.vertices[i].name,
				&G.vertices[i].longitude, &G.vertices[i].latitude, &G.vertices[i].altitude);
			G.vertices[i].firstarc = NULL;
		}
		for (int k = 0; k < G.arcnum; k++)
		{
			fscanf(fp, "%f %d %d\n", &w, &va, &vb);
			i = FindSpot(G, va);//弧头
			j = FindSpot(G, vb);//弧尾
								//连接<i,j>
			p = (Road *)malloc(sizeof(Road));
			p->adjvex = j;
			p->cost = (float *)malloc(sizeof(float));
			*(p->cost) = w;
			p->nextarc = G.vertices[i].firstarc;//插在表头
			G.vertices[i].firstarc = p;
			//连接<j,i>
			p = (Road*)malloc(sizeof(Road));
			p->adjvex = i;
			p->cost = (float *)malloc(sizeof(float));
			*(p->cost) = w;
			p->nextarc = G.vertices[j].firstarc;//插在表头
			G.vertices[j].firstarc = p;
		}
	}
	fclose(fp);
}
//创建图
void CreateGraph(ALGraph &G) {
	int i, j, k;
	float w;//权值
	int va, vb;//用于输入边所连接的两个点在邻接表中的存储位置
	Road *p;
	cout << "请输入地图的城市数，道路数（以空格为间隔）：" << endl;
	cin >> G.vexnum >> G.arcnum;
	cout << "请输入" << G.vexnum << "个城市的编号" << "、名字(" << MAX_NAME << "个字符以内）、经度、纬度以及高程：" << endl;
	for (i = 0; i < G.vexnum; i++)//构造顶点
	{
		cin >> G.vertices[i].spotId >> G.vertices[i].name >> G.vertices[i].longitude >> G.vertices[i].latitude >> G.vertices[i].altitude;
		G.vertices[i].firstarc = NULL;//第一条链指向空
	}
	cout << "请按顺序输入每条道路的耗时以及所连接的城市编号（以空格作为间隔）:" << endl;
	for (k = 0; k < G.arcnum; k++)//构造表结点链表
	{
		cin >> w >> va >> vb;
		i = FindSpot(G, va);//弧头
		j = FindSpot(G, vb);//弧尾
							//连接<i,j>
		p = (Road *)malloc(sizeof(Road));
		p->adjvex = j;
		p->cost = (float *)malloc(sizeof(float));
		*(p->cost) = w;
		p->nextarc = G.vertices[i].firstarc;//插在表头
		G.vertices[i].firstarc = p;
		//连接<j,i>
		p = (Road*)malloc(sizeof(Road));
		p->adjvex = i;
		p->cost = (float *)malloc(sizeof(float));
		*(p->cost) = w;
		p->nextarc = G.vertices[j].firstarc;//插在表头
		G.vertices[j].firstarc = p;
	}
}
//修改点信息
void UpdateSpot(ALGraph &G) {
	int spotId;
	cout << "请输入你要修改的城市的编号：" << endl;
	cin >> spotId;
	int i;
	i = FindSpot(G, spotId);
	if (i>-1) {
		cout << "请输入新的名称，经度，纬度，高程（以空格间隔）：" << endl;
		cin >> G.vertices[i].name >> G.vertices[i].longitude >> G.vertices[i].latitude >> G.vertices[i].altitude;
	}
}
//插入点v
void AddSpot(ALGraph &G) {
	int id;
	cout << "请输入要添加城市的编号：" << endl;
	cin >> id;
	for (int i = 0; i < G.vexnum; i++)
	{
		if (id == G.vertices[i].spotId) {
			cout << "城市编号重复，请重新输入！" << endl;
			return;
		}
	}
	cout << "请输入要添加城市的名称，经度，纬度，高程（以空格间隔）：" << endl;
	cin >> G.vertices[G.vexnum].name >> G.vertices[G.vexnum].longitude
		>> G.vertices[G.vexnum].latitude >> G.vertices[G.vexnum].altitude;
	G.vertices[G.vexnum].spotId = id;
	G.vertices[G.vexnum].firstarc = NULL;
	G.vexnum++;//图的顶点数加1
}
//删除点
void DeleteSpot(ALGraph &G) {
	int spotId;
	cout << "请输入要删除城市的编号：" << endl;
	cin >> spotId;
	int i, j;
	Road *p, *q;
	j = FindSpot(G, spotId);//j是定点v的序号
	if (j < 0) {
		cout << "未找到该城市，删除失败！" << endl;
		return;//v不是图G的顶点
	}
	p = G.vertices[j].firstarc;//删除以v为出度的弧或边
	while (p)
	{
		q = p;
		p = p->nextarc;
		free(q->cost);
		free(q);
		G.arcnum--;//弧或边数减一
	}
	G.vexnum--;//顶点数减一
	for (i = j; i < G.vexnum; i++)
		G.vertices[i] = G.vertices[i + 1];//顶点v后面的顶点前移
	for (i = 0; i < G.vexnum; i++)//删除以v为入度的弧或边且必要时修改表结点的顶点位置
	{
		p = G.vertices[i].firstarc;//指向第1条弧或边
		while (p)
		{
			if (p->adjvex == j) {
				if (p == G.vertices[i].firstarc)//待删除结点是第1个结点
				{
					G.vertices[i].firstarc = p->nextarc;
					free(p->cost);
					free(p);
					p = G.vertices[i].firstarc;
				}
				else
				{
					q->nextarc = p->nextarc;
					free(p->cost);
					free(p);
					p = p->nextarc;
				}
			}
			else
			{
				if (p->adjvex > j)
					p->adjvex--;//修改表结点的顶点位置值
				q = p;
				p = p->nextarc;
			}
		}
	}
}
//添加道路
void AddRoad(ALGraph &G) {
	int v;
	int w;
	cout << "请问你想在哪两个城市搭建道路（输入城市编号，以空格为间隔）" << endl;
	cin >> v >> w;
	Road *p;
	int i, j;
	float w1;
	i = FindSpot(G, v);
	j = FindSpot(G, w);
	if (i < 0 || j < 0) {
		cout << "未找到相关城市，添加道路失败！" << endl;
		return;
	}
	G.arcnum++;
	cout << "请输入道路 " << G.vertices[i].name << "--" << G.vertices[j].name << "的耗时：" << endl;
	cin >> w1;
	p = (Road *)malloc(sizeof(Road));
	p->adjvex = j;
	p->cost = (float *)malloc(sizeof(float));
	*(p->cost) = w1;
	p->nextarc = G.vertices[i].firstarc;//插在表头
	G.vertices[i].firstarc = p;
	//无向，生产另一个表结点
	p = (Road *)malloc(sizeof(Road));
	p->adjvex = i;
	p->cost = (float *)malloc(sizeof(float));
	*(p->cost) = w1;
	p->nextarc = G.vertices[j].firstarc;//插在表头
	G.vertices[j].firstarc = p;
}
//删除道路
void DeleteRoad(ALGraph &G) {
	int v;
	int w;
	cout << "请输入你要删除的道路所连接的两个城市编号（以空格间隔）：" << endl;
	cin >> v >> w;
	Road *p, *q;
	int i, j;
	i = FindSpot(G, v);//i是定点的序号
	j = FindSpot(G, w);//j是定点的序号
	if (i < 0 || j < 0 || i == j) {
		cout << "未找到相关道路信息，删除失败！" << endl;
		return;
	}
	p = G.vertices[i].firstarc;//p指向顶点v的第一条弧尾
	while (p&&p->adjvex != j)//p不空且所指的弧不是待删除<v,w>
	{
		q = p;
		p = p->nextarc;//p指向下一条弧
	}
	if (p&&p->adjvex == j)//找到
	{
		if (p == G.vertices[i].firstarc)//p是指第1条弧
			G.vertices[i].firstarc = p->nextarc;
		else
			q->nextarc = p->nextarc;//指向下一条弧
		free(p->cost);
		free(p);//释放此结点
		G.arcnum--;//弧或边数减1
	}
	//无向，删除对称弧<w,v>
	p = G.vertices[j].firstarc;
	while (p&&p->adjvex != i)
	{
		q = p;
		p = p->nextarc;
	}
	if (p&&p->adjvex == i)
	{
		if (p == G.vertices[j].firstarc)
			G.vertices[j].firstarc = p->nextarc;
		else
			q->nextarc = p->nextarc;
		free(p->cost);
		free(p);
	}
}
//更新道路耗时
void UpdateRoad(ALGraph &G) {
	int v, w;//用于输入两个城市的编号
	float w1;
	cout << "请输入你要修改的道路所连接的两个城市编号（以空格间隔）：" << endl;
	cin >> v >> w;
	Road *p, *q;
	int i, j;
	i = FindSpot(G, v);//i是定点的在邻接表内的存储位置
	j = FindSpot(G, w);//j是定点的在邻接表内的存储位置
	if (i < 0 || j < 0 || i == j) {
		cout << "未找到相关道路信息，修改失败！" << endl;
		return;
	}
	cout << "请输入道路" << G.vertices[i].name << "--" << G.vertices[j].name << "新的耗时：" << endl;
	cin >> w1;
	p = G.vertices[i].firstarc;//p指向顶点v的第一条弧尾
	while (p&&p->adjvex != j)//p不空且所指的弧不是待修改<v,w>
	{
		p = p->nextarc;//p指向下一条弧
	}
	if (p&&p->adjvex == j) {
		*(p->cost) = w1;
	}//找到
	 //无向图还要更新另一对称边的耗时
	p = G.vertices[j].firstarc;
	while (p&&p->adjvex != i)
	{
		p = p->nextarc;
	}
	if (p&&p->adjvex == j) {
		*(p->cost) = w1;
	}//找到
}
//显示图
void Display(ALGraph G) {
	int i;
	int count = 0;//用于换行
	Road *p = NULL;
	cout << G.vexnum << "个城市：" << endl;
	cout << "编号" << "  " << "名称" << "  " << "经度" << "  " << "纬度" << "  " << "高程       "
		<< "编号" << "  " << "名称" << "  " << "经度" << "  " << "纬度" << "  " << "高程       " << endl;;
	for (i = 0; i < G.vexnum; i++)
	{
		if (i % 2 == 0)
		{
			cout << endl;
		}
		cout << G.vertices[i].spotId << "   " << G.vertices[i].name << "   "
			<< G.vertices[i].longitude << "   " << G.vertices[i].latitude << "   "
			<< G.vertices[i].altitude << "             ";
	}
	cout << endl;
	cout << G.arcnum << "条道路：" << endl;
	cout << "城市" << "   " << "城市" << "   " << "耗时" << "          "
		<< "城市" << "   " << "城市" << "   " << "耗时" << "          " << endl;
	for (i = 0; i < G.vexnum; i++)
	{
		p = G.vertices[i].firstarc;
		while (p)
		{
			if (i < p->adjvex) {//使得输出的结果不重复例如输出v0-v3
				if (count % 2 == 0)//就不会再输出v3-v0
				{
					cout << endl;
				}
				count++;
				cout << G.vertices[i].name << "---" << G.vertices[p->adjvex].name;
				cout << ":" << "  " << *(p->cost) << "            ";
			}
			p = p->nextarc;
		}
	}
	cout << endl;
}
//得到任意两点的最短路径
void GetShortestPath(ALGraph &G) {
	int d[MAX_VERTEX_NUM];//记录v到各顶点的最小路径
	int use[MAX_VERTEX_NUM];//记录各节点是否已求得最短路径，0表示未求得，1表示已求得
	int path[MAX_VERTEX_NUM];//记录v0到各个顶点最小路径的中间节点
	int i, j;//循环变量
	int source, sink;
	cout << "请输入要查询最短路径的两个城市编号（以空格为间隔）：" << endl;
	cin >> source >> sink;
	int a, b;
	a = FindSpot(G, source);//a是起点的序号
	b = FindSpot(G, sink);//b是终点的序号
	if (a < 0 || b < 0 || a == b)
		return;
	Road *p, *q;
	p = G.vertices[source].firstarc;
	for (i = 0; i < MAX_VERTEX_NUM; i++)
	{
		d[i] = inf;
	}
	for (i = 0; i < MAX_VERTEX_NUM; i++)
	{
		use[i] = 0;
	}
	for (p; p != NULL; p = p->nextarc)
	{
		for (i = 0; i < G.vexnum; i++)
		{
			if (p->adjvex == i)
			{
				d[i] = *(p->cost);
			}
		}
	}
	use[source] = 1;
	for (i = 1; i < G.vexnum; i++)
	{
		int k, min = inf;//k为中间点，min为最小路径
		for (j = 0; j < G.vexnum; j++)
		{
			if (!use[j] && d[j]<min)
			{
				min = d[j];
				k = j;
			}
		}
		use[k] = 1;
		q = G.vertices[k].firstarc;
		while (q)
		{
			for (int w = q->adjvex; w != -1 && q != NULL;)
			{
				w = q->adjvex;
				if (!use[w] && min + *(q->cost) < d[w]) {
					d[w] = min + *(q->cost);
					path[w] = k;
				}
				q = q->nextarc;
			}
		}
	}
	cout << G.vertices[source].name << "-- " << G.vertices[sink].name << "的最短路径是：" << endl;
	cout << G.vertices[source].name << "-->";
	int location = sink;
	int path_out[MAX_VERTEX_NUM];//用于正序输出最短路径
	int l = G.vexnum - 1;
	while (path[sink]>0)
	{
		path_out[l--] = path[sink];
		sink = path[sink];
	}
	for (i = 0; i < G.vexnum; i++)
	{
		if (path_out[i]>0)
		{
			cout << G.vertices[path_out[i]].name << "-->";
		}
	}
	cout << G.vertices[location].name << "   " << "耗时：" << d[location] << endl;
}
//显示主菜单
void Menu() {
	cout << "====================================================" << endl;
	cout << "             欢迎登陆地理信息管理系统               " << endl;
	cout << "                                                    " << endl;
	cout << "          1.创建地图       6.显示地图信息           " << endl;
	cout << "          2.添加城市       7.修改城市信息           " << endl;
	cout << "          3.添加道路       8.修改道路信息           " << endl;
	cout << "          4.删除城市       9.查看最短路径           " << endl;
	cout << "          5.删除道路       0.退出系统               " << endl;
	cout << "         10.用文件读取地图                          " << endl;
	cout << "====================================================" << endl;
}
//主函数
int main(void) {
	int i;
	ALGraph G;
	while (1)
	{
		Menu();
		cin >> i;
		switch (i)
		{
		case 0:return 0;//退出系统
		case 1:CreateGraph(G);
			break;
		case 2:AddSpot(G);
			break;
		case 3:AddRoad(G);
			break;
		case 4:DeleteSpot(G);
			break;
		case 5:DeleteRoad(G);
			break;
		case 6:Display(G);
			break;
		case 7:UpdateSpot(G);
			break;
		case 8:UpdateRoad(G);
			break;
		case 9:GetShortestPath(G);
			break;
		case 10:LoadMapByFile(G);
			break;
		default:
			break;
		}
	}
	return 0;
}
