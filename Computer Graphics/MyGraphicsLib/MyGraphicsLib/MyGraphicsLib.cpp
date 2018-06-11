#include <windows.h>
#include <math.h>
#include <iostream>
#include <queue>
#include "name.h"
using namespace std;
// 用于注册的窗口类名
const char g_szClassName[] = "myWindowClass";
#define pi 3.1415926

#define BLACK_COLOR RGB(0, 0, 0) //黑色 
#define BLUE_COLOR  RGB(0,0,255) //蓝色
#define RED_COLOR   RGB(255,0,0) //红色
#define α pi/4					 //阴影线45°
#define K tan(α)                //阴影线斜率

const int POINTNUM = 9;      //多边形点数.

typedef struct XET
{
	float x;
	float dx, ymax;
	struct XET* next;
}AET, NET;
/******定义点结构体point******************************************************/
struct point
{
	float x;
	float y;
}polypoint[POINTNUM] = {395, 887, 479, 998, 1199, 433, 1101, 867, 1294, 715, 1417, 171, 857, 163, 668, 314, 1111, 321};//{ 370,10,560,100,560,200,430,130,340,170,340,80 };//多边形顶点 

void DemoHZ(unsigned char *buf, int x, int y, COLORREF color,HDC hdc)
{
	int i;
	int j;
	int k;
	int nWidth = buf[0];
	int nBytesPerRow = buf[2];
	//开始写汉字  
	buf += 3;
	for (i = 0; i<nWidth; i++)
	{
		for (j = 0; j<nBytesPerRow; j++)
		{
			for (k = 0; k<8; k++)
				if (((buf[nBytesPerRow*i + j] >> (7 - k)) & 0x1) != NULL)
					SetPixel(hdc,x + 8 * j + k, y + i, color);
		}
	}
}

//直线算法一
void DDA(float x0, float y0, float xn, float yn, HDC hdc) {
	/*确定增量dx,dy*/
	int max;
	double dx, dy;
	int d_x = abs(xn - x0);
	int d_y = abs(yn - y0);
	if (d_x >= d_y)
		max = d_x;
	else
		max = d_y;
	dx = (xn - x0) / max;
	dy = (yn - y0) / max;
	/*绘点*/
	double xi = x0, yi = y0;
	int pix_x = floor(xi), pix_y = floor(yi);
	SetPixel(hdc, pix_x, pix_y, RGB(23, 24, 23));
	for (int i = 0; i != max; ++i) {
		xi += dx;
		yi += dy;
		pix_x = floor(xi);
		pix_y = floor(yi);
		SetPixel(hdc, pix_x, pix_y, RGB(23, 24, 23));
	}
}
//直线算法二
void Bresenham(int xs, int ys, int xe, int ye, HDC hdc) {
	int x, y, deltax, deltay, e;
	deltax = xe - xs;
	deltay = ye - ys;
	e = -deltax;
	x = xs;
	y = ys;
	for (int i = 0; i <= deltax; i++)
	{
		SetPixel(hdc, x + 0.5, y + 0.5, RED_COLOR);
		if (e >= 0)
		{
			y = y + 1;
			e = e - 2 * deltax;
		}
		else {
			x = x + 1;
			e = e + 2 * deltay;
		}
	}
}
//圆弧算法
void arc1(int x, int y, int r, HDC hdc, int kx, int ky) {
	int d;
	d = 3 - 2 * r;
	while (x<y)
	{
		SetPixel(hdc, kx*x + 300, ky*y + 300, BLACK_COLOR);
		if (d<0)
		{
			d = d + 4 * x + 6;
		}
		else {
			d = d + 4 * (x - y) + 10;
			y = y - 1;
		}
		x = x + 1;
	}
	if (x == y)
	{
		SetPixel(hdc, kx*x + 300, ky*y + 300, BLACK_COLOR);
	}
}
void arc2(int x, int y, int r, HDC hdc, int kx, int ky) {
	int d;
	d = 3 - 2 * r;
	while (x<y)
	{
		SetPixel(hdc, kx*y + 300, ky*x + 300, BLACK_COLOR);
		if (d<0)
		{
			d = d + 4 * x + 6;
		}
		else {
			d = d + 4 * (x - y) + 10;
			y = y - 1;
		}
		x = x + 1;
	}
	if (x == y)
	{
		SetPixel(hdc, kx*y + 300, ky*x + 300, BLACK_COLOR);
	}
}

