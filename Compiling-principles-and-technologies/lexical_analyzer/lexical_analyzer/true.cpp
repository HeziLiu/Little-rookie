#include<stdio.h>

int main() {
	int a = 6;
	float b = 6.54;
	double c = 6.54e-10;
	a *= b-c;
	c /= b;
	//  /output %&*()
	/*  the result****/
	printf("%d", a);
	return 0;
}
