import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class q_learning {
    private FeedbackMatrix R=new FeedbackMatrix();
    private ExperienceMatrix Q=new ExperienceMatrix();
    public static void main(String args[]) {
        q_learning ql=new q_learning();
        for (int i=0;i<500;i++){
            Random random=new Random();
            int x=random.nextInt(100)%8;//随机产生初始房间号
            System.out.println("第"+(i+1)+"次学习，初始房间是"+x);
            ql.learn(x);
            System.out.println();
        }
    }

    private void learn(int x) {
        do {
            //随机选择一个联通的房间进入
            int y=chooseRandomRY(x);
            //获取以进入的房间为起点的历史最佳得分
            int qy=getMaxQY(y);
            //计算此次移动的得分
            int value=calculateNewQ(x,y,qy);
            Q.set(x,y,value);
            x=y;//y房间作为下一次探索的起点
        }
        //走出房间则学习结束
        while (4!=x);
        Q.print();
    }

    private int chooseRandomRY(int x) {
        int[] qRow=R.getRow(x);
        List<Integer> yValues= new ArrayList<>();
        for (int i=0;i<qRow.length;i++){
            if (qRow[i]>=0){
                yValues.add(i);
            }
        }
        Random random=new Random();
        int i=random.nextInt(yValues.size())%yValues.size();
        return yValues.get(i);
    }

    private int getMaxQY(int x) {
        int[] qRow=Q.getRow(x);
        int length=qRow.length;
        List<YAndValue> yValues= new ArrayList<>();
        for (int i=0;i<length;i++){
            YAndValue yv=new YAndValue(i,qRow[i]);
            yValues.add(yv);
        }
        Collections.sort(yValues);
        int num=1;
        int value=yValues.get(0).getValue();
        //取得优先队列中价值最大的一个点
        for (int i=1;i<length;i++){
            if (yValues.get(i).getValue()==value)
                num=i+1;
            //若有多个价值最大的点，则随机选择一个
            else
                break;
        }
        Random random=new Random();
        int i=random.nextInt(num)%num;
        return yValues.get(i).getY();
    }
    //Q(x,y)=R(x,y)+0.8*max(Q(y,i))
    private int calculateNewQ(int x, int y, int qy) {
        return (int) (R.get(x,y)+0.8*Q.get(y,qy));

    }
    //优先队列，对每个房间到达各个房间的价值作降序排序
    public static class YAndValue implements Comparable<YAndValue>{
        int y,value;
        int getY() {
            return y;
        }
        /*public void setY(int y) {
            this.y = y;
        }*/
        int getValue() {
            return value;
        }
        YAndValue(int y, int value){
            this.y=y;
            this.value=value;
        }
        @Override
        public int compareTo(YAndValue o) {
            return o.getValue()-this.value;
        }
    }
}
