package hillcypher;

import Jama.Matrix;

/**
 *
 * @author halzate93
 */
public class Key {
    private final Matrix encryption, decryption;

    public Matrix getEncryption() {
        return encryption;
    }

    public Matrix getDecryption() {
        return decryption;
    }
    
    public Key(int[][] matrix) throws Exception{
        encryption = MatrixHelper.modM(new Matrix(Util.int2Double(matrix)));
        decryption = MatrixHelper.modM(MatrixHelper.inverse(encryption));
        
        //TODO: Remove
        /*System.out.println("key ---");
        encryption.print(3, 3);
        decryption.print(3, 3);*/
    }
}
