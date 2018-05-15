import java.util.PriorityQueue;

public class Knapsack {
    static class PackNode implements Comparable<PackNode>{//pt表的节点类型
        double weight,value,upvalue; //upvalue是评估值
        int left,level;             //left左节点标志，若左节点可行则为1，反之则为0 level表示当前搜索深度
        PackNode father;            //父节点
        @Override                   //使节点在队列里按upvalue值排列
        public int compareTo(PackNode o) {
            return Double.compare(o.upvalue, this.upvalue);
        }
    }
    private static int n=4,c=10;
    private static double up,down;
    private static double[] w={3,5,7,4};
    private static double[] v={12,25,42,40};
    private static int[] x=new int[n];

    public static void main(String args[]){
        sort();
        for (int i=0;i<n;i++){
            System.out.println("物品"+i+": w:"+w[i]+" v:"+v[i]);
        }
        System.out.println();
        up=calc_up();   //计算上界
        down=calc_down();//用贪心法计算下届
        brand_bound();//分支限界方法
    }

    private static void brand_bound() {
        double maxValue=0;
        PriorityQueue<PackNode> pt= new PriorityQueue<>();
        PackNode root=new PackNode();
        root.level=-1;
        root.upvalue=up;
        pt.add(root);
        while (!pt.isEmpty()){
            PackNode fatherNode=pt.poll();//取出pt节点中ub值最大的（即在优先队列中的第一个）
            if (fatherNode.level==n-1){
                if (fatherNode.value>maxValue){
                    maxValue=fatherNode.value;
                    for (int i=n-1;i>=0;i--){
                        x[i]=fatherNode.left;
                        fatherNode=fatherNode.father;
                    }
                }
            }
            else {
                if (w[fatherNode.level+1]+fatherNode.weight<=c){
                    PackNode newNode=new PackNode();
                    newNode.level=fatherNode.level+1;
                    newNode.value=fatherNode.value+v[fatherNode.level+1];
                    newNode.weight=fatherNode.weight+w[fatherNode.level+1];
                    newNode.upvalue=Bound(newNode);
                    newNode.father=fatherNode;

                    newNode.left=1;
                    if (newNode.upvalue>=down)
                        pt.add(newNode);
                }//向右搜索节点,
                if (fatherNode.value+(c-fatherNode.weight)*(v[fatherNode.level+1]/w[fatherNode
                        .level+1])>=down){
                    PackNode newNode2=new PackNode();
                    newNode2.level=fatherNode.level+1;
                    newNode2.value=fatherNode.value;
                    newNode2.weight=fatherNode.weight;
                    newNode2.father=fatherNode;
                    newNode2.upvalue=fatherNode.value+(c-fatherNode.weight)*(v[fatherNode.level+1]/w[fatherNode
                            .level+1]);
                    newNode2.left=0;
                    pt.add(newNode2);
                }
            }
        }
        System.out.println("分支限界求解最大价值为："+maxValue);
        System.out.println("取出的物品为：");
        for (int i=0;i<n;i++){
            if (x[i]==1)
                System.out.print("物品"+i+" ");
        }
    }
//左子树延伸
    private static double Bound(PackNode node) {
        double maxLeft=node.value;
        double lw=c-node.weight;
        int tempLevel=node.level;
        while (tempLevel<=n-1&&lw>w[tempLevel]){
            lw-=w[tempLevel];
            maxLeft+=v[tempLevel];
            tempLevel++;
        }
        //不能装时 用下一个物品的单位重量折算剩余空间
        if (tempLevel<=n-1){
            maxLeft+=v[tempLevel]/w[tempLevel]*lw;
        }
        return maxLeft;
    }
//按物品单位价值排序
    private static void sort() {
        double tw,tv;
        for (int i=0;i<n-1;i++){
            for (int j=i+1;j<n;j++){
                if (v[i]/w[i]<v[j]/w[j]){
                    tw=w[i];
                    tv=v[i];
                    w[i]=w[j];
                    v[i]=v[j];
                    w[j]=tw;
                    v[j]=tv;
                }
            }
        }
    }
//计算下界
    private static double calc_down() {
        double maxValue=0;
        double now_w=0;
        for (int i=0;i<n;i++) {
            if (now_w+w[i]<c){
                maxValue += v[i];
                now_w += w[i];
            }
        }
        return maxValue;
    }
//计算上界
    private static double calc_up() {
        return v[0]/w[0]*c;
    }
}
