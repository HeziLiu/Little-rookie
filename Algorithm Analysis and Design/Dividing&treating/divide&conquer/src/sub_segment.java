public class sub_segment {
    private static int begin=0;
    private static int end=0;
    public static  void main(String args[]){
        int[] a={-20,11,-4,13,-5,-2};
        for (int anA : a) {
            System.out.print(anA + " ");
        }
        System.out.println();
        System.out.println("最大子段和为 "+MaxSum(a,0,5));
        System.out.println("从第"+(begin+1)+"个元素到第"+(end+1)+"个元素：");
    }

    private static int MaxSum(int[] a, int left, int right) {
        int sum,midSum,leftSum,rightSum;
        int center,s1,s2,lefts,rights;
        if (left==right){
            sum=a[left];//序列长度为1直接求解
        }else {
            center=(left+right)/2;
            leftSum=MaxSum(a,left,center);
            rightSum=MaxSum(a,center+1,right);
            s1=0;lefts=0;
            for (int i=center;i>=left;i--){
                lefts+=a[i];
                if (lefts>s1){
                    s1=lefts;
                    begin=i;
                }
            }
            s2=0;rights=0;
            for (int j=center+1;j<right;j++){
                rights+=a[j];
                if (rights>s2){
                    s2=rights;
                    end=j;
                }
            }
            midSum=s1+s2;
            if (midSum<leftSum)
                sum=leftSum;
            else sum=midSum;
            if (sum<rightSum)
                sum=rightSum;
        }
        return sum;
    }
}
