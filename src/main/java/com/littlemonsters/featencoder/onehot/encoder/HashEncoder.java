package com.littlemonsters.featencoder.onehot.encoder;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.littlemonsters.featencoder.util.MiscUtils;


public class HashEncoder implements FeatureEncoderIntf, java.io.Serializable{

    private static final long serialVersionUID = 1520626597385138260L;
    HashFunction hf = Hashing.murmur3_32();
    int maxrange = -1;
    int featureStartIndex=-1;
    boolean encodeSingle=true;
    public HashEncoder(int maxrange,boolean encodeSingle,int featureStartIndex){
        this.maxrange=maxrange;
        this.encodeSingle=encodeSingle;
        this.featureStartIndex=featureStartIndex;
    }

    @Override
    public Object[] encode(Object x) throws Exception{
        if(encodeSingle) {
            Object[] encoder = new Object[2];
            encoder[0] = this.featureStartIndex + Math.abs(hf.hashBytes(MiscUtils.serialize(x)).hashCode()) % maxrange;
            encoder[1] = 1;
            return encoder;

        }else{
            return null;
        }
    }

    @Override
    public Object[] encode(Object x1, Object x2) {
        return null;
    }

    @Override
    public int getFeatureLen() {
        if(encodeSingle){
            return this.maxrange;
        }else{
            return 0;
        }
    }


    @Override
    public int getOffset(Object x) throws Exception{

        return Math.abs(hf.hashBytes(MiscUtils.serialize(x)).hashCode())%maxrange;

    }
}
