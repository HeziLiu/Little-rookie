import java.util.Scanner;
public class BagPro {
    static int flag=0;//标记可获得最大价值的可行方案下标
    public static class goods{
        private int weight;
        private int value;
        goods(int weight, int value){//构造方法
            this.weight=weight;
            this.value=value;
        }
    }
    //主函数
    public static void main(String args[]){
        int[][] s=new int[1024][10];//存放子集的二维数组，最大容量为10，物品组合有2^10种
        int[] mark=new int[1024];//用来标记可行的方案，可行，数组内容为当前方案价值，不可行则为0
        int n,max_v,c;//n为物品个数，max_v为最大总价值，c为背包容量，flag为可行方案在s数组中的下标
        //初始化对象数组
        goods[] goods= {new goods(0,0),new goods(0,0),new goods(0,0),new goods(0,0),new goods(0,0),
                        new goods(0,0),new goods(0,0),new goods(0,0),new goods(0,0),new goods(0,0)};
        System.out.println("请输入背包的容量：");
        Scanner sc=new Scanner(System.in);
        c=sc.nextInt();
        System.out.println("请输入物品的个数：");
        n=sc.nextInt();
        System.out.println("请分别输入物品的重量：");
        for (int i=0;i<n;i++){
            goods[i].weight=sc.nextInt();
        }
        System.out.println("请分别输入物品的价值:");
        for (int i=0;i<n;i++){
            goods[i].value=sc.nextInt();
        }
        sc.close();
        subnet(s,n);
        judge(s,goods,mark,n,c);
        max_v=getmax(mark,n);
        output(flag,s,n);
        System.out.println("背包可容纳的最大价值为："+max_v);
    }
//输出可行方案的装入物品编号
    private static void output(int flag, int[][] s, int n) {
        System.out.print("装入背包物品的编号为：");
        for (int i=0;i<n;i++){
            if (s[flag][i]==1){
                System.out.print((i+1)+" ");
            }
        }
        System.out.println();
    }
//判断所有方案是否可行
    private static void judge(int[][] s, goods[] goods, int[] mark, int n, int c) {
        int v,w;
        for (int i=0;i<Math.pow(2,n);i++){
            v=0;w=0;
            for (int j=0;j<n;j++){
                w+=goods[j].weight*s[i][j];
                v+=goods[j].value*s[i][j];
            }
            if (w<=c)
                mark[i]=v;
            else
                mark[i]=0;
        }
    }
//求得物品放入的所有组合
    private static void subnet(int[][] s, int n) {
        int k, m;
        for (int i = 0; i < Math.pow(2, n); i++) {
            k = i;
            for (int j = n - 1; j >= 0; j--) {
                //if((s[i][0]&&s[i][3])||(s[i][2]&&s[i][3])||(s[i][4]&&s[i][6])||(s[i][0]&&s[i][6])||(s[i][2]&&s[i][4])||(s[i][5]&&s[i][6])){
                // break;}
                m = k % 2;
                s[i][j] = m;
                k = k / 2;
            }
        }
    }
    //求可行方案中的最优价值
    private static int getmax(int[] mark,int n){
        int max=0;
        for (int i=0;i<Math.pow(2,n);i++){
            if (mark[i]>max){
                max=mark[i];
                flag=i;
            }
        }
        return max;
    }
}