//椭圆
void oval(int a, int b, HDC hdc) {
	float x, y, t = 0;

	for (; t <= 2 * pi; t += 0.0000005)
	{
		x = a*cos(t);
		y = b*sin(t);
		SetPixel(hdc, (int)x + 400, (int)y + 300, BLACK_COLOR);
	}
}

//按x坐标值升序排序
void bubbleSort(double array[][2], int size)
{
	bool changed = true;
	int n = 0;
	do {
		changed = false;
		for (int i = 0; i<size - 1 - n; i++) {
			if (array[i][0]>array[i + 1][0]) {
				swap(array[i][0], array[i + 1][0]);
				swap(array[i][1], array[i + 1][1]);
				changed = true;
			}
		}
		n++;
	} while (changed);
}

//阴影填充  mn 8,4
void shadowLine(HDC hdc, point P[], int mn, int m, double h, double a) {
	const double k = 1.0;
	const double deltaB = 8.0;
	/*对于每条棱边，计算其两个端点按影阴线斜率K引线得到的截距，
	其中较小的放在B[i][0]中，较大的放在B[i][1]中*/
	double B[9][2];
	int i, j;
	for (i = 0; (i + 1) <= (m - 1); ++i) {
		B[i][0] = (double)(P[i].y - k*P[i].x);
		B[i][1] = (double)(P[i + 1].y - k*P[i + 1].x);
		if (B[i][0]>B[i][1])
			swap(B[i][0], B[i][1]);
	}
	B[m - 1][0] = (double)(P[m - 1].y - k*P[m - 1].x);
	B[m - 1][1] = (double)(P[0].y - k*P[0].x);
	if (B[m - 1][0]>B[m - 1][1])
		swap(B[m - 1][0], B[m - 1][1]);
	for (i = m; (i + 1) <= (mn - 1); ++i) {
		B[i][0] = (double)(P[i].y - k*P[i].x);
		B[i][1] = (double)(P[i + 1].y - k*P[i + 1].x);
		if (B[i][0]>B[i][1])
			swap(B[i][0], B[i][1]);
	}
	B[mn - 1][0] = (double)(P[mn - 1].y - k*P[mn - 1].x);
	B[mn - 1][1] = (double)(P[m].y - k*P[m].x);
	if (B[mn - 1][0]>B[mn - 1][1])
		swap(B[m - 1][0], B[m - 1][1]);

	/*求出最小和最大的截距*/
	double Bmin = B[0][0], Bmax = B[0][1];
	for (i = 1; i<mn; i++) {
		if (B[i][0]<Bmin)
			Bmin = B[i][0];
		if (B[i][1]>Bmax)
			Bmax = B[i][1];
	}
	/*取第一条影阴线为Bmin+deltaB,之后每条+deltaB，对每条阴影线，判断其与
	各棱边是否有交点，若存在则存入D数组，按X升序排列D数组，按顺序，产生奇
	数点到偶数点的线段*/
	double b = Bmin + deltaB;
	double D[9][2];
	double xp, yp, xq, yq, x, y;
	while (b<Bmax) {
		//初始化D数组
		for (i = 0; i <= mn - 1; ++i)
			for (j = 0; j <= 1; ++j)
				D[i][j] = 10000.0;
		//求该阴影线和各棱边的交点，寻如D数组
		for (i = 0; (i + 1) <= (m - 1); ++i) {
			if ((B[i][0] <= b) && (b<B[i][1])) {
				xp = P[i].x;
				yp = P[i].y;
				xq = P[i + 1].x;
				yq = P[i + 1].y;
				x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
				y = k*x + b;
				D[i][0] = x;
				D[i][1] = y;
			}
		}
		if ((B[m - 1][0] <= b) && (b<B[m - 1][1])) {
			xp = P[m - 1].x;
			yp = P[m - 1].y;
			xq = P[0].x;
			yq = P[0].y;
			x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
			y = k*x + b;
			D[i][0] = x;
			D[i][1] = y;
		}
		for (i = m; (i + 1) <= (mn - 1); ++i) {
			if ((B[i][0] <= b) && (b<B[i][1])) {
				xp = P[i].x;
				yp = P[i].y;
				xq = P[i + 1].x;
				yq = P[i + 1].y;
				x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
				y = k*x + b;
				D[i][0] = x;
				D[i][1] = y;
			}
		}
		if ((B[mn - 1][0] <= b) && (b<B[mn - 1][1])) {
			xp = P[mn - 1].x;
			yp = P[mn - 1].y;
			xq = P[m].x;
			yq = P[m].y;
			x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
			y = k*x + b;
			D[i][0] = x;
			D[i][1] = y;
		}
		//按X升序排列D数组中的交点
		bubbleSort(D, 9);
		//按顺序，产生奇数点到偶数点之间的线段
		for (int k = 0; (D[2 * k][0] != 10000) && (D[2 * k + 1][0] != 10000); k++) {
			DDA(D[2 * k][0], D[2 * k][1], D[2 * k + 1][0], D[2 * k + 1][1], hdc);
		}
		//取下一条影阴线
		b = b + deltaB;
	}
}

