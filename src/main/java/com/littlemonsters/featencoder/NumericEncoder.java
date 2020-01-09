package com.littlemonsters.featencoder;



import java.util.ArrayList;
import java.util.List;

public class NumericEncoder implements FeatureEncoderIntf, java.io.Serializable{

    private static final long serialVersionUID = -2753682641617718551L;
    List<Double[]> splitPoints=new ArrayList<>();
    int featureStartIndex=-1;
    boolean encodeSingle=true;

    public static NumericEncoder loadConf(List<String> lines, int featureStartIndex, boolean encodeSingle){
        NumericEncoder conf=new NumericEncoder();
        conf.featureStartIndex=featureStartIndex;
        if(lines!=null){
            for(String line :lines){
                String[] temp= line.split(",");
                double start=Double.MIN_VALUE;
                double end=Double.MAX_VALUE;
                if(!temp[0].equals("MIN")){
                    start= Double.valueOf(temp[0]);
                }
                if(!temp[1].equals("MAX")){
                    end=Double.valueOf(temp[1]);
                }
                Double[] points=new Double[]{start,end};

                conf.splitPoints.add(points);
            }

        }
        conf.encodeSingle=encodeSingle;
        return conf;
    }

    public Object[] encode(Object x){
        Object[] encoder=new Object[2];
        if(this.encodeSingle){
            if(splitPoints.size()>0){
                if(x.toString().equals("NULL")||x.toString().equals("\\N")){
                    encoder[0]=featureStartIndex+splitPoints.size();
                    encoder[1]=1;

                }
                double y=(Double)x;
                int offset=-1;
                for(Double[] d: splitPoints){
                    offset++;
                    if(y>d[0] && y<d[1]){
                        encoder[0]=featureStartIndex+offset;
                        encoder[1]=1;

                    }
                }
                encoder[0]=featureStartIndex+splitPoints.size();
                encoder[1]=1;

            }else{
                encoder[0]=featureStartIndex;
                encoder[1]=x;

            }
            return encoder;
        }
            return null;

    }
    @Override
    public int getOffset(Object x) {
        if(splitPoints.size()>0){
            if(x.toString().equals("NULL")||x.toString().equals("\\N")){
                return splitPoints.size();
            }
            double y=(Double)x;
            int offset=-1;
            for(Double[] d: splitPoints){
                offset++;
                if(y>d[0] && y<d[1]){
                    return offset;
                }
            }
            return splitPoints.size();
        }else{
            return 0;
        }

    }
    @Override
    public Object[] encode(Object x1, Object x2) {
        return null;
    }

    public int getFeatureLen(){
        if(encodeSingle){
            return this.splitPoints.size()+1;
        }else{
            return 0;
        }

    }

}
