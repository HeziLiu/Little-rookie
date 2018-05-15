import java.util.Random;
import java.util.Scanner;

public class element_select {
    private static int[] nums={-20,11,-4,13,-5,-2};
    private static int[] nums_copy={-20,11,-4,13,-5,-2};//用于对照的原数组
    private static Random rand=new Random();
    public static void  main(String args[]){
        for (int i=0;i<nums.length;i++){
            System.out.print(nums[i]+" ");
        }
        System.out.println();
        System.out.println("求这组数第几小的数？");
        Scanner sc=new Scanner(System.in);
        int k=sc.nextInt();
        System.out.println(RandomFind(0,nums.length-1,k));
    }

    private static int RandomFind( int p, int r, int k) {
        if (p==r){
            for(int i=0;i<nums_copy.length;i++){
                if (nums_copy[i]==nums[p])
                    System.out.print(" 第"+k+"小的数在数组第"+(i+1)+"位，值为：");
            }
            return nums[p];
        }

        int q=RandomPartition(p,r);
        if ((q+1)==k)
            return nums[q];
        else if ((q+1)<k)
            return RandomFind(q+1,r,k);
        else
            return RandomFind(p,q-1,k);
    }

    private static int RandomPartition( int p, int r) {
        int pivot=rand.nextInt(r-p+1)+p;
        int x=nums[pivot];
        int i=p,j=r;
        while (true){
            while (nums[i]<x)
                i++;
            while(nums[j]>x)
                j--;
            if (i<j){
                int temp=nums[i];
                nums[i]=nums[j];
                nums[j]=temp;
            }else
                return j;
        }
    }
}
