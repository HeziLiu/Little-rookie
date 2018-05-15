import java.util.PriorityQueue;

public class task_problem {
    private static int[][] task={{9,2,7,8},
                         {6,4,3,7},
                         {5,8,1,8},
                         {7,6,9,4}};
    private static int up;
    private static int down;
    private static int[] solu={0,0,0,0};
    private static int[] down_col={0,0,0,0};
    static class PTNode implements Comparable<PTNode>{
        int lb_value;
        int level;
        int job_num;
        PTNode(int lb_value,int level,int job_num,PTNode father){
            this.level=level;
            this.job_num=job_num;
            this.lb_value=lb_value;
            this.father=father;
        }
        PTNode father;
        @Override
        public int compareTo(PTNode o) {//队列总按lb值进行从大到小排列
            return Integer.compare(o.lb_value,this.lb_value);
        }
    }
    public static void main(String args[]){
        System.out.println("任务分配成本矩阵：");
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                System.out.print(task[i][j]+" ");
            }
            System.out.println();
        }
        down=calc_down();//计算假想下界
        up=calc_up();   //贪心法计算上界
        brand_bound();  //分支限界法
    }

    private static void brand_bound() {
        int max_lb=0;
        PriorityQueue<PTNode> pt=new PriorityQueue<>();//建立优先队列pt
        PTNode root=new PTNode(down,-1,-1,null);
        pt.add(root);
        int [] col_flag={0,0,0,0};
        while (!pt.isEmpty()){
            PTNode fatherNode=pt.poll();//取出顶端节点
            if (pt.size()>=1){          //由于队列中存在多个pt节点，所以标志数组应当重新赋值
                PTNode fn1=new PTNode(fatherNode.lb_value,fatherNode.level,fatherNode.job_num,fatherNode.father);
                for(int a=0;a<4;a++){
                    col_flag[a]=0;
                }
                while (fn1.level>=0){
                    col_flag[fn1.job_num]=1;
                    fn1=fn1.father;
                }
            }
            if (fatherNode.level==3){   //分配到最后一个物品
                if (fatherNode.lb_value>max_lb){
                    max_lb=fatherNode.lb_value;
                    for (int i=fatherNode.level;i>=0;i--){
                        solu[i]=fatherNode.job_num+1;
                        fatherNode=fatherNode.father;
                    }
                }
            }
            else {
                int k=fatherNode.level+2;//k用于定位成本矩阵第k行的最小值
                for (int j=0;j<4;j++){
                    if (col_flag[j]==0){ //判断任务是否被分配
                        int t_lb=calc_lb(k,fatherNode.level+1,j,fatherNode);
                        if (t_lb<=up&&t_lb>=down){    //在区间内则生成新节点
                            PTNode newnode=new PTNode(t_lb,fatherNode.level+1,j,fatherNode);
                            col_flag[j]=1;
                            pt.add(newnode);
                        }
                    }
                }
            }
        }
        System.out.println("任务分配情况如下：");
        for (int i=0;i<4;i++){
            System.out.print((i+1)+": 任务"+solu[i]+"  ");
        }
        System.out.println();
        System.out.print("最优分配成本："+max_lb);
    }
    //计算lb值
    private static int calc_lb(int k,int i,int j,PTNode fathernode) {
        int _lb=task[i][j];
        PTNode _fn=new PTNode(fathernode.lb_value,fathernode.level,fathernode.job_num,fathernode.father);
        while (_fn.level>=0){//向上遍历获得父节点的任务成本值
            _lb+=task[_fn.level][_fn.job_num];
            _fn=_fn.father;
        }
        for (;k<4;k++){
            _lb+=down_col[k];//加上第k行的最小值
        }
        return _lb;
    }
    //计算上界
    private static int calc_up() {
        int up_v=0,temp;
        int[] flag={0,0,0,0};
        for (int i=0;i<4;i++){
            temp=task[i][0];
            for (int j=0;j<4;j++){
                if (task[i][j]<temp){
                    if (flag[j]==0){
                        temp=task[i][j];
                        flag[j]=1;
                    }
                }
            }
            up_v+=temp;
        }
        return up_v;
    }
    //计算下届
    private static int calc_down() {
        int temp,down_v=0;
        for (int i=0;i<4;i++){
            temp=task[i][0];
            for (int j=0;j<4;j++){
                if (task[i][j]<temp){
                    temp=task[i][j];
                }
            }
            down_v+=temp;
            down_col[i]=temp;
        }
        return down_v;
    }
}
