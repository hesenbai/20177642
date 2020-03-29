import java.io.*;
import java.util.ArrayList;
public class solution {
    private static int m;
    public static int n;
    private static String inputPath;
    private static String outputPath;
    private static ArrayList<String> hang = new ArrayList<String>();//���ڴ洢�ļ������ÿһ��
    private static int[][] gongge = new int[9][10];
    private static int boxRow;
    private static int boxCol;
    private static int j = 0;//�����趨ĳ�������̵ĵ�j��
    private static int[][] rowArray = new int[9][10];
    private static int[][] coluArray = new int[9][10];
    private static int[][] boxArray = new int[9][10];

    public static boolean sudokuSolved = false;
    /**
     * �����ж������ʽ�Դ�
     */
    public boolean isTrue(String str) {
        if(str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9")) {
            return true;
        }
        if(str.contains(".txt")) {
            return true;
        }
        else return false;
    }
    /**
     * �����д���
     */
    public void loadCommands(String[] args) {
        if(args.length > 0 && args != null) {
            for(int i = 0; i < args.length; i++) {
                switch(args[i]) {
                    case "-m":
                        i++;
                        if(isTrue(args[i]))
                            m = Integer.parseInt(args[i]);
                        else
                            System.out.println("ȱ�������Ľ���m��");
                        break;
                    case "-n":
                        i++;
                        if(isTrue(args[i]))
                            n = Integer.parseInt(args[i]);
                        else
                            System.out.println("ȱ�������ĸ���n��");
                        break;
                    case "-i":
                        i++;
                        if(isTrue(args[i]))
                            inputPath = args[i];
                        else
                            System.out.println("��ȡ�ļ���ʽ����ȷ��");
                        break;
                    case "-o":
                        i++;
                        if(isTrue(args[i]))
                            outputPath = args[i];
                        else
                            System.out.println("����ļ���ʽ����ȷ��");
                        break;
                    default:
                        System.out.println("�밴��ʽ���룡");
                        break;
                }
            }
        }else {
            System.out.println("δ���������");
            System.exit(1);
        }
    }

    /**
     * �ж��ļ��Ƿ����
     */
    public void checkcommands() {
        if(inputPath == null || !(new File(inputPath).exists())) {
            System.out.println("�����ļ������ڣ�");
            System.exit(1);
        }
    }
    /**
     * ��ȡ
     * @param
     */
    public void readFile() {
        //Ϊ�˷�ֹ������catch��׽���󲢴�ӡ
        try (FileReader reader = new FileReader(inputPath);
             BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
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
                System.out.println("��ȡ�ļ�Ϊ�գ�");
                System.exit(1);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * ȡ�õ�numb�������̲����н�����
     * @param numb
     * @param
     */
    public void getAndDo(int numb) {
        int index;
        for(int i = 0; i < m; i++) {
            index = numb*m+i;
            Slipt(hang.get(index));
        }
        //�������ж�ռλ�������ʼ��Ϊ0�����ж������Ƿ�����ʼ��Ϊfalse
        Initialize(rowArray);
        Initialize(coluArray);
        Initialize(boxArray);
        sudokuSolved = false;

        solveSudoku(gongge);
        for(int i = 0; i < j; i++) {
            for(int k = 0; k < m; k++) {
                if(k != m-1) {
                    System.out.print(gongge[i][k]+" ");
                }else {
                    System.out.println(gongge[i][k]);
                }
            }
        }
        System.out.println();
        j = 0;
    }
    /**
     * �趨���Ĵ�С
     */
    public static void setBox() {
        if(m == 3 || m == 5 || m == 7) {
            boxRow = -1;
        }
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

    }
    /**
     * ���ÿ��Ϊ����
     * @param line
     * @param
     */
    public void Slipt(String line) {
        int k = 0;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) != ' ') {
                String str = String.valueOf(line.charAt(i));
                gongge[j][k] = Integer.parseInt(str);
                k++;
            }
        }
        j++;
    }

    /**
     * д���ļ�
     */
    public void writeinFile(boolean isDone){
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
                                bw.write(String.valueOf(gongge[i][k]));
                                bw.write(" ");
                            }else {
                                bw.write(String.valueOf(gongge[i][k]));
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
     * ѭ����ʼ��ռλ����
     */
    public void Initialize(int[][] Occupied) {
        for(int i = 0; i < m; i++) {
            for(int k = 0; k < m+1; k++) {
                Occupied[i][k] = 0;
            }
        }
    }
    public boolean isPlace(int d, int row, int col) {

        //�ж�ĳ�����Ƿ���Է���
        if(boxRow > 0) {
            int idx = (row / boxRow ) * boxRow + col / boxCol;
            return rowArray[row][d] + coluArray[col][d] + boxArray[idx][d] == 0;
        }else {
            return rowArray[row][d] + coluArray[col][d] == 0;
        }

    }
    /**
     * ������䷽��
     * @param row
     * @param col
     */
    public void backtrack(int row, int col) {


        if(gongge[row][col] == 0) {
            for(int d = 1; d <= m; d++) {
                int idx = 0;
                if(boxRow > 0) {
                    idx = (row / boxRow ) * boxRow + col / boxCol;
                }

                if(isPlace(d, row, col)) {
                    //������֣��������������
                    boxArray[idx][d]++;
                    rowArray[row][d]++;
                    coluArray[col][d]++;
                    gongge[row][col] = d;
                    //�Ƿ���䵽���һ��
                    if ((col == m-1) && (row == m-1)) {
                        sudokuSolved = true;
                    }
                    else {
                        //���������һ�еĸ��ӣ���һ��������ת����һ��
                        if (col == m-1) {
                            backtrack(row + 1, 0);
                        }else {
                            backtrack(row, col + 1);
                        }
                    }
                    if(!sudokuSolved) {//�Ƴ������޷����к���������

                        boxArray[idx][d]--;
                        rowArray[row][d]--;
                        coluArray[col][d]--;
                        gongge[row][col] = 0;
                    }
                }
            }
        }else {
            if ((col == m-1) && (row == m-1)) {
                sudokuSolved = true;
            }
            else {
                //���������һ�еĸ��ӣ���һ��������ת����һ��
                if (col == m-1) {
                    backtrack(row + 1, 0);
                }else {
                    backtrack(row, col + 1);
                }
            }
        }
    }
    /**
     * ������
     */
    public void solveSudoku(int[][] shudoPan) {
        setBox();//�������ù�������������
        //System.out.println("boxRow,boxCol:"+boxRow+" "+boxCol);

        // ��ʼ��ĳ�������С��С���
        for (int i = 0; i < m; i++) {
            for (int k = 0; k < m; k++) {
                int num = shudoPan[i][k];
                if (num != 0) {
                    int d = num;
                    if(boxRow > 0) {
                        int idx = (i / boxRow ) * boxRow + k / boxCol;
                        boxArray[idx][d]++;
                    }
                    rowArray[i][d]++;
                    coluArray[k][d]++;
                }
            }
        }
        backtrack(0, 0);
    }
}