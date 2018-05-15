#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<iostream>
#define MAX_VERTEX_NUM 10	//�����ӵ�еĵ���
#define MAX_NAME 20			//�����ַ������ֵ
const int inf = 0x3f3f3f3f;//���޴��ֵ����ʾ��·����ͨ
using namespace std;
//�����ڽӱ�洢ͼ�ṹ
typedef struct _Road {
	int adjvex;//�û�ָ��Ķ����λ��
	struct _Road *nextarc;//ָ����һ������ָ��
	float *cost;//·�̺�ʱ
}Road;
typedef struct _SpotNode {
	int spotId;//����
	char name[20];//����
	float longitude;//����
	float latitude;//γ��
	float altitude;//�߳�
	Road *firstarc;//ָ���һ�������ö���Ļ���ָ��
}SpotNode, AdjList[MAX_VERTEX_NUM];
typedef struct _ALGraph
{
	AdjList vertices;	//�ڽ�����
	int vexnum, arcnum;//ͼ��ǰ�Ķ������ͻ���
}ALGraph;
//�������������
void AddSpot(ALGraph &G);
void UpdateSpot(ALGraph &G);
void DeleteSpot(ALGraph &G);
int FindSpot(ALGraph G, int u);//uΪ���������Ҫ��λ�ĳ��б��
							   //�߲�����������
