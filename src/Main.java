import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        UtilitatsXifrar utilitatsXifrar = new UtilitatsXifrar();

        while (true){

            System.out.println("1. Generar un SecretKey");
            System.out.println("2. Cifrar y Descifrar KeyGeneration");
            System.out.println("3. Generar un Passowrd");
            System.out.println("4. Cifrar y Descifrar PassGeneration");
            System.out.println("5. Descifrar un fichero .crypt");
            System.out.println("6. Salir");

            int opt = sc.nextInt();
            switch (opt){
                case 1:
                    utilitatsXifrar.genKeyInFile();
                    break;
                case 2:
                    utilitatsXifrar.keyGenCryptDecrypt();
                    break;
                case 3:
                    utilitatsXifrar.genPassInFile();
                    break;
                case 4:
                    utilitatsXifrar.passGenCryptDecrypt();
                    break;
                case 5:
                    utilitatsXifrar.DecryptFile();
                case 6:
                    System.exit(0);
            }
        }
    }




}