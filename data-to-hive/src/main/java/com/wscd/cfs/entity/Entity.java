package com.wscd.cfs.entity;

/**
 * @ClassName: Entity
 * @Description: TODO
 * @Author chengfei
 * @Date 2020/1/14 17:04
 * @Version 1.0
 **/
public class Entity{
    private String sou_dir;
    private String sou_file;
    private String save_dir;
    private String tar_tabName;
    private String fil_diff_runDate;
    private String system_source;

    public String getSou_dir() {
        return sou_dir;
    }

    public void setSou_dir(String sou_dir) {
        this.sou_dir = sou_dir;
    }

    public String getSou_file() {
        return sou_file;
    }

    public void setSou_file(String sou_file) {
        this.sou_file = sou_file;
    }

    public String getSave_dir() {
        return save_dir;
    }

    public void setSave_dir(String save_dir) {
        this.save_dir = save_dir;
    }

    public String getTar_tabName() {
        return tar_tabName;
    }

    public void setTar_tabName(String tar_tabName) {
        this.tar_tabName = tar_tabName;
    }

    public String getFil_diff_runDate() {
        return fil_diff_runDate;
    }

    public void setFil_diff_runDate(String fil_diff_runDate) {
        this.fil_diff_runDate = fil_diff_runDate;
    }

    public String getSystem_source() {
        return system_source;
    }

    public void setSystem_source(String system_source) {
        this.system_source = system_source;
    }
}
