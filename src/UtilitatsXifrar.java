import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UtilitatsXifrar {
    Scanner sc = new Scanner(System.in);
    public static SecretKey keygenKeyGeneration(int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public static SecretKey passwordKeyGeneration(String text, int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                byte[] data = text.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

    public static byte[] encryptData(SecretKey sKey, byte[] data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData =  cipher.doFinal(data);
        } catch (Exception  ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return encryptedData;
    }

    public static byte[] decryptData(SecretKey sKey, byte[] data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            encryptedData =  cipher.doFinal(data);
        } catch (Exception  ex) {
            System.err.println("Error dexifrant les dades: " + ex);
        }
        return encryptedData;
    }

    public void genKeyInFile() {
        int keySize = 128;
        try{
            // Generar la clave secreta
            SecretKey clave = UtilitatsXifrar.keygenKeyGeneration(keySize);

            System.out.println( "Key encoding format : " +  Arrays.toString(clave.getEncoded()));
            System.out.println( "Key algorithm : " +  clave.getAlgorithm());
            System.out.println( "Key encoding format(string) : " +  clave.getFormat());

            //escribir la clave a un fichero
            byte[] keyBytesSecrectKey = clave.getEncoded();
            System.out.println("Indrocuce la ruta que quieres poner la clave secreta");
            String rutaKey = sc.nextLine();
            Files.write(Paths.get(rutaKey), keyBytesSecrectKey);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void keyGenCryptDecrypt() throws IOException {
        try{
            FileInputStream fileInputStream = new FileInputStream("src/File/clausA4.txt");
            Scanner sc = new Scanner(System.in);
            UtilitatsXifrar utilitatsXifrar = new UtilitatsXifrar();

            System.out.println("Introduce la ruta que esta la clave para cifrar el fichero");
            String rutaClave = sc.nextLine();
            byte[] keyBytes = Files.readAllBytes(Path.of(rutaClave));
            SecretKey keyCript = new SecretKeySpec(keyBytes, "AES");
            // Cifrar el texto en claro
            byte[] textoCifrado = UtilitatsXifrar.encryptData(keyCript, fileInputStream.readAllBytes());

            // Mostrar el texto cifrado
            System.out.println("Texto cifrado: \n" + new String(textoCifrado));

            // Leer el fichero que contenga el password
            System.out.println("Introduce la ruta del secret key para descifrar el fichero");
            rutaClave = sc.nextLine();
            keyBytes = Files.readAllBytes(Path.of(rutaClave));
            SecretKey keyDecript = new SecretKeySpec(keyBytes, "AES");

            // Descifrar el texto cifrado
            byte[] textoDescifrado = UtilitatsXifrar.decryptData(keyDecript, textoCifrado);

            // Mostrar el texto descifrado
            System.out.println("Texto descifrado: \n" + new String(textoDescifrado));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void passGenCryptDecrypt() {

        try{
            FileInputStream fileInputStream = new FileInputStream("src/File/clausA4.txt");

            System.out.println("Indruce la ruta de la contraseña secreta para cifrar el fichero");
            String rutaPass = sc.nextLine();
            byte[] keyBytes = Files.readAllBytes(Path.of(rutaPass));
            SecretKey PassCript = new SecretKeySpec(keyBytes, "AES");

            // Cifrar el texto en claro
            byte[] textoCifrado = UtilitatsXifrar.encryptData(PassCript, fileInputStream.readAllBytes());

            // Mostrar el texto cifrado
            System.out.println("Texto cifrado: \n" + new String(textoCifrado));

            // Leer el fichero que contenga el password
            System.out.println("Introduce la ruta del secret key para descifrar el fichero");
            String rutaKey = sc.nextLine();
            keyBytes = Files.readAllBytes(Path.of(rutaKey));
            SecretKey Passdecript = new SecretKeySpec(keyBytes, "AES");

            // Descifrar el texto cifrado
            byte[] textoDescifrado = UtilitatsXifrar.decryptData(Passdecript, textoCifrado);

            // Mostrar el texto descifrado
            System.out.println("Texto descifrado: \n" + new String(textoDescifrado));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void genPassInFile() throws IOException {

        int keySize = 128;

        System.out.println("Introduce un password");
        String pass = sc.nextLine();
        // Generar la clave secreta
        SecretKey clave = UtilitatsXifrar.passwordKeyGeneration(pass, keySize);

        //escribir la clave a un fichero
        byte[] keyBytesSecrectKey = clave.getEncoded();
        System.out.println("Indrocude la ruta donde quieres dejar el fichero de password");
        String rutaPass = sc.nextLine();
        Files.write(Paths.get(rutaPass), keyBytesSecrectKey);
    }


    public void DecryptFile() throws FileNotFoundException {
        int keySize = 128;
        System.out.println("Indroduce la ruta del fichero que quieres descifrar");
        String rutaDecrypt = sc.nextLine();

        // Leer el fichero encriptado en array de bytes
        byte[] fileCifrado;
        try (FileInputStream fis = new FileInputStream(rutaDecrypt)) {
            fileCifrado = fis.readAllBytes();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo cifrado: " + e.getMessage());
            return;
        }

        // Leer el fichero de password
        System.out.println("Indroduce la ruta del archivo de claves");
        String rutaClaves = sc.nextLine();
        List<String> passwords = new ArrayList<>();
        try (Scanner keyScanner = new Scanner(new File(rutaClaves))) {
            while (keyScanner.hasNextLine()) {
                passwords.add(keyScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de claves no encontrado: " + e.getMessage());
            return;
        }

        // Bucle donde prueba toda las contraseña hasta que funciona
        boolean success = false;
        for (String password : passwords) {
            SecretKey passDecrypt = passwordKeyGeneration(password, keySize);

            try {
                byte[] textoDescifrado = UtilitatsXifrar.decryptData(passDecrypt, fileCifrado);
                System.out.println("Texto descifrado: \n" + new String(textoDescifrado));
                System.out.println("La contraseña es:");
                System.out.println(password);
                success = true;
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
