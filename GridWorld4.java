import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.Timer;
class Agent {
    int row;
    int col;
    Agent(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class GridWorld4 {

    public static char chooseDirection(char[][] grid, Agent agent, int goalRow, int goalCol,boolean [][]visited){ //自主决策何时距离最短
        int row = agent.row;
        int col = agent.col;
        int ldis=Integer.MAX_VALUE;
        int udis=Integer.MAX_VALUE;
        int rdis=Integer.MAX_VALUE;
        int ddis=Integer.MAX_VALUE;
        int minDistance = Integer.MAX_VALUE;
        char bestdirection = 'X';
        if (agent.col + 1 < grid[0].length&& grid[agent.row][agent.col + 1] != '#'&&!visited[agent.row][agent.col + 1]) 
{
     rdis = distance(agent, goalRow, goalCol - 1);
}
        if (agent.col - 1 >= 0 && grid[agent.row][agent.col - 1] != '#'&&!visited[agent.row][agent.col - 1]) {
             ldis = distance(agent, goalRow, goalCol + 1);
        }
        if (agent.row + 1 < grid.length && grid[agent.row + 1][agent.col] != '#'&&!visited[agent.row + 1][agent.col]) {
            ddis = distance(agent, goalRow - 1, goalCol);
        }
        if (agent.row - 1 >= 0 && grid[agent.row - 1][agent.col] != '#'&&!visited[agent.row - 1][agent.col]) {
             udis = distance(agent, goalRow + 1, goalCol);
        }
        minDistance = Math.min(Math.min(rdis, ddis),
                           Math.min(ldis, udis));
        if(minDistance==Integer.MAX_VALUE){
            return 'X';
        }
        if (minDistance == rdis) {
            bestdirection = 'R';
        } else if (minDistance == ddis) {
            bestdirection = 'D';}
        else if (minDistance == ldis) {
            bestdirection = 'L';
        } else if (minDistance ==udis) {
            bestdirection = 'U';
        }
        return bestdirection;
    }
    public static int distance(Agent agent, int goalRow, int goalCol){
        int row = agent.row;
        int col = agent.col;
        int distance=((row-goalRow)*(row-goalRow)+(col-goalCol)*(col-goalCol));
        return distance;
    }

    public static boolean isGoal(char[][] grid, int goalRow, int goalCol) {
        return grid[goalRow][goalCol] == 'A';
    }

    public static void moveAgent(char[][] grid, Agent agent, char direction){//移动
        int row = agent.row;
        int col = agent.col;

        if(direction=='U'){
            if(row-1>=0 && grid[row-1][col]!='#'){
                grid[row][col]='.';
                grid[row-1][col]='A';
                agent.row = row-1;
            }
        }
        if(direction=='D'){
            if(row+1<grid.length && grid[row+1][col]!='#'){
                grid[row][col]='.';
                grid[row+1][col]='A';
                agent.row = row+1;
            }
        }
        if(direction=='L'){
            if(col-1>=0 && grid[row][col-1]!='#'){
                grid[row][col]='.';
                grid[row][col-1]='A';
                agent.col = col-1;
            }
        }
        if(direction=='R'){
            if(col+1<grid[0].length && grid[row][col+1]!='#'){
                grid[row][col]='.';
                grid[row][col+1]='A';
                agent.col = col+1;
            }
        }
    }
    public static void printGrid(char[][] grid) {
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void updateGrid(char[][]grid,JLabel[][] cells,int goalRow,int goalCol){//同步cells与grid
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                cells[i][j].setText(String.valueOf(grid[i][j]));
             if (grid[i][j] == '#') {
    cells[i][j].setBackground(Color.DARK_GRAY);
} 
else if(grid[i][j] == 'A'&&i==goalRow&&j==goalCol){
    cells[i][j].setBackground(Color.GREEN);
}
else if (grid[i][j] == 'A') {
    cells[i][j].setBackground(Color.BLUE);
} else if (grid[i][j] == 'G') {
    cells[i][j].setBackground(Color.RED);
} else {
    cells[i][j].setBackground(Color.WHITE);
}
            }
        }
    }
    public static void main(String[] args) {
        int [] backtrackCount = {0};//记录回溯次数
        char[][] grid = new char[5][5];
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                grid[i][j] = '.';
            }
        }
        grid[4][4]='G';
        grid[0][0]='A';
        

grid[0][3] = '#';
grid[0][4] = '#';

grid[1][1] = '#';
grid[1][3] = '#';
grid[1][4] = '#';

grid[2][1] = '#';
grid[2][2] = '#';
grid[2][3] = '#';
grid[2][4] = '#';

grid[3][4] = '#';

grid[4][0] = '#';
grid[4][1] = '#';
grid[4][2] = '#';

