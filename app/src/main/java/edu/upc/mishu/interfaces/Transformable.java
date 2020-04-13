package edu.upc.mishu.interfaces;

//TODO 换个更合适的名字
//TODO 应用单例模式，解决现在混乱的情况
public interface Transformable {
    String encode(String stringAEncriptar);
    String decode(String stringADesencriptar);
}
