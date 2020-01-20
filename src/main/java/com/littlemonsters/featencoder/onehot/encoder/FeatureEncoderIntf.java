package com.littlemonsters.featencoder.onehot.encoder;

public interface FeatureEncoderIntf {
    Object[] encode(Object x) throws Exception;
    Object[] encode(Object x1, Object x2)throws Exception;
    int getFeatureLen();

    int getOffset(Object x)throws Exception;
}
