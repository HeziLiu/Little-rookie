#include <windows.h>
#include <math.h>
#include <iostream>
#include <queue>
#include "name.h"
using namespace std;
// ����ע��Ĵ�������
const char g_szClassName[] = "myWindowClass";
#define pi 3.1415926

#define BLACK_COLOR RGB(0, 0, 0) //��ɫ 
#define BLUE_COLOR  RGB(0,0,255) //��ɫ
#define RED_COLOR   RGB(255,0,0) //��ɫ
#define �� pi/4					 //��Ӱ��45��
#define K tan(��)                //��Ӱ��б��

const int POINTNUM = 9;      //����ε���.

typedef struct XET
{
	float x;
	float dx, ymax;
	struct XET* next;
}AET, NET;
/******�����ṹ��point******************************************************/
struct point
{
	float x;
	float y;
}polypoint[POINTNUM] = {395, 887, 479, 998, 1199, 433, 1101, 867, 1294, 715, 1417, 171, 857, 163, 668, 314, 1111, 321};//{ 370,10,560,100,560,200,430,130,340,170,340,80 };//����ζ��� 

void DemoHZ(unsigned char *buf, int x, int y, COLORREF color,HDC hdc)
{
	int i;
	int j;
	int k;
	int nWidth = buf[0];
	int nBytesPerRow = buf[2];
	//��ʼд����  
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

//ֱ���㷨һ
void DDA(float x0, float y0, float xn, float yn, HDC hdc) {
	/*ȷ������dx,dy*/
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
	/*���*/
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
//ֱ���㷨��
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
//Բ���㷨
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

//��Բ
void oval(int a, int b, HDC hdc) {
	float x, y, t = 0;

	for (; t <= 2 * pi; t += 0.0000005)
	{
		x = a*cos(t);
		y = b*sin(t);
		SetPixel(hdc, (int)x + 400, (int)y + 300, BLACK_COLOR);
	}
}

//��x����ֵ��������
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

//��Ӱ���  mn 8,4
void shadowLine(HDC hdc, point P[], int mn, int m, double h, double a) {
	const double k = 1.0;
	const double deltaB = 8.0;
	/*����ÿ����ߣ������������˵㰴Ӱ����б��K���ߵõ��Ľؾ࣬
	���н�С�ķ���B[i][0]�У��ϴ�ķ���B[i][1]��*/
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

	/*�����С�����Ľؾ�*/
	double Bmin = B[0][0], Bmax = B[0][1];
	for (i = 1; i<mn; i++) {
		if (B[i][0]<Bmin)
			Bmin = B[i][0];
		if (B[i][1]>Bmax)
			Bmax = B[i][1];
	}
	/*ȡ��һ��Ӱ����ΪBmin+deltaB,֮��ÿ��+deltaB����ÿ����Ӱ�ߣ��ж�����
	������Ƿ��н��㣬�����������D���飬��X��������D���飬��˳�򣬲�����
	���㵽ż������߶�*/
	double b = Bmin + deltaB;
	double D[9][2];
	double xp, yp, xq, yq, x, y;
	while (b<Bmax) {
		//��ʼ��D����
		for (i = 0; i <= mn - 1; ++i)
			for (j = 0; j <= 1; ++j)
				D[i][j] = 10000.0;
		//�����Ӱ�ߺ͸���ߵĽ��㣬Ѱ��D����
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
		//��X��������D�����еĽ���
		bubbleSort(D, 9);
		//��˳�򣬲��������㵽ż����֮����߶�
		for (int k = 0; (D[2 * k][0] != 10000) && (D[2 * k + 1][0] != 10000); k++) {
			DDA(D[2 * k][0], D[2 * k][1], D[2 * k + 1][0], D[2 * k + 1][1], hdc);
		}
		//ȡ��һ��Ӱ����
		b = b + deltaB;
	}
}

//������������
void drawPolygon(HDC hdc, point p[], int mn, int m) {
	int i;
	for (i = 0; (i + 1) <= (m - 1); ++i)
		DDA(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y, hdc);
	DDA(p[0].x, p[0].y, p[m - 1].x, p[m - 1].y, hdc);
	for (i = m; (i + 1) <= (mn - 1); ++i)
		DDA(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y, hdc);
	//DDA(p[m].x, p[m].y, p[mn - 1].x, p[mn - 1].y, hdc);
}

//��ˮ�����
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

	/*******��ʼ��AET������ʼ����Ծ�߱�***********************************************************/
	AET *pAET = new AET;
	pAET->next = NULL;

	/******��ʼ��NET������ʼ���߱�************************************************************/
	NET *pNET[2000];
	for (i = MinY; i <= MaxY; i++)
	{
		pNET[i] = new NET;
		pNET[i]->next = NULL;
	}

	/******ɨ�貢����NET���������߱�*********************************************************/
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

	/******���������»��Ա߱�AET*****************************************************/
	for (i = MinY; i <= MaxY; i++) // forѭ���а���������̶����ǡ������ͼ��ѧ���쳤��ڶ���P38��Polygonfill�㷨�е�whileѭ���е����̣�                        
	{                               // �������Դ������еı߽����⣬���迪ʼʱ���б�����                                       
									//�����µĽ���x,����AET********************************************************/  
		NET *p = pAET->next;
		while (p != NULL)
		{
			p->x = p->x + p->dx;
			p = p->next;
		}

		//���º���AET������*************************************************************/  
		//�ϱ�����,���ٿ��ٿռ�  
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

		//(�Ľ��㷨)�ȴ�AET����ɾ��ymax==i�Ľ��****************************************/  
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

		//��NET�е��µ����AET,���ò��뷨��Xֵ��������**********************************/  
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

		/******��������ɫ***************************************************************/
		p = pAET->next;
		while (p != NULL && p->next != NULL)
		{
			for (float j = p->x; j <= p->next->x; j++)
			{
				SetPixel(hdc,static_cast<int>(j), i, RGB(255, 0, 0));
			}  // pDC.MoveTo( static_cast<int>(p->x), i ); �û�ֱ�����滻������������ص���ɫ���ٶȸ���  
			   //  pDC.LineTo( static_cast<int>(p->next->x), i );  

			p = p->next->next;//���Ƕ˵����  
		}
	}
	NET *phead = NULL;
	NET *pnext = NULL;
	//�ͷű߱�  
	
	//�ͷŻ�Ծ�߱�  
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
	
	//int p[8][2];//��Ŷ���ζ˵㣬�⻷4�����ڻ�4��
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
	HPEN hpen, _hpen;//��Ӱ����仭��

	hdc = BeginPaint(hwnd, &ps);
	hpen = CreatePen(PS_SOLID, 1, BLACK_COLOR);
	_hpen = CreatePen(PS_SOLID, 1, RED_COLOR);//��Ӱ����仭�ʳ�ʼ��
	SelectObject(hdc, hpen);

	//drawPolygon(hdc, polypoint, 9, 9);
	//ScanFill(hdc);	//�޸�
	//shadowLine(hdc, polypoint, 9, 9, 10, 30);
	//COLORREF this_color = RGB(255, 255, 255);//GetPixel(hdc, 180, 180);
	//FloodFill4(hdc, 101, 200, this_color, RED_COLOR);
	//DemoHZ(Bmp001, 100, 100, BLUE_COLOR, hdc);
	DemoHZ(Bmp002, 150, 100, BLUE_COLOR, hdc);
	//DemoHZ(Bmp003, 200, 100, BLUE_COLOR, hdc);
	DeleteObject(hpen);
	SelectObject(hdc, _hpen);// ѡ����Ӱ����仭��
	DeleteObject(_hpen);
	EndPaint(hwnd, &ps);
}


/*
* ���Ĳ�,���ڹ���
*/
LRESULT CALLBACK MyWindowProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg)
	{
		// ���ڻ�����Ϣ
	case WM_PAINT:
		Paint(hwnd);
		/*
		* ����ֻ��Ҫ������������ǵ� GDI ���ƺ����Ϳ��ԣ������ط�����������
		*/
		break;
		// ���ڹر���Ϣ
	case WM_CLOSE:
		DestroyWindow(hwnd);
		break;
		// ����������Ϣ
	case WM_DESTROY:
		PostQuitMessage(0); // �����뿪��Ϣ��ϵͳ
		break;
		// ������Ϣ
	default:
		// pass ��ϵͳ���۲���
		return DefWindowProc(hwnd, msg, wParam, lParam);
	}
	return 0;
}

