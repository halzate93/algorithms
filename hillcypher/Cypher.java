package hillcypher;

import Jama.Matrix;
import java.util.ArrayList;

/**
 *
 * @author halzate93
 */
public class Cypher {
    private final Key key;
    private final Mode mode;
    
    public Cypher(Key key, Mode mode) {
        this.key = key;
        this.mode = mode;
    }
    
    public String process(String text) throws Exception{    
               
        ArrayList<Matrix> blocks = Util.breakBlocks(text);
        
        //TODO: Remove
        /*for (Matrix block : blocks) {
            Util.printMatrix(block);
        } */  
        
        Matrix k = (mode == Mode.Encrypt)?key.getEncryption():key.getDecryption();
        
        String output = "";
    
        //TODO: Remove
        //System.out.println(k.getRowDimension() + " " + k.getColumnDimension());
        
        for(Matrix b : blocks){
            //TODO: Remove
            //System.out.println(b.getRowDimension() + " " + b.getColumnDimension() + "----");
            
            Matrix p = MatrixHelper.modM(k.times(b));
            for (int i = 0; i < p.getRowDimension(); i++) {
                int c = (int) p.get(i, 0);
                //TODO: Remove
                //System.out.println(b.get(i, 0) + " -> " +c.get(i, 0)%Util.COUNT);
                
                output += Util.int2Char(c);
            }
        }
        
        return output;
    }
    
    public enum Mode {
        Encrypt, Decrypt
    }
}
