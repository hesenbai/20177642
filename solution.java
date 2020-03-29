import java.io.*;
import java.util.ArrayList;
public class solution {
    private static int m;
    public static int n;
    private static String inputPath;
    private static String outputPath;
    private static ArrayList<String> hang = new ArrayList<String>();//用于存储文件读入的每一行
    private static int[][] shudoPan = new int[9][10];
    private static int boxRow;
    private static int boxCol;
    private static int j = 0;//用于设定某个数独盘的第j行
    private static int[][] rowOccupied = new int[9][10];
    private static int[][] colOccupied = new int[9][10];
    private static int[][] boxOccupied = new int[9][10];

    public static boolean sudokuSolved = false;
    /**
     * 用于判断输入格式对错
     */
    public boolean ifTrue(String str) {
        if(str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9")) {
            return true;
        }
        if(str.contains(".txt")) {
            return true;
        }
        else return false;
    }
    /**
     * 命令行传参
     */
    public void loadArgs(String[] args) {
        if(args.length > 0 && args != null) {
            for(int i = 0; i < args.length; i++) {
                switch(args[i]) {
                    case "-m":
                        i++;
                        if(ifTrue(args[i]))
                            m = Integer.parseInt(args[i]);
                        else
                            System.out.println("缺少数独阶数m……");
                        break;
                    case "-n":
                        i++;
                        if(ifTrue(args[i]))
                            n = Integer.parseInt(args[i]);
                        else
                            System.out.println("缺少数独个数n……");
                        break;
                    case "-i":
                        i++;
                        if(ifTrue(args[i]))
                            inputPath = args[i];
                        else
                            System.out.println("读取文件格式不正确……");
                        break;
                    case "-o":
                        i++;
                        if(ifTrue(args[i]))
                            outputPath = args[i];
                        else
                            System.out.println("输出文件格式不正确……");
                        break;
                    default:
                        System.out.println("请按格式输入……");
                        break;
                }
            }
        }else {
            System.out.println("未输入参数……");
            System.exit(1);
        }
    }

    /**
     * 判断文件是否存在
     */
    public void checkArgs() {
        if(inputPath == null || !(new File(inputPath).exists())) {
            System.out.println("输入文件不存在……");
            System.exit(1);
        }
    }
    /**
     * 读取
     * @param
     */
    public void readFile() {
        //为了防止错误，用catch捕捉错误并打印
        try (FileReader reader = new FileReader(inputPath);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            while(true) {
                try {
                    String line = br.readLine();
                    if(!line.equals("")) {
                        hang.add(line);
                    }else {
                        continue;
                    }
                }catch(NullPointerException ex) {
                    break;
                }
            }
            if(hang.size() < m*n) {
                System.out.println("读取文件为空……");
                System.exit(1);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 取得第numb个数独盘并进行解数独
     * @param numb
     * @param
     */
    public void getAndDO(int numb) {
        int index;
        for(int i = 0; i < m; i++) {
            index = numb*m+i;
            Slipt(hang.get(index));
        }
        //将三个判断占位的数组初始化为0，把判断数独是否解完初始化为false
        Initialize(rowOccupied);
        Initialize(colOccupied);
        Initialize(boxOccupied);
        sudokuSolved = false;

        solveSudoku(shudoPan);
        for(int i = 0; i < j; i++) {
            for(int k = 0; k < m; k++) {
                if(k != m-1) {
                    System.out.print(shudoPan[i][k]+" ");
                }else {
                    System.out.println(shudoPan[i][k]);
                }
            }
        }
        System.out.println();
        j = 0;
    }
    /**
     * 设定宫的大小
     */
    public static void setBox() {
        if(m == 4) {
            boxRow = 2;
            boxCol = 2;
        }
        if(m == 6) {
            boxRow = 2;
            boxCol = 3;
        }
        if(m == 8) {
            boxRow = 4;
            boxCol = 2;
        }
        if(m == 9) {
            boxRow = 3;
            boxCol = 3;
        }
        if(m == 3 || m == 5 || m == 7) {
            boxRow = -1;
        }
    }
    /**
     * 拆分每行为数字
     * @param line
     * @param
     */
    public void Slipt(String line) {
        int k = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) != ' ') {
                String str = String.valueOf(line.charAt(i));
                shudoPan[j][k] = Integer.parseInt(str);
                k++;
            }
        }
        j++;
    }

