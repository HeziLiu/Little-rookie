public class Graph_Color {
    private static int[][] graph={
            {0,1,1,0,0},
            {1,0,1,1,1},
            {1,1,0,0,1},
            {0,1,0,0,1},
            {0,1,1,1,0}};
    private static int[] color={0,0,0,0,0};
    private static void GraphColor(int m){
        int k;
        k=0;
        while(k>=0){
            color[k]=color[k]+1;
            while (color[k]<=m){
                if (Ok(k))
                    break;
                else color[k]=color[k]+1;
            }
            if (color[k]<=m&&k==4){
                for (int i=0;i<5;i++){
                    System.out.print(color[i]+" ");
                }
                return;
            }
            if (color[k]<=m&&k<4)
                k=k+1;
            else
                color[k--]=0;
        }
    }
    private static boolean Ok(int k) {
        for (int i=0;i<k;i++){
            if (graph[k][i]==1&&color[i]==color[k])
                return false;
        }
        return true;
    }
    public static void main(String args[]){
        GraphColor(3);
    }
}
