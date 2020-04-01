package edu.upc.mishu.translate;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class controller implements Transformable {
    private static String llaveSecreta = "WgG6dcDBsHzpc6A";//密钥
    private static String salt = "uhTP682F3vyxHaB";//补充完整密文

    /**
     * AES/CBC/PKCS5Padding
     * @param stringAEncriptar 待加密解密的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String encode(String stringAEncriptar) {
        try{
            // 初始化向量
            byte[] iv = { 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0 };

            IvParameterSpec ivspec = new IvParameterSpec( iv );
            SecretKeyFactory factory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
            KeySpec spec = new PBEKeySpec( llaveSecreta.toCharArray(), salt.getBytes(), 65536, 256 );
            SecretKey tmp = factory.generateSecret( spec );
            SecretKeySpec secretKey = new SecretKeySpec( tmp.getEncoded(), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );


            cipher.init( Cipher.ENCRYPT_MODE, secretKey, ivspec );
            return Base64.getEncoder().encodeToString( cipher.doFinal( stringAEncriptar.getBytes( "UTF-8" ) ) );

        }catch ( Exception e ){
            System.out.println( "Error Mientras se Encriptaba : " + e.toString() );
        }
        return null;
    }

    /**
     * AES/CBC/PKCS5Padding
     * @param stringADesencriptar  待加密字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String decode(String stringADesencriptar) {
        try{
            byte[] iv = { 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0 };

            IvParameterSpec ivspec = new IvParameterSpec( iv );
            SecretKeyFactory factory = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
            KeySpec spec = new PBEKeySpec( llaveSecreta.toCharArray(), salt.getBytes(), 65536, 256 );
            SecretKey tmp = factory.generateSecret( spec );
            SecretKeySpec secretKey = new SecretKeySpec( tmp.getEncoded(), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5PADDING" );

            cipher.init( Cipher.DECRYPT_MODE, secretKey, ivspec );

            return new String( cipher.doFinal( Base64.getDecoder().decode( stringADesencriptar ) ) );

        }catch ( Exception e ) {
            System.out.println( "Error Mientras se Desencriptaba: " + e.toString() );
        }
    return null;
    }
}