    /**
     * 写入文件
     */
    public void writeFile(boolean isDone){
        try {
            FileWriter bw = null;
            File newFile = new File(outputPath);
            if(!newFile.exists()) {
                newFile.createNewFile();
            }
            if(isDone == true) {
                bw = new FileWriter(newFile,isDone);
                {
                    for(int i = 0;i < m; i++) {
                        for(int k = 0; k < m; k++) {
                            if(k != m-1) {
                                bw.write(String.valueOf(shudoPan[i][k]));
                                bw.write(" ");
                            }else {
                                bw.write(String.valueOf(shudoPan[i][k]));
                                bw.write("\n");
                            }
                        }
                    }
                    bw.write("\n");
                    bw.flush();
                    bw.close();
                }
            }else {
                bw = new FileWriter(newFile,isDone);
                bw.write("");
            }

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环初始化占位数组
     */
    public void Initialize(int[][] Occupied) {
        for(int i = 0; i < m; i++) {
            for(int k = 0; k < m+1; k++) {
                Occupied[i][k] = 0;
            }
        }
    }
    public boolean couldPlace(int d, int row, int col) {

        //判断某格子是否可以放置
        if(boxRow > 0) {
            int idx = (row / boxRow ) * boxRow + col / boxCol;
            return rowOccupied[row][d] + colOccupied[col][d] + boxOccupied[idx][d] == 0;
        }else {
            return rowOccupied[row][d] + colOccupied[col][d] == 0;
        }

    }
    /**
     * 回溯填充方法
     * @param row
     * @param col
     */
    public void backtrack(int row, int col) {


        if(shudoPan[row][col] == 0) {
            for(int d = 1; d <= m; d++) {
                int idx = 0;
                if(boxRow > 0) {
                    idx = (row / boxRow ) * boxRow + col / boxCol;
                }

                if(couldPlace(d, row, col)) {
                    //填充数字，并设置填充限制
                    boxOccupied[idx][d]++;
                    rowOccupied[row][d]++;
                    colOccupied[col][d]++;
                    shudoPan[row][col] = d;
                    //是否填充到最后一格
                    if ((col == m-1) && (row == m-1)) {
                        sudokuSolved = true;
                    }
                    else {
                        //当到达最后一列的格子，下一个格子跳转到下一行
                        if (col == m-1) {
                            backtrack(row + 1, 0);
                        }else {
                            backtrack(row, col + 1);
                        }
                    }
                    if(!sudokuSolved) {//移除填充后无法进行后续填充的数

                        boxOccupied[idx][d]--;
                        rowOccupied[row][d]--;
                        colOccupied[col][d]--;
                        shudoPan[row][col] = 0;
                    }
                }
            }
        }else {
            if ((col == m-1) && (row == m-1)) {
                sudokuSolved = true;
            }
            else {
                //当到达最后一列的格子，下一个格子跳转到下一行
                if (col == m-1) {
                    backtrack(row + 1, 0);
                }else {
                    backtrack(row, col + 1);
                }
            }
        }
    }
    /**
     * 解数独方法
     */
    public void solveSudoku(int[][] shudoPan) {
        setBox();//调用设置宫的行列数方法
        //System.out.println("boxRow,boxCol:"+boxRow+" "+boxCol);

        // 初始化某数所在行、列、宫
        for (int i = 0; i < m; i++) {
            for (int k = 0; k < m; k++) {
                int num = shudoPan[i][k];
                if (num != 0) {
                    int d = num;
                    if(boxRow > 0) {
                        int idx = (i / boxRow ) * boxRow + k / boxCol;
                        boxOccupied[idx][d]++;
                    }
                    rowOccupied[i][d]++;
                    colOccupied[k][d]++;
                }
            }
        }
        backtrack(0, 0);
    }
}