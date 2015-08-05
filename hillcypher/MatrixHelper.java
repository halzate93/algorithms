package hillcypher;

import Jama.Matrix;

/**
 *
 * @author halzate93
 */
public class MatrixHelper {
    
    //d × d-1 = 1 (mod 26)
    public static Matrix inverse(Matrix m) throws Exception{
        int d = det(m);
        
        int di = -1;
        for (int c = 0; di == -1 && c < Util.COUNT; c++) {
            if((d * c) % Util.COUNT == 1){
                di = c;
            }
        }
        if(di == -1){
            throw new Exception("Invalid key matrix.");
        }
        
        //TODO: Remove
        //System.out.println(d + " " + di);
        
        Matrix adj = cofactor(m).transpose();
        //TODO: Remove
        //System.out.println("adj ---");
        //adj.print(3, 2);
        
        return modM(adj.times(di));
    }
    
    public static Matrix modM(Matrix m){
        for (int i = 0; i < m.getRowDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                m.set(i, j, ((m.get(i, j)%Util.COUNT)+Util.COUNT)%Util.COUNT);
            }
        }
        return m;
    }
    
    public static Matrix cofactor(Matrix m) throws Exception{
        if(m.getRowDimension() != 3 || m.getColumnDimension() != 3){
            throw new Exception("Can't find adjoint for key matrix.");
        }
        Matrix cm = new Matrix(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int sign = ((i+j) % 2 == 0)? 1:-1;
                cm.set(i, j, sign*cofactor(i, j, m));
            }
        }
        
        //TODO: Remove
        //System.out.println("cofactor:");
        //cm.print(3, 2);
        return cm;
    }
    
    public static double cofactor(int i, int j, Matrix m){
        Matrix p = new Matrix(2, 2);
        
        for (int k = 0, r = 0; k < 3; k++) {
            if(k == i) continue;
            for (int l = 0, c = 0; l < 3; l++) {
                if(l == j) continue;
                p.set(r, c, m.get(k, l));
                c++;
            }
            r++;
        }
        
        return det(p);
    }
    
    public static int det(Matrix m){
        //TODO: Remove
        //System.out.println("det --");
        //m.print(m.getColumnDimension(), 2);
        
        int n = m.getColumnDimension();
        int sum = 0;
        
        if(n==2) {
            sum = (int)(m.get(0, 0) * m.get(1, 1) - m.get(0, 1) * m.get(1, 0));
        }else{
            for (int j = 0; j < n; j++) {
                int diagPos = 1, diagNeg = 1;
                for (int k = 0; k < n; k++) {
                    int pos = (int)m.get(k, (((j+k) % n) + n)%n);
                    diagPos *= pos;
                    int neg = (int)m.get(k, (((j-k) % n) + n)%n);
                    diagNeg *= neg;

                    //TODO: Remove
                    //System.out.println(">"+pos + " <" + neg);
                }
                sum += diagPos - diagNeg;
            }
        }
        
        //TODO: Remove
        //System.out.println(sum);
        return sum;
    }
}
