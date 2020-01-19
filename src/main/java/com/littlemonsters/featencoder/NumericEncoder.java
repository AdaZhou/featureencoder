package com.littlemonsters.featencoder;

import java.util.Map;

public class NumericEncoder implements FeatureEncoderIntf, java.io.Serializable{
    private String encoder;
    private Map<String,String> param;
    private boolean encodeSingle=true;
    private int featureStartIndex= -1;
    public NumericEncoder(int featureStartIndex, String encoder, Map<String,String> param, boolean encodeSingle){
        this.encoder=encoder;
        this.param=param;
        this.encodeSingle=encodeSingle;
        this.featureStartIndex=featureStartIndex;
    }

    @Override
    public Object[] encode(Object x) throws Exception {
        if(this.encodeSingle){
            if(this.encoder.equals("max-min")){
               double max= Double.valueOf(param.get("max"));
               double min=Double.valueOf(param.get("min"));
               return new Object[]{featureStartIndex,((Double)x-min)/(max-min)};
            }else if(this.encoder.equals("z-score")){
                double mean= Double.valueOf(param.get("mean"));
                double std=Double.valueOf(param.get("std"));
                return new Object[]{featureStartIndex,((Double)x-mean)/std};
            }

        }
        return null;
    }

    @Override
    public Object[] encode(Object x1, Object x2) throws Exception {
        return null;
    }

    @Override
    public int getFeatureLen() {
        if(encodeSingle){
            return 1;
        }
        return 0;

    }

    @Override
    public int getOffset(Object x) throws Exception {
        return 1;
    }
}
