package com.littlemonsters.featencoder.onehot.encoder.onehot.manager;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeatureEncoderFileManager extends AbsFeatureEncoderManager {

    private final static Logger logger = LoggerFactory.getLogger(FeatureEncoderFileManager.class);
    private static final long serialVersionUID = -6541574949640023706L;
    private String rootPath;
    private String encoderConfPath="atom_encoder_conf";
    private String crossFeatureFileName="cross_encoder_conf";
    private String featureFileName="encoder_conf";

    public FeatureEncoderFileManager(String confRoot){
        this.rootPath=confRoot;

    }


    @Override
    public void loadCrossEncoderConfig() throws Exception {
        List<String> cross=FileUtils.readLines(new File(rootPath+"/"+crossFeatureFileName));

        super.loadCrossConfig(cross);
    }

    @Override
    public void loadEncoderConfig() throws Exception {
        List<String> feat=FileUtils.readLines(new File(rootPath+"/"+featureFileName));
        super.loadConfig(feat);
    }

    @Override
    protected List<String> getAtomEncoderConfig(String fname) throws Exception{
        logger.info("load feature "+fname);
        List<String> lines= FileUtils.readLines(new File(this.rootPath+"/"+encoderConfPath+"/"+fname));
        List<String> newLine=new ArrayList<>();
        for(String line:lines){
            String es=line.split("\t")[0];
            if(es.trim().length()>0) {
                newLine.add(es);
            }
        }
        return newLine;
    }


    public static void main(String[] args) throws Exception{
        FeatureEncoderFileManager f=new FeatureEncoderFileManager("/Users/zhouxiaocao/IdeaProjects/ctr/ctr-feature/encode_conf");
        f.loadEncoderConfig();
        f.loadCrossEncoderConfig();
        List<Object> uC1=new ArrayList<>();
        List<Object> sc1=new ArrayList<>();
        uC1.add(15.6);

        System.out.println(f.encode("sale_w1",uC1.get(0)));
    }
}