//绘制任意多边形
void drawPolygon(HDC hdc, point p[], int mn, int m) {
	int i;
	for (i = 0; (i + 1) <= (m - 1); ++i)
		DDA(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y, hdc);
	DDA(p[0].x, p[0].y, p[m - 1].x, p[m - 1].y, hdc);
	for (i = m; (i + 1) <= (mn - 1); ++i)
		DDA(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y, hdc);
	//DDA(p[m].x, p[m].y, p[mn - 1].x, p[mn - 1].y, hdc);
}

//漫水法填充
void FloodFill4(HDC hdc, int x, int y, COLORREF old_color, COLORREF new_color) {
	try
	{//Stack Overflow
		if (x < 101.1 && y < 200.1) {
			if (GetPixel(hdc, x, y) == old_color)
			{
				SetPixel(hdc, x, y, new_color);
				FloodFill4(hdc, x, y + 1, old_color, new_color);
				FloodFill4(hdc, x, y - 1, old_color, new_color);
				FloodFill4(hdc, x + 1, y, old_color, new_color);
				FloodFill4(hdc, x - 1, y, old_color, new_color);
			}
		}
	}
	catch (const std::exception&)
	{
		cout << "exception catched!" << endl;
	}
}

void ScanFill(HDC hdc) {
	int MaxY = 0;
	int MinY = 2000;

	int i;
	for (i = 0; i < POINTNUM; i++)
	{
		if (polypoint[i].y > MaxY)
		{
			MaxY = polypoint[i].y;
		}

		if (polypoint[i].y < MinY)
		{
			MinY = polypoint[i].y;
		}

	}

	/*******初始化AET表，即初始化活跃边表***********************************************************/
	AET *pAET = new AET;
	pAET->next = NULL;

	/******初始化NET表，即初始化边表************************************************************/
	NET *pNET[2000];
	for (i = MinY; i <= MaxY; i++)
	{
		pNET[i] = new NET;
		pNET[i]->next = NULL;
	}

	/******扫描并建立NET表，即建立边表*********************************************************/
	for (i = MinY; i <= MaxY; i++)
	{
		for (int j = 0; j < POINTNUM; j++)
		{
			if (polypoint[j].y == i)
			{
				if (polypoint[(j - 1 + POINTNUM) % POINTNUM].y > polypoint[j].y)
				{
					NET *p = new NET;
					p->x = polypoint[j].x;
					p->ymax = polypoint[(j - 1 + POINTNUM) % POINTNUM].y;
					p->dx = (polypoint[(j - 1 + POINTNUM) % POINTNUM].x - polypoint[j].x) / (polypoint[(j - 1 + POINTNUM) % POINTNUM].y - polypoint[j].y);
					p->next = pNET[i]->next;
					pNET[i]->next = p;
				}

				if (polypoint[(j + 1 + POINTNUM) % POINTNUM].y > polypoint[j].y)
				{
					NET *p = new NET;
					p->x = polypoint[j].x;
					p->ymax = polypoint[(j + 1 + POINTNUM) % POINTNUM].y;
					p->dx = (polypoint[(j + 1 + POINTNUM) % POINTNUM].x - polypoint[j].x) / (polypoint[(j + 1 + POINTNUM) % POINTNUM].y - polypoint[j].y);
					p->next = pNET[i]->next;
					pNET[i]->next = p;
				}
			}
		}
	}

	/******建立并更新活性边表AET*****************************************************/
	for (i = MinY; i <= MaxY; i++) // for循环中按下面的流程而不是《计算机图形学》徐长青第二版P38中Polygonfill算法中的while循环中的流程，                        
	{                               // 这样可以处理书中的边界问题，无需开始时进行边缩短                                       
									//计算新的交点x,更新AET********************************************************/  
		NET *p = pAET->next;
		while (p != NULL)
		{
			p->x = p->x + p->dx;
			p = p->next;
		}

		//更新后新AET先排序*************************************************************/  
		//断表排序,不再开辟空间  
		AET *tq = pAET;
		p = pAET->next;
		tq->next = NULL;

		while (p != NULL)
		{
			while (tq->next != NULL && p->x >= tq->next->x)
			{
				tq = tq->next;
			}

			NET *s = p->next;
			p->next = tq->next;
			tq->next = p;
			p = s;
			tq = pAET;
		}

		//(改进算法)先从AET表中删除ymax==i的结点****************************************/  
		AET *q = pAET;
		p = q->next;
		while (p != NULL)
		{
			if (p->ymax == i)
			{
				q->next = p->next;
				delete p;
				p = q->next;
			}
			else
			{
				q = q->next;
				p = q->next;
			}
		}

		//将NET中的新点加入AET,并用插入法按X值递增排序**********************************/  
		p = pNET[i]->next;
		q = pAET;
		while (p != NULL)
		{
			while (q->next != NULL && p->x >= q->next->x)
			{
				q = q->next;
			}

			NET *s = p->next;
			p->next = q->next;
			q->next = p;
			p = s;
			q = pAET;
		}

		/******配对填充颜色***************************************************************/
		p = pAET->next;
		while (p != NULL && p->next != NULL)
		{
			for (float j = p->x; j <= p->next->x; j++)
			{
				SetPixel(hdc,static_cast<int>(j), i, RGB(255, 0, 0));
			}  // pDC.MoveTo( static_cast<int>(p->x), i ); 用画直线来替换上面的设置像素点颜色，速度更快  
			   //  pDC.LineTo( static_cast<int>(p->next->x), i );  

			p = p->next->next;//考虑端点情况  
		}
	}
	NET *phead = NULL;
	NET *pnext = NULL;
	//释放边表  
	
	//释放活跃边表  
	phead = pAET;
	while (phead != NULL)
	{
		pnext = phead->next;
		delete phead;
		phead = pnext;
	}
}
void Paint(HWND hwnd)
{
	/*POINT p[8] = {(50,200),(200,200),(200,50),(50,50),
				  (100,100),(100,150),(150,150),(150,100)};*/
	//Point(566, 970), Point(685, 1020), Point(754, 683), Point(985, 768), Point(1037, 481), Point(1208, 546), Point(1233, 179), Point(1140, 440), Point(951, 386), Point(899, 662), Point(668, 562)
	
	//int p[8][2];//存放多边形端点，外环4个，内环4个
	//p[0][0] = 50; p[0][1] = 200;
	//p[1][0] = 200; p[1][1] = 200;
	//p[2][0] = 200; p[2][1] = 50;
	//p[3][0] = 50; p[3][1] = 50;
	//p[4][0] = 100; p[4][1] = 100;
	//p[5][0] = 100; p[5][1] = 150;
	//p[6][0] = 150; p[6][1] = 150;
	//p[7][0] = 150; p[7][1] = 100;
	PAINTSTRUCT ps;
	HDC hdc;
	HPEN hpen, _hpen;//阴影线填充画笔

	hdc = BeginPaint(hwnd, &ps);
	hpen = CreatePen(PS_SOLID, 1, BLACK_COLOR);
	_hpen = CreatePen(PS_SOLID, 1, RED_COLOR);//阴影线填充画笔初始化
	SelectObject(hdc, hpen);

	//drawPolygon(hdc, polypoint, 9, 9);
	//ScanFill(hdc);	//修改
	//shadowLine(hdc, polypoint, 9, 9, 10, 30);
	//COLORREF this_color = RGB(255, 255, 255);//GetPixel(hdc, 180, 180);
	//FloodFill4(hdc, 101, 200, this_color, RED_COLOR);
	//DemoHZ(Bmp001, 100, 100, BLUE_COLOR, hdc);
	DemoHZ(Bmp002, 150, 100, BLUE_COLOR, hdc);
	//DemoHZ(Bmp003, 200, 100, BLUE_COLOR, hdc);
	DeleteObject(hpen);
	SelectObject(hdc, _hpen);// 选择阴影线填充画笔
	DeleteObject(_hpen);
	EndPaint(hwnd, &ps);
}


