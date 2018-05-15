
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class EightPuzzle implements Comparable{
    private int[] num = new int[9];
    private int depth;           //当前的深度即走到当前状态的步骤
    private int evaluation;      //从起始状态到目标的最小估计值
    private int misposition;     //到目标的最小估计
    private EightPuzzle parent;  //当前状态的父状态
    private int[] getNum() {
        return num;
    }
    private void setNum(int[] num) {
        this.num = num;
    }
    private int getDepth() {
        return depth;
    }
    private void setDepth(int depth) {
        this.depth = depth;
    }
    private int getEvaluation() {
        return evaluation;
    }
    private void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }
    private int getMisposition() {
        return misposition;
    }
    private void setMisposition(int misposition) {
        this.misposition = misposition;
    }
    private EightPuzzle getParent() {
        return parent;
    }
    public void setParent(EightPuzzle parent) {
        this.parent = parent;
    }

    /**
     * 判断当前状态是否为目标状态
     * @param target
     * @return
     */
    private boolean isTarget(EightPuzzle target){
        return Arrays.equals(getNum(), target.getNum());
    }

    /**
     * 求f(n) = g(n)+h(n);
     * 初始化状态信息
     * @param target
     */
    private void init(EightPuzzle target){
        int temp = 0;
        for(int i=0;i<9;i++){
            if(num[i]!=target.getNum()[i])
                temp++;
        }
        this.setMisposition(temp);
        if(this.getParent()==null){
            this.setDepth(0);
        }else{
            this.depth = this.parent.getDepth()+1;
        }
        this.setEvaluation(this.getDepth()+this.getMisposition());
    }

    /**
     * 求逆序值并判断是否有解
     * @param target
     * @return 有解：true 无解：false
     */
    private boolean isSolvable(EightPuzzle target){
        int reverse = 0;
        for(int i=0;i<9;i++){
            for(int j=0;j<i;j++){
                if(num[j]>num[i])
                    reverse++;
                if(target.getNum()[j]>target.getNum()[i])
                    reverse++;
            }
        }
        return reverse % 2 == 0;
    }
    @Override
    public int compareTo(Object o) {
        EightPuzzle c = (EightPuzzle) o;
        return this.evaluation-c.getEvaluation();//默认排序为f(n)由小到大排序
    }
    /**
     * @return 返回0在八数码中的位置
     */
    private int getZeroPosition(){
        int position = -1;
        for(int i=0;i<9;i++){
            if(this.num[i] == 0){
                position = i;
            }
        }
        return position;
    }
    /**
     *
     * @param open    状态集合
     * @return 判断当前状态是否存在于open表中
     */
    private int isContains(ArrayList<EightPuzzle> open){
        for(int i=0;i<open.size();i++){
            if(Arrays.equals(open.get(i).getNum(), getNum())){
                return i;
            }
        }
        return -1;
    }
    /**
     *
     * @return 小于3的不能上移返回false
     */
    private boolean isMoveUp() {
        int position = getZeroPosition();
        return position > 2;
    }
    /**
     *
     * @return 大于6返回false
     */
    private boolean isMoveDown() {
        int position = getZeroPosition();
        return position < 6;
    }
    /**
     *
     * @return 0，3，6返回false
     */
    private boolean isMoveLeft() {
        int position = getZeroPosition();
        return position % 3 != 0;
    }
    /**
     *
     * @return 2，5，8不能右移返回false
     */
    private boolean isMoveRight() {
        int position = getZeroPosition();
        return (position) % 3 != 2;
    }
    /**
     *
     * @param move 0：上，1：下，2：左，3：右
     * @return 返回移动后的状态
     */
    private EightPuzzle moveUp(int move){
        EightPuzzle temp = new EightPuzzle();
        int[] tempnum = num.clone();
        temp.setNum(tempnum);
        int position = getZeroPosition();    //0的位置
        int p=0;                            //与0换位置的位置
        switch(move){
            case 0:
                p = position-3;
                temp.getNum()[position] = num[p];
                break;
            case 1:
                p = position+3;
                temp.getNum()[position] = num[p];
                break;
            case 2:
                p = position-1;
                temp.getNum()[position] = num[p];
                break;
            case 3:
                p = position+1;
                temp.getNum()[position] = num[p];
                break;
        }
        temp.getNum()[p] = 0;
        return temp;
    }
    /**
     * 按照八数码的格式输出
     */
    private void print(){
        for(int i=0;i<9;i++){
            if(i%3 == 2){
                System.out.println(this.num[i]);
            }else{
                System.out.print(this.num[i]+"  ");
            }
        }
    }
    /**
     * 反序列的输出状态
     */
    private void printRoute(){
        EightPuzzle temp;
        int count = 0;
        temp = this;
        while(temp!=null){
            temp.print();
            System.out.println("----------分割线----------");
            temp = temp.getParent();
            count++;
        }
        System.out.println("步骤数："+(count-1));
    }
    /**
     *
     * @param open open表
     * @param close close表
     * @param parent 父状态
     * @param target 目标状态
     */
    private void operation(ArrayList<EightPuzzle> open, ArrayList<EightPuzzle> close, EightPuzzle parent, EightPuzzle target){
        if(this.isContains(close) == -1){
            int position = this.isContains(open);
            if(position == -1){
                this.parent = parent;
                this.init(target);
                open.add(this);
            }else{
                if(this.getDepth() < open.get(position).getDepth()){
                    open.remove(position);
                    this.parent = parent;
                    this.init(target);
                    open.add(this);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String args[]){
        //定义open表
        ArrayList<EightPuzzle> open = new ArrayList<>();
        ArrayList<EightPuzzle> close = new ArrayList<>();
        EightPuzzle start = new EightPuzzle();
        EightPuzzle target = new EightPuzzle();
        int stnum[] = {1,2,3,4,5,6,7,8,0};
        int tanum[] = {1,2,3,7,0,4,8,6,5};

        start.setNum(stnum);
        target.setNum(tanum);
        long startTime=System.currentTimeMillis();   //获取开始时间
        if(start.isSolvable(target)){
            //初始化初始状态
            start.init(target);
            open.add(start);
            while(!open.isEmpty()){
                Collections.sort(open);            //按照evaluation的值排序
                EightPuzzle best = open.get(0);    //从open表中取出最小估值的状态并移除open表
                open.remove(0);
                close.add(best);
                if(best.isTarget(target)){
                    //输出
                    best.printRoute();
                    long end=System.currentTimeMillis(); //获取结束时间
                    System.out.println("程序运行时间： "+(end-startTime)+"ms");
                    System.exit(0);
                }
                int move;
                //由best状态进行扩展并加入到open表中
                //0的位置上移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if(best.isMoveUp()){
                    move = 0;
                    EightPuzzle up = best.moveUp(move);
                    up.operation(open, close, best, target);
                }
                //0的位置下移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if(best.isMoveDown()){
                    move = 1;
                    EightPuzzle up = best.moveUp(move);
                    up.operation(open, close, best, target);
                }
                //0的位置左移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if(best.isMoveLeft()){
                    move = 2;
                    EightPuzzle up = best.moveUp(move);
                    up.operation(open, close, best, target);
                }
                //0的位置右移之后状态不在close和open中设定best为其父状态，并初始化f(n)估值函数
                if(best.isMoveRight()){
                    move = 3;
                    EightPuzzle up = best.moveUp(move);
                    up.operation(open, close, best, target);
                }

            }
        }else
            System.out.println("没有解，请重新输入。");
    }
}