        printGrid(grid);
        JFrame frame = new JFrame("GridWorld");//创建窗口
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

JPanel panel = new JPanel();//创建容器
JLabel[][] cells = new JLabel[grid.length][grid[0].length];//创建二维数组，类也可以作为数据类型
panel.setLayout(new GridLayout(5, 5));//设置网格排列方式

for (int i = 0; i < grid.length; i++) {
    for (int j = 0; j < grid[i].length; j++) {
        JLabel cell = new JLabel(//显示数组内容
            String.valueOf(grid[i][j]),//转为字符串
            JLabel.CENTER//居中显示
        );
        cell.setOpaque(true);//这个 JLabel 需要绘制自己的背景，否则即使设置了背景颜色，也可能看不到效果

if (grid[i][j] == '#') {
    cell.setBackground(Color.DARK_GRAY);
    cell.setForeground(Color.WHITE);
} 
else if (grid[i][j] == 'A') {
    cell.setBackground(Color.BLUE);
    cell.setForeground(Color.WHITE);
} 
else if (grid[i][j] == 'G') {
    cell.setBackground(Color.RED);
} 
else {
    cell.setBackground(Color.WHITE);
}
        cells[i][j] = cell;
        panel.add(cell);//把当前组件添加到画板上
    }
}

frame.add(panel);
frame.setSize(500, 500);
frame.setLocationRelativeTo(null);
frame.setVisible(true);
        int goalRow = 4;
        int goalCol = 4;
       
        
       Agent agent = new Agent(0, 0);
        boolean [][]visited = new boolean[grid.length][grid[0].length];//记录是否访问过
       visited[agent.row][agent.col] = true;
        Deque<Agent> path = new ArrayDeque<>();
        path.push(new Agent(agent.row, agent.col));

Timer timer = new Timer(500, e -> {
    char direction = chooseDirection(grid, agent, goalRow, goalCol,visited);

    if (direction == 'X') {
        if(path.size()==1){
        System.out.println("No available move!");
        System.out.println("Backtrack count: " + backtrackCount[0]);
        Timer tm = (Timer) e.getSource();
        tm.stop();
        return;
    }
    
       path.pop();//回溯
       Agent prev=path.peek();
       grid[agent.row][agent.col]='.';
       agent.row=prev.row;
       agent.col=prev.col;
       grid[agent.row][agent.col]='A';
       backtrackCount[0]++;//回溯次数加1
        printGrid(grid);
        updateGrid(grid, cells,goalRow, goalCol);
        return;
}

    moveAgent(grid, agent, direction);
    updateGrid(grid, cells,goalRow, goalCol);
    visited[agent.row][agent.col] = true;
    path.push(new Agent(agent.row, agent.col));
    printGrid(grid);

    System.out.println("Distance to goal: "
            + distance(agent, goalRow, goalCol));

    if (isGoal(grid, goalRow, goalCol)) {
        System.out.println("You win!");
        System.out.println("Backtrack count: " + backtrackCount[0]);
       ((Timer) e.getSource()).stop();
        return;//和上面的创建新对象的同理
    }
} );
timer.start();

          
        
    }
}
