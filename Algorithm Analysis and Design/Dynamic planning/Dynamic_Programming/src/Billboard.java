import static java.lang.Integer.max;
public class Billboard {
    private static int[] e={0,0,0,0,0};//e[j]表示编号比j小且距xj大于5英里的最东边的地点
    private static int[] opt={0,0,0,0,0};//opt[j]表示从x1,...,xj中地点的最优子集得到的收益
    private static int[] pre={-1,-1,-1,-1,-1};//用于记录与当前放置广告牌的前一个可放置广告牌

    public static void main(String args[]){
        int M=20,n=4;//距离20，4个广告牌
        int[] x={0,6,7,12,14};//广告牌坐标
        int[] r={0,5,6,5,1};  //广告牌价值
        select(n,x,r);
    }

    private static void select(int n, int[] x, int[] r) {
        for (int j = 2; j <= n; j++) {
            for (int i = 1; i < j; i++) {
                int distance = 5;
                if (x[j] - x[i] > distance){
                    e[j]++;
                    pre[j]=i;
                }
            }
        }
        for (int j=1;j<=n;j++){
            opt[j]=max(r[j]+opt[e[j]],opt[j-1]);
        }
        for (int j=n;j>0;j--){
            if (opt[j]>opt[j-1]&&pre[j]>0){
                System.out.print("x"+j+" x"+pre[j]);
            }
        }
    }
}
