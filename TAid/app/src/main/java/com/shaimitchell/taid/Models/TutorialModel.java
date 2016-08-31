package com.shaimitchell.taid.Models;

/**
 * Model class to create and manage Tutorial entries
 */
public class TutorialModel {

    private String url;
    private int id;
    private String code;
    private String taIDs;
    private String studentIDs;
    private int updated = 0;

    public TutorialModel(){}

    public TutorialModel(String url, int id, String code, String taIDs, String studentIDs){
        setUrl(url);
        setId(id);
        setCode(code);
        setTaIDs(taIDs);
        setStudentIDs(studentIDs);
    }

    public TutorialModel(String url, String code, String taIDs, String studentIDs){
        setUrl(url);
        setCode(code);
        setTaIDs(taIDs);
        setStudentIDs(studentIDs);
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTaIDs(String ids){
        taIDs = ids;
    }

    public String getTaIDs() {
        return taIDs;
    }

    public void setStudentIDs(String studentIDs) {
        this.studentIDs = studentIDs;
    }

    public String getStudentIDs() {
        return studentIDs;
    }

    public void setUpdated(){
        if (updated == 0){
            updated = 1;
        }else{
            updated = 0;
        }
    }

    public int getUpdated(){
        return updated;
    }
}
