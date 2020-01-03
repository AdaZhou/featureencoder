package com.littlemonsters.featencoder;

public class CrossOne2OneEncoder implements FeatureEncoderIntf,java.io.Serializable{

    private static final long serialVersionUID = 9080790805943470622L;
    FeatureEncoderIntf feature1;
    FeatureEncoderIntf feature2;

    int featureStartIndex=-1;
    public CrossOne2OneEncoder(FeatureEncoderIntf feature1, FeatureEncoderIntf feature2, int featureStartIndex){
        this.feature1=feature1;
        this.feature2=feature2;
        this.featureStartIndex=featureStartIndex;
    }

    @Override
    public int getOffset(Object x) {
        return -1;
    }

    @Override
    public Object encode(Object x1, Object x2) throws Exception{
        if(x1.equals(x2)){
            return this.featureStartIndex+feature1.getOffset(x1);
        }
        return null;
    }

    @Override
    public Object encode(Object x) {
       return null;
    }

    @Override
    public int getFeatureLen() {
        return feature1.getFeatureLen();
    }

}
