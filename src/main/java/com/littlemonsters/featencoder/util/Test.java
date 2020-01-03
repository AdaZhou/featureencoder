package com.littlemonsters.featencoder.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


public class Test {
    public static void main(String[] args){

        for(int i=0;i<10;i++) {
            HashFunction hf = Hashing.murmur3_32();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            System.out.println(hf.hashBytes("zxc".getBytes()).hashCode());
        }
    }
}
