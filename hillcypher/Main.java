package hillcypher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author halzate93
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Hillcypher\n\t- Hector C. Alzate (T00523742)");
        
        String inputPath = "", outputPath = "", keyPath = "key.txt";
        Cypher.Mode mode = null;
        
        for(int i = 0; i < args.length; i++){
            try {
                switch(args[i]){
                    case "-k":
                        keyPath = args[i+1];
                        break;
                    case "-e":
                        inputPath = args[i+1];
                        mode = Cypher.Mode.Encrypt;
                        break;
                    case "-d":
                        inputPath = args[i+1];
                        mode = Cypher.Mode.Decrypt;
                        break;
                    case "-o":
                        outputPath = args[i+1];
                        break;
                    default:
                        System.err.println("Unsupported option: " + args[i]);
                }
                i++;
            } catch (ArrayIndexOutOfBoundsException ai){
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ai);
                System.err.println("Missing filepath after " + args[i]);
                System.exit(-1);
            }
        }
        
        if(inputPath.equals("") || outputPath.equals("")) {
            System.err.println("Input or Output files missing.");
            System.exit(-1);
        }
        
        File keyFile = new File(keyPath);
        Key key = null;
        try {

            Scanner sc = new Scanner(keyFile);
            
            int[][] keyMatrix = new int[3][3];
            int count = 0;
            while (sc.hasNextInt()) {
                keyMatrix[count/3][count%3] = sc.nextInt();
                count++;
            }
            sc.close();
            
            if(count != 9) {
                System.err.println("Invalid key matrix.");
                System.exit(-1);
            } else {
                key = new Key(keyMatrix);
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.err.println("Couldn't find key file at: " + keyPath + ".");
            System.exit(-1);
        } catch (ArrayIndexOutOfBoundsException ai){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ai);
            System.err.println("Invalid key matrix.");
            System.exit(-1);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Non inversible key, try another.");
            System.exit(-1);
        }
        
        Cypher cypher = new Cypher(key, mode);
        
        PrintWriter writer;
        try {
            writer = new PrintWriter(outputPath, "UTF-8");
            for (String line : Files.readAllLines(Paths.get(inputPath))) {
                line = line.replaceAll(" ", "");
                String out = cypher.process(line);
                writer.println(out);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Invalid input text.");
            System.exit(-1);
        }
        
        System.out.println("Finished! output: " + outputPath);
        
    }
    
}
