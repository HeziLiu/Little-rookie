#include <windows.h>
#include <math.h>
#include<iostream>
using namespace std;
// ����ע��Ĵ�������
const char g_szClassName[] = "myWindowClass";
#define pi 3.1415926

#define BLACK_COLOR RGB(0, 0, 0) //��ɫ 
#define BLUE_COLOR  RGB(0,0,255) //��ɫ
#define RED_COLOR   RGB(255,0,0) //��ɫ
#define �� pi/4					 //��Ӱ��45��
#define K tan(��)                //��Ӱ��б��

//ֱ���㷨һ
void DDA(int x0, int y0, int xn, int yn, HDC hdc) {
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
void shadowLine(HDC hdc, int P[][2], int mn, int m, double h, double a) {
	const double k = 1.0;
	const double deltaB = 8.0;
	/*����ÿ����ߣ������������˵㰴Ӱ����б��K���ߵõ��Ľؾ࣬
	���н�С�ķ���B[i][0]�У��ϴ�ķ���B[i][1]��*/
	double B[8][2];
	int i, j;
	for (i = 0; (i + 1) <= (m - 1); ++i) {
		B[i][0] = (double)(P[i][1] - k*P[i][0]);
		B[i][1] = (double)(P[i + 1][1] - k*P[i + 1][0]);
		if (B[i][0]>B[i][1])
			swap(B[i][0], B[i][1]);
	}
	B[m - 1][0] = (double)(P[m - 1][1] - k*P[m - 1][0]);
	B[m - 1][1] = (double)(P[0][1] - k*P[0][0]);
	if (B[m - 1][0]>B[m - 1][1])
		swap(B[m - 1][0], B[m - 1][1]);
	for (i = m; (i + 1) <= (mn - 1); ++i) {
		B[i][0] = (double)(P[i][1] - k*P[i][0]);
		B[i][1] = (double)(P[i + 1][1] - k*P[i + 1][0]);
		if (B[i][0]>B[i][1])
			swap(B[i][0], B[i][1]);
	}
	B[mn - 1][0] = (double)(P[mn - 1][1] - k*P[mn - 1][0]);
	B[mn - 1][1] = (double)(P[m][1] - k*P[m][0]);
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
	double D[8][2];
	double xp, yp, xq, yq, x, y;
	while (b<Bmax) {
		//��ʼ��D����
		for (i = 0; i <= mn - 1; ++i)
			for (j = 0; j <= 1; ++j)
				D[i][j] = 10000.0;
		//�����Ӱ�ߺ͸���ߵĽ��㣬Ѱ��D����
		for (i = 0; (i + 1) <= (m - 1); ++i) {
			if ((B[i][0] <= b) && (b<B[i][1])) {
				xp = P[i][0];
				yp = P[i][1];
				xq = P[i + 1][0];
				yq = P[i + 1][1];
				x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
				y = k*x + b;
				D[i][0] = x;
				D[i][1] = y;
			}
		}
		if ((B[m - 1][0] <= b) && (b<B[m - 1][1])) {
			xp = P[m - 1][0];
			yp = P[m - 1][1];
			xq = P[0][0];
			yq = P[0][1];
			x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
			y = k*x + b;
			D[i][0] = x;
			D[i][1] = y;
		}
		for (i = m; (i + 1) <= (mn - 1); ++i) {
			if ((B[i][0] <= b) && (b<B[i][1])) {
				xp = P[i][0];
				yp = P[i][1];
				xq = P[i + 1][0];
				yq = P[i + 1][1];
				x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
				y = k*x + b;
				D[i][0] = x;
				D[i][1] = y;
			}
		}
		if ((B[mn - 1][0] <= b) && (b<B[mn - 1][1])) {
			xp = P[mn - 1][0];
			yp = P[mn - 1][1];
			xq = P[m][0];
			yq = P[m][1];
			x = (xp*yq - yp*xq + b*(xq - xp)) / (yq - yp - k*(xq - xp));
			y = k*x + b;
			D[i][0] = x;
			D[i][1] = y;
		}
		//��X��������D�����еĽ���
		bubbleSort(D, 8);
		//��˳�򣬲��������㵽ż����֮����߶�
		for (int k = 0; (D[2 * k][0] != 10000) && (D[2 * k + 1][0] != 10000); k++) {
			DDA(D[2 * k][0], D[2 * k][1], D[2 * k + 1][0], D[2 * k + 1][1], hdc);
		}
		//ȡ��һ��Ӱ����
		b = b + deltaB;
	}
}

//������������
void drawPolygon(HDC hdc, int p[][2], int mn, int m) {
	int i;
	for (i = 0; (i + 1) <= (m - 1); ++i)
		DDA(p[i][0], p[i][1], p[i + 1][0], p[i + 1][1], hdc);
	DDA(p[0][0], p[0][1], p[m - 1][0], p[m - 1][1], hdc);
	for (i = m; (i + 1) <= (mn - 1); ++i)
		DDA(p[i][0], p[i][1], p[i + 1][0], p[i + 1][1], hdc);
	DDA(p[m][0], p[m][1], p[mn - 1][0], p[mn - 1][1], hdc);
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

void Paint(HWND hwnd)
{
	int p[8][2];//��Ŷ���ζ˵㣬�⻷4�����ڻ�4��
	p[0][0] = 50; p[0][1] = 200;
	p[1][0] = 200; p[1][1] = 200;
	p[2][0] = 200; p[2][1] = 50;
	p[3][0] = 50; p[3][1] = 50;
	p[4][0] = 100; p[4][1] = 100;
	p[5][0] = 100; p[5][1] = 150;
	p[6][0] = 150; p[6][1] = 150;
	p[7][0] = 150; p[7][1] = 100;
	PAINTSTRUCT ps;
	HDC hdc;
	HPEN hpen, _hpen;//��Ӱ����仭��

	hdc = BeginPaint(hwnd, &ps);
	hpen = CreatePen(PS_SOLID, 1, BLACK_COLOR);
	_hpen = CreatePen(PS_SOLID, 1, RED_COLOR);//��Ӱ����仭�ʳ�ʼ��
	SelectObject(hdc, hpen);

	//drawPolygon(hdc, p, 8, 4);
	//shadowLine(hdc, p, 8, 4, 10, 30);
	COLORREF this_color = RGB(255, 255, 255);//GetPixel(hdc, 180, 180);
	FloodFill4(hdc, 101, 200, this_color, RED_COLOR);

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
		CW_USEDEFAULT, CW_USEDEFAULT, 900, 800, // �������� x,y Ĭ�Ϸ��� ���ڿ� 400 �� 300
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