void AddRoad(ALGraph &G);
void UpdateRoad(ALGraph &G);
void DeleteRoad(ALGraph &G);
//�Ͻ�˹�����㷨��������
void GetShortestPath(ALGraph &G);
//�ļ���ȡ��ͼ��Ϣ��������
void LoadMapByFile(ALGraph &G);
/**********��������ʵ�ַ���**********/
//����G��spotIdΪu�ĵ��λ��
int FindSpot(ALGraph G, int u) {
	int i;
	for (i = 0; i < G.vexnum; i++)
	{
		if (u == G.vertices[i].spotId)
			return i;
	}
	return -1;//�Ҳ�������-1
}
//���ļ����ص�ͼ��Ϣ
void LoadMapByFile(ALGraph &G) {
	int i, j;
	Road *p;
	float w;//Ȩֵ
	int va, vb;//��������������ӵ����������ڽӱ��еĴ洢λ��
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
			i = FindSpot(G, va);//��ͷ
			j = FindSpot(G, vb);//��β
								//����<i,j>
			p = (Road *)malloc(sizeof(Road));
			p->adjvex = j;
			p->cost = (float *)malloc(sizeof(float));
			*(p->cost) = w;
			p->nextarc = G.vertices[i].firstarc;//���ڱ�ͷ
			G.vertices[i].firstarc = p;
			//����<j,i>
			p = (Road*)malloc(sizeof(Road));
			p->adjvex = i;
			p->cost = (float *)malloc(sizeof(float));
			*(p->cost) = w;
			p->nextarc = G.vertices[j].firstarc;//���ڱ�ͷ
			G.vertices[j].firstarc = p;
		}
	}
	fclose(fp);
}
//����ͼ
void CreateGraph(ALGraph &G) {
	int i, j, k;
	float w;//Ȩֵ
	int va, vb;//��������������ӵ����������ڽӱ��еĴ洢λ��
	Road *p;
	cout << "�������ͼ�ĳ���������·�����Կո�Ϊ�������" << endl;
	cin >> G.vexnum >> G.arcnum;
	cout << "������" << G.vexnum << "�����еı��" << "������(" << MAX_NAME << "���ַ����ڣ������ȡ�γ���Լ��̣߳�" << endl;
	for (i = 0; i < G.vexnum; i++)//���춥��
	{
		cin >> G.vertices[i].spotId >> G.vertices[i].name >> G.vertices[i].longitude >> G.vertices[i].latitude >> G.vertices[i].altitude;
		G.vertices[i].firstarc = NULL;//��һ����ָ���
	}
	cout << "�밴˳������ÿ����·�ĺ�ʱ�Լ������ӵĳ��б�ţ��Կո���Ϊ�����:" << endl;
	for (k = 0; k < G.arcnum; k++)//�����������
	{
		cin >> w >> va >> vb;
		i = FindSpot(G, va);//��ͷ
		j = FindSpot(G, vb);//��β
							//����<i,j>
		p = (Road *)malloc(sizeof(Road));
		p->adjvex = j;
		p->cost = (float *)malloc(sizeof(float));
		*(p->cost) = w;
		p->nextarc = G.vertices[i].firstarc;//���ڱ�ͷ
		G.vertices[i].firstarc = p;
		//����<j,i>
		p = (Road*)malloc(sizeof(Road));
		p->adjvex = i;
		p->cost = (float *)malloc(sizeof(float));
		*(p->cost) = w;
		p->nextarc = G.vertices[j].firstarc;//���ڱ�ͷ
		G.vertices[j].firstarc = p;
	}
}
//�޸ĵ���Ϣ
void UpdateSpot(ALGraph &G) {
	int spotId;
	cout << "��������Ҫ�޸ĵĳ��еı�ţ�" << endl;
	cin >> spotId;
	int i;
	i = FindSpot(G, spotId);
	if (i>-1) {
		cout << "�������µ����ƣ����ȣ�γ�ȣ��̣߳��Կո�������" << endl;
		cin >> G.vertices[i].name >> G.vertices[i].longitude >> G.vertices[i].latitude >> G.vertices[i].altitude;
	}
}
//�����v
void AddSpot(ALGraph &G) {
	int id;
	cout << "������Ҫ��ӳ��еı�ţ�" << endl;
	cin >> id;
	for (int i = 0; i < G.vexnum; i++)
	{
		if (id == G.vertices[i].spotId) {
			cout << "���б���ظ������������룡" << endl;
			return;
		}
	}
	cout << "������Ҫ��ӳ��е����ƣ����ȣ�γ�ȣ��̣߳��Կո�������" << endl;
	cin >> G.vertices[G.vexnum].name >> G.vertices[G.vexnum].longitude
		>> G.vertices[G.vexnum].latitude >> G.vertices[G.vexnum].altitude;
	G.vertices[G.vexnum].spotId = id;
	G.vertices[G.vexnum].firstarc = NULL;
	G.vexnum++;//ͼ�Ķ�������1
}
//ɾ����
void DeleteSpot(ALGraph &G) {
	int spotId;
	cout << "������Ҫɾ�����еı�ţ�" << endl;
	cin >> spotId;
	int i, j;
	Road *p, *q;
	j = FindSpot(G, spotId);//j�Ƕ���v�����
	if (j < 0) {
		cout << "δ�ҵ��ó��У�ɾ��ʧ�ܣ�" << endl;
		return;//v����ͼG�Ķ���
	}
	p = G.vertices[j].firstarc;//ɾ����vΪ���ȵĻ����
	while (p)
	{
		q = p;
		p = p->nextarc;
		free(q->cost);
		free(q);
		G.arcnum--;//���������һ
	}
	G.vexnum--;//��������һ
	for (i = j; i < G.vexnum; i++)
		G.vertices[i] = G.vertices[i + 1];//����v����Ķ���ǰ��
	for (i = 0; i < G.vexnum; i++)//ɾ����vΪ��ȵĻ�����ұ�Ҫʱ�޸ı���Ķ���λ��
	{
		p = G.vertices[i].firstarc;//ָ���1�������
		while (p)
		{
			if (p->adjvex == j) {
				if (p == G.vertices[i].firstarc)//��ɾ������ǵ�1�����
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
					p->adjvex--;//�޸ı���Ķ���λ��ֵ
				q = p;
				p = p->nextarc;
			}
		}
	}
}
//��ӵ�·
void AddRoad(ALGraph &G) {
	int v;
	int w;
	cout << "�������������������д��·��������б�ţ��Կո�Ϊ�����" << endl;
	cin >> v >> w;
	Road *p;
	int i, j;
	float w1;
	i = FindSpot(G, v);
	j = FindSpot(G, w);
	if (i < 0 || j < 0) {
		cout << "δ�ҵ���س��У���ӵ�·ʧ�ܣ�" << endl;
		return;
	}
	G.arcnum++;
	cout << "�������· " << G.vertices[i].name << "--" << G.vertices[j].name << "�ĺ�ʱ��" << endl;
	cin >> w1;
	p = (Road *)malloc(sizeof(Road));
	p->adjvex = j;
	p->cost = (float *)malloc(sizeof(float));
	*(p->cost) = w1;
	p->nextarc = G.vertices[i].firstarc;//���ڱ�ͷ
	G.vertices[i].firstarc = p;
	//����������һ������
	p = (Road *)malloc(sizeof(Road));
	p->adjvex = i;
	p->cost = (float *)malloc(sizeof(float));
	*(p->cost) = w1;
	p->nextarc = G.vertices[j].firstarc;//���ڱ�ͷ
	G.vertices[j].firstarc = p;
}
//ɾ����·
void DeleteRoad(ALGraph &G) {
	int v;
	int w;
	cout << "��������Ҫɾ���ĵ�·�����ӵ��������б�ţ��Կո�������" << endl;
	cin >> v >> w;
	Road *p, *q;
	int i, j;
	i = FindSpot(G, v);//i�Ƕ�������
	j = FindSpot(G, w);//j�Ƕ�������
	if (i < 0 || j < 0 || i == j) {
		cout << "δ�ҵ���ص�·��Ϣ��ɾ��ʧ�ܣ�" << endl;
		return;
	}
	p = G.vertices[i].firstarc;//pָ�򶥵�v�ĵ�һ����β
	while (p&&p->adjvex != j)//p��������ָ�Ļ����Ǵ�ɾ��<v,w>
	{
		q = p;
		p = p->nextarc;//pָ����һ����
	}
	if (p&&p->adjvex == j)//�ҵ�
	{
		if (p == G.vertices[i].firstarc)//p��ָ��1����
			G.vertices[i].firstarc = p->nextarc;
		else
			q->nextarc = p->nextarc;//ָ����һ����
		free(p->cost);
		free(p);//�ͷŴ˽��
		G.arcnum--;//���������1
	}
	//����ɾ���Գƻ�<w,v>
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
//���µ�·��ʱ
void UpdateRoad(ALGraph &G) {
	int v, w;//���������������еı��
	float w1;
	cout << "��������Ҫ�޸ĵĵ�·�����ӵ��������б�ţ��Կո�������" << endl;
	cin >> v >> w;
	Road *p, *q;
	int i, j;
	i = FindSpot(G, v);//i�Ƕ�������ڽӱ��ڵĴ洢λ��
	j = FindSpot(G, w);//j�Ƕ�������ڽӱ��ڵĴ洢λ��
	if (i < 0 || j < 0 || i == j) {
		cout << "δ�ҵ���ص�·��Ϣ���޸�ʧ�ܣ�" << endl;
		return;
	}
	cout << "�������·" << G.vertices[i].name << "--" << G.vertices[j].name << "�µĺ�ʱ��" << endl;
	cin >> w1;
	p = G.vertices[i].firstarc;//pָ�򶥵�v�ĵ�һ����β
	while (p&&p->adjvex != j)//p��������ָ�Ļ����Ǵ��޸�<v,w>
	{
		p = p->nextarc;//pָ����һ����
	}
	if (p&&p->adjvex == j) {
		*(p->cost) = w1;
	}//�ҵ�
	 //����ͼ��Ҫ������һ�ԳƱߵĺ�ʱ
	p = G.vertices[j].firstarc;
	while (p&&p->adjvex != i)
	{
		p = p->nextarc;
	}
	if (p&&p->adjvex == j) {
		*(p->cost) = w1;
	}//�ҵ�
}
//��ʾͼ
void Display(ALGraph G) {
	int i;
	int count = 0;//���ڻ���
	Road *p = NULL;
	cout << G.vexnum << "�����У�" << endl;
	cout << "���" << "  " << "����" << "  " << "����" << "  " << "γ��" << "  " << "�߳�       "
		<< "���" << "  " << "����" << "  " << "����" << "  " << "γ��" << "  " << "�߳�       " << endl;;
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
	cout << G.arcnum << "����·��" << endl;
	cout << "����" << "   " << "����" << "   " << "��ʱ" << "          "
		<< "����" << "   " << "����" << "   " << "��ʱ" << "          " << endl;
	for (i = 0; i < G.vexnum; i++)
	{
		p = G.vertices[i].firstarc;
		while (p)
		{
			if (i < p->adjvex) {//ʹ������Ľ�����ظ��������v0-v3
				if (count % 2 == 0)//�Ͳ��������v3-v0
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
//�õ�������������·��
void GetShortestPath(ALGraph &G) {
	int d[MAX_VERTEX_NUM];//��¼v�����������С·��
	int use[MAX_VERTEX_NUM];//��¼���ڵ��Ƿ���������·����0��ʾδ��ã�1��ʾ�����
	int path[MAX_VERTEX_NUM];//��¼v0������������С·�����м�ڵ�
	int i, j;//ѭ������
	int source, sink;
	cout << "������Ҫ��ѯ���·�����������б�ţ��Կո�Ϊ�������" << endl;
	cin >> source >> sink;
	int a, b;
	a = FindSpot(G, source);//a���������
	b = FindSpot(G, sink);//b���յ�����
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
		int k, min = inf;//kΪ�м�㣬minΪ��С·��
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
	cout << G.vertices[source].name << "-- " << G.vertices[sink].name << "�����·���ǣ�" << endl;
	cout << G.vertices[source].name << "-->";
	int location = sink;
	int path_out[MAX_VERTEX_NUM];//��������������·��
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
	cout << G.vertices[location].name << "   " << "��ʱ��" << d[location] << endl;
}
//��ʾ���˵�
void Menu() {
	cout << "====================================================" << endl;
	cout << "             ��ӭ��½������Ϣ����ϵͳ               " << endl;
	cout << "                                                    " << endl;
	cout << "          1.������ͼ       6.��ʾ��ͼ��Ϣ           " << endl;
	cout << "          2.��ӳ���       7.�޸ĳ�����Ϣ           " << endl;
	cout << "          3.��ӵ�·       8.�޸ĵ�·��Ϣ           " << endl;
	cout << "          4.ɾ������       9.�鿴���·��           " << endl;
	cout << "          5.ɾ����·       0.�˳�ϵͳ               " << endl;
	cout << "         10.���ļ���ȡ��ͼ                          " << endl;
	cout << "====================================================" << endl;
}
//������
int main(void) {
	int i;
	ALGraph G;
	while (1)
	{
		Menu();
		cin >> i;
		switch (i)
		{
		case 0:return 0;//�˳�ϵͳ
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
