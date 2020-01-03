package com.littlemonsters.featencoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossMany2ManyEncoder implements FeatureEncoderIntf,java.io.Serializable{
    private final static Logger logger = LoggerFactory.getLogger(CrossMany2ManyEncoder.class);
    private static final long serialVersionUID = 4286677357353519105L;
    FeatureEncoderIntf feature1;
    FeatureEncoderIntf feature2;

    int featureStartIndex=-1;
    public CrossMany2ManyEncoder(FeatureEncoderIntf feature1, FeatureEncoderIntf feature2, int featureStartIndex){
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
        int index1=feature1.getOffset(x1);
        int index2=feature2.getOffset(x2);
        //if(index1>0&&index2>0){
            return featureStartIndex+index1*feature2.getFeatureLen()+index2;
        //}
        //return -1;
    }

    @Override
    public Object encode(Object x) {
       return null;

    }

    @Override
    public int getFeatureLen() {
        return feature1.getFeatureLen()*feature2.getFeatureLen();
    }

}
