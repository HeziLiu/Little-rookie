import java.util.Scanner;

public class Chess_Board {
    private static int t=0;   //L型骨牌编号
    public static void main(String args[]){
        int k,dr,dc;
        System.out.println("输入棋盘大小k值（2^k*2^k）:");
        Scanner input =new Scanner(System.in);
        k=input.nextInt();
        int size= (int) Math.pow(2,k);
        int[][] board=new int[size][size];
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                board[i][j]=0;
            }
        }
        System.out.println("请输入特殊方格的坐标x,y:");
        dr=input.nextInt();dc=input.nextInt();
        ChessBoard(0,0,dr,dc,size,board);
        for (int[] aBoard : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(aBoard[j] + " ");
            }
            System.out.println();
        }
    }

    private static void ChessBoard(int tr, int tc, int dr, int dc, int size,int[][] board) {
        int s,t1;     //t1表示本次覆盖所用L型骨牌的编号
        if(size==1)
            return;
        t1=++t;
        s=size/2;
        if (dr<tr+s&&dc<tc+s)   //特殊方格在左上角子棋盘中
            ChessBoard(tr,tc,dr,dc,s,board);//递归处理子棋盘
        else{
            board[tr+s-1][tc+s-1]=t1;
            ChessBoard(tr,tc,tr+s-1,tc+s-1,s,board);
        }
        if (dr<tr+s&&dc>=tc+s)
            ChessBoard(tr,tc+s,dr,dc,s,board);
        else {
            board[tr+s-1][tc+s]=t1;
            ChessBoard(tr,tc+s,tr+s-1,tc+s,s,board);
        }
        if (dr>=tr+s&&dc<tc+s){
            ChessBoard(tr+s,tc,dr,dc,s,board);
        }else {
            board[tr+s][tc+s-1]=t1;
            ChessBoard(tr+s,tc,tr+s,tc+s-1,s,board);
        }
        if (dr>=tr+s&&dc>=tc+s){
            ChessBoard(tr+s,tc+s,dr,dc,s,board);
        }else {
            board[tr+s][tc+s]=t1;
            ChessBoard(tr+s,tc+s,tr+s,tc+s,s,board);
        }
    }
}
