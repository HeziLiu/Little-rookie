import java.util.Arrays;
import java.util.Scanner;

public class Post_locate {
    public static void main(String args[]){
        int n;
        System.out.println("请输入居民点个数：");
        Scanner input=new Scanner(System.in);
        n=input.nextInt();
        int[] x=new int[n];
        int[] y=new int[n];
        System.out.println("请分别输入"+n+"个居民点的坐标：");
        for(int i=0;i<n;i++){
            x[i]=input.nextInt();
            y[i]=input.nextInt();
        }
        input.close();
        Arrays.sort(x);
        Arrays.sort(y);
        double mid_x,mid_y;
        if (n%2==0){
            mid_x=(x[n/2-1]+x[n/2])/2;
            mid_y=(y[n/2-1]+y[n/2])/2;
        }else {
            mid_x=x[n/2];
            mid_y=y[n/2];
        }
        double sum=0;
        for (int i=0;i<n;i++){
            sum+=Math.abs(y[i]-mid_y);
            sum+=Math.abs(x[i]-mid_x);
        }
        System.out.println(n+"个居民点到邮局的距离总和最小值为："+sum);
    }
}
