public class sudoku {
    public static void main(String[] args){
//		Scanner scanner = new Scanner(System.in);
//		scanner.next();//���ܲ�����
        solution ab = new solution();
        ab.loadArgs(args);//�������
        ab.checkArgs();//���������ļ��Ƿ����
        ab.readFile();//��ȡ�ļ�
        ab.writeFile(false);
        for(int i =0; i < solution.n; i++) {
            ab.getAndDO(i);//ȡ�õ�i��������������
            ab.writeFile(true);//�����д��ָ���ļ���
        }
//		scanner.next();

    }

}
