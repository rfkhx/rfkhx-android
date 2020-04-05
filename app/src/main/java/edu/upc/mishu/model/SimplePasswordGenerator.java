package edu.upc.mishu.model;

import android.util.Log;

import java.util.Random;

import edu.upc.mishu.interfaces.PasswordGenerator;
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
        if(length<=0||(!uppercaseLetters&&!lowercaseLetters&&!numbers&&!symbols)){
            RuntimeException e=new RuntimeException(toString());
            Log.e(getClass().getSimpleName(),"生成失败，请检查生成器配置。",e);
            throw e;
        }

        char[] chUppercaseLetters={'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] chLowercaseLetters={'a','b','c','d','e','f','g','h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};
        char[] chNumbers={'2','3','4','5','6','7','8','9'};
        char[] chSymbols={'!','@','$','%','^','&','*'};
        Random random=new Random(System.currentTimeMillis());
        StringBuilder sb=new StringBuilder();
        while (sb.length()<length) {
            switch (random.nextInt(4)) {
                case 0:
                    if(uppercaseLetters)
                        sb.append(chUppercaseLetters[random.nextInt(chUppercaseLetters.length)]);
                    break;
                case 1:
                    if(lowercaseLetters)
                        sb.append(chLowercaseLetters[random.nextInt(chLowercaseLetters.length)]);
                    break;
                case 2:
                    if(numbers)
                        sb.append(chNumbers[random.nextInt(chNumbers.length)]);
                    break;
                default:
                    if(symbols)
                        sb.append(chSymbols[random.nextInt(chSymbols.length)]);
            }
        }
        return sb.toString();
    }
}
