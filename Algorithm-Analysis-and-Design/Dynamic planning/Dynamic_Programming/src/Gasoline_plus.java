import java.util.Scanner;

public class Gasoline_plus {
    private static int N,A,C,B,K;
    private static int[][][] f=new int[50][50][2];
    private static int[][] s={{-1,0,0},{0,-1,0},{1,0,B},{0,1,B}};
    private static int[][] a=new int[50][50];//输入的方形网络
    private static int INF=10000;
    private static int sol(){
        int i,j,k;
        //初始化参数
        for (i=1; i<=N; i++){
            for (j=1;j<=N;j++){
                f[i][j][0]= INF;
                f[i][j][1]=K;
            }
        }
        f[1][1][0]=0;
        f[1][1][1]=K;
        int count=1;
        int tx,ty;
        while (count>0){
            count=0;
            for (i=1;i<=N;i++){
                for (j=1;j<=N;j++){
                    if (i==1&&j==1)
                        continue;
                    int minStep= INF;
                    int minDstep=0,step,dstep;
                    for (k=0;k<4;k++){//可行驶的四个方向
                        tx=i+s[k][0];
                        ty=j+s[k][1];
                        if (tx<1||ty<1||tx>N||ty>N)//出界
                            continue;
                        step=f[tx][ty][0]+s[k][2];
                        dstep=f[tx][ty][1]-1;
                        if (a[i][j]==1){//若是油库
                            step+=A;
                            dstep=K;
                        }
                        if (a[i][j]==0&&dstep==0&&(i!=N||j!=N)){//若不是油库，且油已耗光
                            step+=A+C;
                            dstep=K;
                        }
                        if (step<minStep){
                            minStep=step;
                            minDstep=dstep;
                        }
                    }
                    if (f[i][j][0]>minStep){
                        count++;
                        f[i][j][0]=minStep;
                        f[i][j][1]=minDstep;
                    }
                }
            }
        }
        return f[N][N][0];
    }
    public static void main(String args[]){
        System.out.println("请分别输入N(方格规模)，K(装满油后能行驶的网格边数)，\nA(加油费)，B(坐标减少时应付油费)，C(增设油库费用):");
        Scanner input=new Scanner(System.in);
        N=input.nextInt();K=input.nextInt();A=input.nextInt();B=input.nextInt();C=input.nextInt();
        s[2][2]=s[3][2]=B;
        System.out.println("输入方格：");
        for (int i=1;i<=N;i++){
            for (int j=1;j<=N;j++){
                a[i][j]=input.nextInt();
            }
        }/*输入为：
9 3 2 3 6

0 0 0 0 1 0 0 0 0
0 0 0 1 0 1 1 0 0
1 0 1 0 0 0 0 1 0
0 0 0 0 0 1 0 0 1
1 0 0 1 0 0 1 0 0
0 1 0 0 0 0 0 1 0
0 0 0 0 1 0 0 0 1
1 0 0 1 0 0 0 1 0
0 1 0 0 0 0 0 0 0*/
        input.close();
        System.out.println(sol());
    }
}
