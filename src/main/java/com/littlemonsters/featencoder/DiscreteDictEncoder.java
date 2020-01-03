package com.littlemonsters.featencoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscreteDictEncoder implements FeatureEncoderIntf, java.io.Serializable{

    private static final long serialVersionUID = 762358252266093050L;
    Map<String,Integer> words2offset=new HashMap<>();
   int featureStartIndex=-1;
   boolean encodeSingle=true;
    public static DiscreteDictEncoder loadConf(List<String> lines, int featureStartIndex, boolean encodeSingle){
        DiscreteDictEncoder conf=new DiscreteDictEncoder();
        conf.featureStartIndex=featureStartIndex;
        for(int i=0;i<lines.size();i++){
            conf.words2offset.put(lines.get(i),i);
        }
        conf.encodeSingle=encodeSingle;
        return conf;
    }

    @Override
    public int getOffset(Object x) {

        Integer offset=words2offset.get(x);
        if(offset!=null){
            return offset;
        }
        return this.words2offset.size();
    }

    @Override
    public Object encode(Object x1, Object x2) {
        return null;
    }

    @Override
    public Object encode(Object x) {
        if(encodeSingle) {
            String y = (String) x;
            Integer offset = words2offset.get(y);
            if (offset != null) {
                return featureStartIndex + offset;
            }
            return featureStartIndex + this.words2offset.size();
        }else{
            return null;
        }
    }

    public int getFeatureLen(){
        if(encodeSingle){
            return this.words2offset.size()+1;
        }else{
            return 0;
        }

    }
    public int getFeatureStartIndex() {
        if(encodeSingle){
            return this.featureStartIndex;
        }else{
            return -1;
        }
    }
}
