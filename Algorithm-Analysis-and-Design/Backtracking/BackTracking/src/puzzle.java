import java.awt.*;
import java.util.Stack;
public class puzzle {
    public static void main(String args[]){
        Stack<Point> path= new Stack<>();
        int[][] map={
                {1,1,1,1,1,1,1,1,1,1},
                {1,0,0,1,0,0,0,1,0,1},
                {1,0,0,1,0,0,0,1,0,1},
                {1,0,0,0,0,1,1,0,0,1},
                {1,0,1,1,1,0,0,0,0,1},
                {1,0,0,0,1,0,0,0,0,1},
                {1,0,1,0,0,0,1,0,0,1},
                {1,0,1,1,1,0,1,1,0,1},
                {1,1,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1}
        };
        int[][] direction={{1,0},{0,1},{0,-1},{-1,0}};//分别代表下右左上四个方向，因为是起点和终点构成对角线所以优先考虑下和右
        Point pos=new Point(1,1);
        System.out.println("迷宫（1：墙 0：通路 2：已走过的点，最外一层均为1）：");
        System.out.println("起点（1，1），终点（8，8）");
        print_map(map);
        System.out.println();
        System.out.println("输出路径：");
        System.out.print("("+pos.x+","+pos.y+")"+" ");
        if (solution(map,direction,path,pos))
            System.out.println("输出迷宫：");
            print_map(map);
    }
    private static boolean solution(int map[][],int direction[][],Stack<Point> path,Point entry){
        Point cur=new Point(entry.x,entry.y);
        int count=0;
        path.push(cur.getLocation());
        overloop:while (!path.isEmpty()){
            if (cur.x!=entry.x&&cur.y!=entry.y&&cur.x==8&&cur.y==8){//终点为（8，8）
                map[cur.x][cur.y]=2;
                System.out.println();
                return true;
            }
            map[cur.x][cur.y]=2;
            for (int i=0;i<4;i++){
                cur.x+=direction[i][0];
                cur.y+=direction[i][1];
                if (check(map,cur)){
                    path.push(cur.getLocation());
                    count++;
                    if (count%5==0)
                        System.out.println();
                    System.out.print("("+cur.x+","+cur.y+")"+" ");
                    map[cur.x][cur.y]=2;
                    continue overloop;//继续最外层的循环，下面的代码不再执行
                }else {
                    cur.x-=direction[i][0];
                    cur.y-=direction[i][1];
                }
            }
            cur.setLocation(path.peek());//获取栈顶的点的位置，从栈顶的点的位置继续摸索
            path.pop();//将走不通的点弹出栈顶
            System.out.print("("+cur.x+","+cur.y+")"+" ");
        }
        return false;
    }
    private static boolean check(int[][] map,Point next){
        return (next.x<10&&next.y<10&&next.x>=0&&next.y>=0&&(map[next.x][next.y]==0));
    }
    private static void print_map(int[][] map) {
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }
}