/*
* ��һ��,ע�ᴰ����
*/
void RegisterMyWindow(HINSTANCE hInstance)
{
	WNDCLASSEX wc;

	// 1)���ô�������
	wc.cbSize = sizeof(WNDCLASSEX);
	wc.style = 0;
	wc.lpfnWndProc = MyWindowProc; // ���õ��Ĳ��Ĵ��ڹ��̻ص�����
	wc.cbClsExtra = 0;
	wc.cbWndExtra = 0;
	wc.hInstance = hInstance;
	wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hbrBackground = (HBRUSH)(COLOR_WINDOW + 1);
	wc.lpszMenuName = NULL;
	wc.lpszClassName = (LPCWSTR)g_szClassName;
	wc.hIconSm = LoadIcon(NULL, IDI_APPLICATION);

	// 2)ע��
	if (!RegisterClassEx(&wc))
	{
		MessageBox(NULL, TEXT("����ע��ʧ��!"), TEXT("����"), MB_ICONEXCLAMATION | MB_OK);
		exit(0); // �����˳�
	}
}

/*
* �ڶ�������������
*/
HWND CreateMyWindow(HINSTANCE hInstance, int nCmdShow)
{
	HWND hwnd;
	hwnd = CreateWindowEx(
		WS_EX_CLIENTEDGE,
		(LPCWSTR)g_szClassName,
		TEXT("�ҵĴ�������"),
		WS_OVERLAPPEDWINDOW,
		CW_USEDEFAULT, CW_USEDEFAULT, 2000, 2000, // �������� x,y Ĭ�Ϸ��� ���ڿ� 400 �� 300
		NULL, NULL, hInstance, NULL);

	if (hwnd == NULL)
	{
		MessageBox(NULL, TEXT("���ڴ���ʧ��!"), TEXT("����"), MB_ICONEXCLAMATION | MB_OK);
		exit(0); // �����˳�
	}

	// ��ʾ����
	ShowWindow(hwnd, nCmdShow);
	UpdateWindow(hwnd);

	return hwnd;
}

/*
* ������
*/
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
	LPSTR lpCmdLine, int nCmdShow)
{
	HWND hwnd;
	MSG Msg;

	// ��һ��,ע�ᴰ����
	RegisterMyWindow(hInstance);
	// �ڶ���:��������
	hwnd = CreateMyWindow(hInstance, nCmdShow);
	// ������:��Ϣѭ��
	while (GetMessage(&Msg, NULL, 0, 0) > 0)
	{
		TranslateMessage(&Msg);
		DispatchMessage(&Msg);
	}
	return Msg.wParam;
}