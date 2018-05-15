import java.util.Scanner;

public class Gasoline {
    private static void greedy(int A[],int d,int n){
        int num=0;
        for (int i=0;i<n;i++){
            if (A[i]>d){
                System.out.println("目的地不可达");
                return;
            }
        }
        for (int i=0,s=0;i<n;i++){
            s+=A[i];
            if (s>n){
                num++;
                s=A[i];
            }
        }
        System.out.println(num);
    }
    public static void main(String args[]){
        int d,n;
        System.out.println("请分别输入汽车可行驶路程d和加油站个数n：");
        Scanner input=new Scanner(System.in);
        d=input.nextInt();
        n=input.nextInt();
        int A[]=new int[n];
        System.out.println("请分别输入"+n+"个加油站之间的距离：");
        for (int i=0;i<n;i++){
            A[i]=input.nextInt();
        }
        input.close();
        System.out.println("结果：");
        greedy(A,d,n);
    }
}
