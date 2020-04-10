package edu.upc.mishu.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import edu.upc.mishu.interfaces.Transformable;
import lombok.Getter;
import lombok.Setter;

public class AES256Enocder implements Transformable {
    private static final String TAG = "AES加密";
    @Getter
    @Setter
    private String password = "WgG6dcDBsHzpc6A";//密钥
    private String salt = "uhTP682F3vyxHaB";//补充完整密文

    private Cipher cipher;
    private SecretKeySpec secretKey;
    private IvParameterSpec ivspec;

    private static AES256Enocder instance;

    private AES256Enocder(String password){
        this.password=password;
        // 初始化向量
        byte[] iv = {1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0};

        ivspec = new IvParameterSpec(iv);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException  e) {
            e.printStackTrace();
        }
    }

    public static AES256Enocder getInstance(String password) {
        if(instance==null||!password.equals(instance.password)){
            instance=new AES256Enocder(password);
        }
        return instance;
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param stringAEncriptar 待加密解密的字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String encode(String stringAEncriptar) {
        try {
            Log.i(TAG,cipher.toString());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(stringAEncriptar.getBytes(StandardCharsets.UTF_8)));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return stringAEncriptar;
    }

    /**
     * AES/CBC/PKCS5Padding
     *
     * @param stringADesencriptar 待加密字符串
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String decode(String stringADesencriptar) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(stringADesencriptar)));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return stringADesencriptar;
    }
}
