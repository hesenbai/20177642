public class sudoku {
    public static void main(String[] args){
//		Scanner scanner = new Scanner(System.in);
//		scanner.next();//���ܲ�����
        solution ab = new solution();
        ab.loadCommands(args);//�������
        ab.checkcommands();//���������ļ��Ƿ����
        ab.readFile();//��ȡ�ļ�
        ab.writeinFile(false);
        for(int i =0; i < solution.n; i++) {
            ab.getAndDo(i);//ȡ�õ�i��������������
            ab.writeinFile(true);//�����д��ָ���ļ���
        }
//		scanner.next();

    }

}
