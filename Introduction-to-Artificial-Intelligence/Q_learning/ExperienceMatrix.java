class ExperienceMatrix {
    int get(int x, int y){
        return Q[x][y];
    }
    int[] getRow(int x){
        return Q[x];
    }
    void set(int x, int y, int value){
        Q[x][y]=value;
    }
    void print(){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                String s=Q[i][j]+" ";
                if (Q[i][j]<10)
                    s=s+"  ";
                else if (Q[i][j]<100)
                    s=s+" ";
                System.out.print(s);
            }
            System.out.println();
        }
    }
    private static int[][] Q=new int[8][8];
}
