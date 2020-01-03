package com.littlemonsters.featencoder.manager;



import com.littlemonsters.featencoder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsFeatureEncoderManager implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(AbsFeatureEncoderManager.class);
    private static final long serialVersionUID = -8874271618267302670L;
    private static String CROSS_TYPE_O2O="o2o";
    private static String CROSS_TYPE_M2M="m2m";
    protected Map<String, FeatureEncoderIntf> featureName2conf=new HashMap<>();
    protected List<String[]> crossFeature=new ArrayList<>();
    protected int featureStartIndex=0;
    public abstract void loadCrossEncoderConfig() throws Exception;
    public abstract void loadEncoderConfig()throws Exception;

    public Object encode(String featureName, Object featureValue) throws Exception{
        return featureName2conf.get(featureName).encode(featureValue);
    }
    public Object encodeCross(String featureName1,Object featureValue1,
                                     String featureName2,Object featureValue2)throws Exception{

        if(featureName2conf.containsKey(featureName1+","+featureName2+",o2o")||
                featureName2conf.containsKey(featureName1+","+featureName2+",m2m")){
            if(featureName2conf.containsKey(featureName1+","+featureName2+",o2o")){
                return featureName2conf.get(featureName1+","+featureName2+",o2o").encode(featureValue1,featureValue2);
            }else{
                return featureName2conf.get(featureName1+","+featureName2+",m2m").encode(featureValue1,featureValue2);
            }


        }else if(featureName2conf.containsKey(featureName2+","+featureName1+",o2o")||
                featureName2conf.containsKey(featureName2+","+featureName1+",m2m")){
            if(featureName2conf.containsKey(featureName2+","+featureName1+",o2o")){
                return featureName2conf.get(featureName2+","+featureName1+",o2o").encode(featureValue2,featureValue1);
            }else{
                return featureName2conf.get(featureName2+","+featureName1+",m2m").encode(featureValue2,featureValue1);
            }
        }else{
            return -1;
        }

    }


    public Map<String, FeatureEncoderIntf> getFeatureName2conf(){
        return this.featureName2conf;
    }

    protected void loadCrossConfig(List<String> lines)throws Exception{
        for(String fline:lines){
            String[] temp=fline.split(",");
            String feature1=temp[0];
            String feature2=temp[1];
            crossFeature.add(new String[]{feature1,feature2});
            String crossType=temp[2];
            FeatureEncoderIntf fconfig=null;
            if(crossType.equals(CROSS_TYPE_M2M)){
                fconfig=new CrossMany2ManyEncoder(featureName2conf.get(feature1),
                        featureName2conf.get(feature2),featureStartIndex);

            }else if(crossType.equals(CROSS_TYPE_O2O)){
                fconfig=new CrossOne2OneEncoder(featureName2conf.get(feature1),
                        featureName2conf.get(feature2),featureStartIndex);

            }
            featureName2conf.put(fline,fconfig);
            System.out.println("----------featureStartIndex is "+featureStartIndex);
            featureStartIndex=featureStartIndex+fconfig.getFeatureLen();
        }
    }

    protected abstract List<String> getAtomEncoderConfig(String featureName)throws Exception;

    public void loadConfig(List<String> conf) throws Exception{
        for(String flines:conf){
            String fname=flines;
            String[] temp=flines.split(" ");
            if(temp.length==2){
                String use=temp[0];
                if(Integer.parseInt(use)==0){
                    continue;
                }
                fname=temp[1];
            }
            String[] fields=fname.split(",");
            String featureName=fields[0];
            String featureType=fields[1];
            boolean encode_single=true;
            if(fields.length==5){
                String encodeSingle=fields[4];
                if(encodeSingle.equals("not_encode_single")){
                    encode_single=false;
                }
            }

            if(featureType.equals("num")){
                System.out.println("----------featureStartIndex is "+featureStartIndex);
                NumericEncoder config=NumericEncoder.loadConf(getAtomEncoderConfig(featureName),featureStartIndex,encode_single);
                featureStartIndex=featureStartIndex+config.getFeatureLen();
                featureName2conf.put(featureName,config);

            }else if(featureType.equals("dict")){
                System.out.println("----------featureStartIndex is "+featureStartIndex);
                DiscreteDictEncoder config = DiscreteDictEncoder.loadConf(getAtomEncoderConfig(featureName), featureStartIndex,encode_single);
                featureStartIndex = featureStartIndex + config.getFeatureLen();
                featureName2conf.put(featureName, config);

            }else if(featureType.contains("hash")){
                System.out.println("----------featureStartIndex is "+featureStartIndex);
                String[] info=featureType.split("_");
                int maxrange=Integer.valueOf(info[1]);
                HashEncoder config=new HashEncoder( maxrange,encode_single,featureStartIndex);
                featureStartIndex = featureStartIndex + config.getFeatureLen();
                featureName2conf.put(featureName, config);
            }
        }
    }
}
