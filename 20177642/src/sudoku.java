public class sudoku {
    public static void main(String[] args){
//		Scanner scanner = new Scanner(System.in);
//		scanner.next();//性能测试用
        solution ab = new solution();
        ab.loadCommands(args);//传入参数
        ab.checkcommands();//检查输入的文件是否存在
        ab.readFile();//读取文件
        ab.writeinFile(false);
        for(int i =0; i < solution.n; i++) {
            ab.getAndDo(i);//取得第i个数独表进行求解
            ab.writeinFile(true);//将结果写入指定文件中
        }
//		scanner.next();

    }

}
