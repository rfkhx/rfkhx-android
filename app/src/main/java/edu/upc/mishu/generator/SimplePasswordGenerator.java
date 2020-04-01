package edu.upc.mishu.generator;

import android.util.Log;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePasswordGenerator implements PasswordGenerator {
    private int length;
    private boolean uppercaseLetters;
    private boolean lowercaseLetters;
    private boolean numbers;
    private boolean symbols;

    @Override
    public String generateAPassword() throws RuntimeException{
        if(length<=0||(!uppercaseLetters&&!lowercaseLetters&&numbers&&symbols)){
            RuntimeException e=new RuntimeException(toString());
            Log.e(getClass().getSimpleName(),"生成失败，请检查生成器配置。",e);
            throw e;
        }

        char[] uppercaseLetters={'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] lowercaseLetters={'a','b','c','d','e','f','g','h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};
        char[] numbers={'2','3','4','5','6','7','8','9'};
        char[] symbols={'!','@','$','%','^','&','*'};
        Random random=new Random(System.currentTimeMillis());
        StringBuilder sb=new StringBuilder();
        while (sb.length()<length) {
            switch (random.nextInt(4)) {
                case 0:
                    sb.append(uppercaseLetters[random.nextInt(uppercaseLetters.length)]);
                    break;
                case 1:
                    sb.append(lowercaseLetters[random.nextInt(lowercaseLetters.length)]);
                    break;
                case 2:
                    sb.append(numbers[random.nextInt(numbers.length)]);
                    break;
                default:
                    sb.append(symbols[random.nextInt(symbols.length)]);
            }
        }
        return sb.toString();
    }
}