/*
* 第四步,窗口过程
*/
LRESULT CALLBACK MyWindowProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg)
	{
		// 窗口绘制消息
	case WM_PAINT:
		Paint(hwnd);
		/*
		* 我们只需要在这里调用我们的 GDI 绘制函数就可以，其他地方可以先无视
		*/
		break;
		// 窗口关闭消息
	case WM_CLOSE:
		DestroyWindow(hwnd);
		break;
		// 窗口销毁消息
	case WM_DESTROY:
		PostQuitMessage(0); // 发送离开消息给系统
		break;
		// 其他消息
	default:
		// pass 给系统，咱不管
		return DefWindowProc(hwnd, msg, wParam, lParam);
	}
	return 0;
}

/*
* 第一步,注册窗口类
*/
void RegisterMyWindow(HINSTANCE hInstance)
{
	WNDCLASSEX wc;

	// 1)配置窗口属性
	wc.cbSize = sizeof(WNDCLASSEX);
	wc.style = 0;
	wc.lpfnWndProc = MyWindowProc; // 设置第四步的窗口过程回调函数
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hInstance = hInstance;
	wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wc.lpszMenuName = NULL;
	wc.lpszClassName = (LPCWSTR)g_szClassName;
	wc.hIconSm = LoadIcon(NULL, IDI_APPLICATION);

	// 2)注册
	if (!RegisterClassEx(&wc))
	{
		MessageBox(NULL, TEXT("窗口注册失败!"), TEXT("错误"), MB_ICONEXCLAMATION | MB_OK);
		exit(0); // 进程退出
	}
}

