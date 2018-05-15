import java.util.Scanner;
public class Fish_Buying {
    static int flag=0;
    public static class fish{
        private int num;//鱼的编号
        private int value;//鱼的价值
        fish(int num,int value){
            this.value=value;
            this.num=num;
        }
    }
    public static void main(String args[]){
        int[][] s=new int[1024][10];//存放子集，最大容量10共有1024种组合
        int [] mark=new int[1024];//标记可行的方案
        int[][] not_together=new int[45][2];// 两两不能共存的鱼类的集合
        int n,max_value,money;//n为总共的鱼的种类，max_value为最大价值总和，money为所持有资金
        int a=1,b=1,c=0;//临时变量，ab用于判断输入不能相处鱼类的结束状态,c是not_together数组下标
        fish fishs[]={
                new fish(0,0),new fish(0,0),new fish(0,0),new fish(0,0),new fish(0,0),
                new fish(0,0),new fish(0,0),new fish(0,0),new fish(0,0),new fish(0,0)
        };
        System.out.println("请输入所持资金和鱼的种类（以空格间隔开来）：");
        Scanner input=new Scanner(System.in);
        money=input.nextInt();
        n=input.nextInt();
        System.out.println("请分别输入鱼的编号以及价值（以空格间隔开来）：");
        for (int i=0;i<n;i++){
            fishs[i].num=input.nextInt();
            fishs[i].value=input.nextInt();
        }
        System.out.println("请输入不能合处的鱼类（输入0 0表示结束输入）：");
        a=input.nextInt();b=input.nextInt();
        while (a!=0&&b!=0){
            not_together[c][0]=a;
            not_together[c][1]=b;
            c++;
            a=input.nextInt();b=input.nextInt();
        }
        input.close();
        subnet(s,n,not_together,c);
        judge(s,n,mark,fishs,money);
        max_value=getmax(mark,n);
        output(s,flag,n,fishs);
        System.out.print("花费  "+max_value);
    }
    private static void output(int[][] s, int flag, int n,fish[] fishs) {
        System.out.print("买到的鱼的编号为：");
        int count=0;
        for (int i=0;i<n;i++){
            if(s[flag][i]==1){
                count++;
                System.out.print(fishs[i].num+" ");
            }
        }
        System.out.print("共"+count+"条鱼，");
    }
    private static int getmax(int[] mark, int n) {
        int max=0;
        for (int i=0;i<Math.pow(2,n);i++){
            if (mark[i]>max){
                max=mark[i];
                flag=i;
            }
        }
        return max;
    }
    private static void judge(int[][] s, int n, int[] mark, fish[] fishs, int money) {
        int cost;
        for (int i=0;i<Math.pow(2,n);i++){
            cost=0;
            for (int j=0;j<n;j++){
                cost+=fishs[j].value*s[i][j];
            }
            if (cost<money)
                mark[i]=cost;
            else
                mark[i]=0;
        }
    }
    private static void subnet(int[][] s, int n, int[][] not_together,int c) {
        int k,m;
        for (int i=0;i<Math.pow(2,n);i++){
            k=i;
            for (int j=n-1;j>=0;j--){
                m=k%2;
                s[i][j]=m;
                k=k/2;
            }
            for (int z=0;z<c;z++){
                if (s[i][not_together[z][0]-1]==1&&s[i][not_together[z][1]-1]==1){
                    for (int j=0;j<n;j++){
                        s[i][j]=0;
                    }
                }
            }
        }
    }
}
