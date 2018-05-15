import static java.lang.Integer.max;
public class Knapsack {
    private static int n=5;//物品数量
    private static int c=10;//背包容量
    private static int w[]={2,2,6,5,4};//重量
    private static int v[]={6,3,5,4,6};//价值
    private static int x[]={0,0,0,0,0};//解集合，放入为1，反之为0
    public static void main(String args[]){
        System.out.println("物品"+n+"个，背包容量10，利用动态规划求解：");
        System.out.println("重量 价值");
        for (int i=0;i<n;i++){
            System.out.println(w[i]+"    "+v[i]);
        }
        BP_solution();
    }

    private static void BP_solution() {
        int V[][]=new int[n+1][c+1];
        int i,j;
        //初始化条件
        for (i=0;i<=n;i++)//前n个物品放入容量为0的背包
            V[i][0]=0;
        for (j=0;j<=c;j++)//前0个物品放入容量为c的背包
            V[0][j]=0;
        for (i=1;i<=n;i++)
            for (j=1;j<=c;j++) {
                if (j < w[i-1])
                    V[i][j] = V[i - 1][j];//小于容量则不装入价值和上一层相同
                else
                    V[i][j]=max(V[i-1][j],V[i-1][j-w[i-1]]+v[i-1]);
            }
         //向回求解放入的物品
        for (j=c,i=n;i>0;i--){
            if (V[i][j]>V[i-1][j]){
                x[i-1]=1;j=j-w[i-1];
            }else
                x[i-1]=0;
        }
        System.out.println();
        for (i=0;i<n;i++){
            System.out.print(x[i]+" ");
        }
    }
}