/*
* 第二步，创建窗口
*/
HWND CreateMyWindow(HINSTANCE hInstance, int nCmdShow)
{
	HWND hwnd;
	hwnd = CreateWindowEx(
		WS_EX_CLIENTEDGE,
		(LPCWSTR)g_szClassName,
		TEXT("我的窗口名称"),
		WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT, CW_USEDEFAULT, 2000, 2000, // 出现坐标 x,y 默认分配 窗口宽 400 高 300
		NULL, NULL, hInstance, NULL);

	if (hwnd == NULL)
	{
		MessageBox(NULL, TEXT("窗口创建失败!"), TEXT("错误"), MB_ICONEXCLAMATION | MB_OK);
		exit(0); // 进程退出
	}

	// 显示窗口
	ShowWindow(hwnd, nCmdShow);
	UpdateWindow(hwnd);

	return hwnd;
}

/*
* 主函数
*/
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
	LPSTR lpCmdLine, int nCmdShow)
{
	HWND hwnd;
	MSG Msg;

	// 第一步,注册窗口类
	RegisterMyWindow(hInstance);
	// 第二步:创建窗口
	hwnd = CreateMyWindow(hInstance, nCmdShow);
	// 第三步:消息循环
	while (GetMessage(&Msg, NULL, 0, 0) > 0)
	{
		TranslateMessage(&Msg);
		DispatchMessage(&Msg);
	}
	return Msg.wParam;
}