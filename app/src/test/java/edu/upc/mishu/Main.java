package edu.upc.mishu;
import edu.upc.mishu.translate.*;
public class Main {
    public static void main(String[] args) {
        controller tem=new controller();
        String miwen=tem.encode("1111");
        String mingwen=tem.decode(miwen);
        System.out.println(miwen);
        System.out.println(mingwen);

        String miwen1=tem.encode("liufukang");
        String mingwen1=tem.decode(miwen1);
        System.out.println(miwen1);
        System.out.println(mingwen1);

        String miwen2=tem.encode("liuhaixin..");
        String mingwen2=tem.decode(miwen2);
        System.out.println(miwen2);
        System.out.println(mingwen2);

        String miwen3=tem.encode("lirui123");
        String mingwen3=tem.decode(miwen3);
        System.out.println(miwen3);
        System.out.println(mingwen3);
    }
}
