import java.util.Scanner;

public class BackTracking {
    public static class good{
        private int weight;
        private int value;
        good(int weight, int value){
            this.weight=weight;
            this.value=value;
        }
    }
    private static int bestvalue;
    private static int[] flag=new int[10];
    private static int[] solu=new int[10];
    public static void main(String args[]){
        int n;//物品个数
        int c;//背包容量
        int cw=0,cp=0;
        System.out.println("请输入物品个数以及背包容量：");
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();c=sc.nextInt();
        good[] goods=new good[n];
        for (int i=0;i<n;i++){
            goods[i]= new good(0,0);
        }
        System.out.println("请分别输入物品的重量和价值");
        for (int i=0;i<n;i++){
            goods[i].weight=sc.nextInt();
            goods[i].value=sc.nextInt();
        }
        sc.close();
        back_track(0,cw,cp,n,c,goods);
        System.out.println("最佳方案为（0：放入 1：不放入）：");
        for (int i=0;i<n;i++){
            System.out.print(solu[i]+" ");
        }
        System.out.println("总价值为："+bestvalue);
    }

    private static void back_track(int i, int cw, int cp,int n,int c,good goods[]) {
        if (i>n-1){
            if (cp>bestvalue){
                bestvalue=cp;
                System.arraycopy(flag, 0, solu, 0, n);
            }
        }else{
            for (int j=1;j>=0;j--){
                flag[i]=j;
                if (cw+flag[i]*goods[i].weight<=c){
                    cw+=goods[i].weight*flag[i];
                    cp+=goods[i].value*flag[i];
                    back_track(i+1,cw,cp,n,c,goods);
                    cw-=goods[i].weight*flag[i];
                    cp-=goods[i].value*flag[i];
                }
            }
        }
    }
}
