package hillcypher;

import Jama.Matrix;
import java.util.ArrayList;

/**
 *
 * @author halzate93
 */
public class Util {
    
    public static char PAD = '?';
    public static int COUNT = 31;
    
    public static char int2Char(int i) throws Exception{
        i = ((i%COUNT)+COUNT)%COUNT;
        if(i >= 1 && i <= 26){
            return (char) ('A' + i - 1);
        }
        switch(i){
            case 0:
                return '?';
            case 27:
                return ',';
            case 28:
                return '.';
            case 29:
                return '!';
            case 30:
                return ';';
            default:
                throw new Exception("Invalid int: " + i);
        }
    }
    
    public static char[] int2Char(int[] i) throws Exception {
        char[] c = new char[i.length];
        for (int j = 0; j < i.length; j++) {
            c[j] = int2Char(i[j]);
        }
        return c;
    }
    
    public static int char2Int(char c) throws Exception{
        if(c >= 'A' && c <= 'Z'){
            return (c - 'A' + 1);
        }
        switch(c){
            case '?':
                return 0;
            case ',':
                return 27;
            case '.':
                return 28;
            case '!':
                return 29;
            case ';':
                return 30;
            default:
                throw new Exception("Invalid char: " + c);
        }
    }
    
    public static int[] char2Int(char[] c) throws Exception{
        int[] i = new int[c.length];
        for (int j = 0; j < c.length; j++) {
            i[j] = char2Int(c[j]);
        }
        return i;
    }
    
    public static int[] double2Int(double[] d){
        int[] i = new int[d.length];
        
        for (int j = 0; j < i.length; j++) {
            i[j] = (int)d[j];
        }
        
        return i;
    }
    
    public static int[][] double2Int(double[][] d){
        int[][] c = new int[d.length][d[0].length];
        
        for (int i = 0; i < d.length; i++) {
            c[i] = double2Int(d[i]);
        }
        
        return c;
    }
    
    public static double[] int2Double(int[] c){
        double[] d = new double[c.length];
        
        for (int i = 0; i < c.length; i++) {
            d[i] = c[i];
        }
        
        return d;
    }
    
    public static double[][] int2Double(int[][] c){
        double[][] d = new double[c.length][c[0].length];
        
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                d[i][j] = (double) c[i][j];
            }
        }
        
        return d;
    }

    public static ArrayList<Matrix> breakBlocks(String str) throws Exception {
        while(str.length() % 3 != 0){
            str+= PAD;
        }
        
        ArrayList<Matrix> blocks = new ArrayList<>();
        for (int i = 0; i < str.length(); i+=3) {
            char[] blockC = new char[]{str.charAt(i), str.charAt(i+1), str.charAt(i+2)};
            int[] blockI = Util.char2Int(blockC);
            double[] blockD = Util.int2Double(blockI);
            blocks.add(new Matrix(blockD, 3));
        }
        
        return blocks;
    }
    
    public static void printMatrix(Matrix m) throws Exception{
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                System.out.print(int2Char((int)m.get(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
