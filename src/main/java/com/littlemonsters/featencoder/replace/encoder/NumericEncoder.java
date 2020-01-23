package com.littlemonsters.featencoder.replace.encoder;

import com.littlemonsters.featencoder.onehot.encoder.FeatureEncoderIntf;

import java.util.Map;

public class NumericEncoder implements java.io.Serializable{

    private static final long serialVersionUID = 313572617728656986L;
    private String encoder;
    private Map<String,String> param;

    public NumericEncoder( String encoder, Map<String,String> param){
        this.encoder=encoder;
        this.param=param;

    }

    public Double encode(Object x) throws Exception {
            if(x==null||x=="NULL"||x=="\\N"){
                return Double.valueOf(param.get("default"));
            }
            if(this.encoder.equals("max-min")){
               double max= Double.valueOf(param.get("max"));
               double min=Double.valueOf(param.get("min"));
               return (Double)x-min/(max-min);
            }else if(this.encoder.equals("z-score")){
                double mean= Double.valueOf(param.get("mean"));
                double std=Double.valueOf(param.get("std"));
                return ((Double)x-mean)/std;
            }
            throw new Exception("not valid NumericEncoder");
    }

}
