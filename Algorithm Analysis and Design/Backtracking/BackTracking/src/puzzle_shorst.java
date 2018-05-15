import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
public class puzzle_shorst {
    public static void main(String args[]) {
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

        Queue<Point> path = new LinkedList<>();
        int[][] direction={{1,0},{0,1},{0,-1},{-1,0}};//分别代表下右左上四个方向，因为是起点和终点构成对角线所以优先考虑下和右
        Point entry=new Point(1,1);
        Point out=new Point(8,8);
        System.out.println("迷宫（1：墙 0：通路 最外一层均为1）：");
        System.out.println("起点（1，1），终点（8，8）");
        print_map(map);
        System.out.println();
        System.out.println("输出路径：");
        solution(map,direction,path,entry,out);
        System.out.println("输出迷宫：");
        print_map(map);
    }

    private static void solution(int[][] map,int[][] direction, Queue<Point> path, Point entry,Point out) {
        Point cur=new Point(entry.x,entry.y);
        Point next=new Point(entry.x,entry.y);
        path.offer(next.getLocation());
        map[cur.x][cur.y]=0;
        int i; int length=0;
        while(path.size()!=0){
            cur.setLocation(path.poll());
            for (i=0;i<4;i++){
                next.x=cur.x+direction[i][0];
                next.y=cur.y+direction[i][1];
                if (check(map,next,entry)){
                    path.add(next.getLocation());
                    map[next.x][next.y]=map[cur.x][cur.y]+1;
                    if (next.x==out.x&&next.y==out.y){
                        length=map[next.x][next.y];
                        break;
                    }
                }
            }
            if (i!=4){
                Point[] out_put=new Point[length];
                out_put[length-1]=next.getLocation();
                out:for (int a=length-1;a>0;a--){
                    for (int j=0;j<4;j++){
                        next.x+=direction[j][0];
                        next.y+=direction[j][1];
                        if (map[next.x][next.y]==a&&next.y!=0){
                            out_put[a-1]=next.getLocation();
                            continue out;
                        }else {
                            next.x-=direction[j][0];
                            next.y-=direction[j][1];
                        }
                    }
                }
                System.out.print("("+entry.x+","+entry.y+")"+" ");
                for (int b=0;b<length;b++){
                    if ((b+1)%7==0)
                        System.out.println();
                    System.out.print("("+out_put[b].x+","+out_put[b].y+")"+" ");
                }
                System.out.print("总长度为："+length);
                System.out.println();
                break;
            }
        }
    }

    private static boolean check(int[][] map,Point next,Point entry) {
        return next.x >= 0 && next.x < 10 && next.y >= 0 && next.y < 10 && map[next.x][next.y]==0&&(next.x!=entry.x||next.y!=entry.y);
    }

    private static void print_map(int[][] map) {
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                System.out.print(map[i][j]+"    ");
            }
            System.out.println();
        }
    }
}
