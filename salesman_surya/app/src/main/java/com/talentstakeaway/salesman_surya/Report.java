package com.talentstakeaway.salesman_surya;

/**
 * Created by Surya on 12-Jun-17.
 */

public class Report {

    private String sl;
    private String date;
    private String distributor;
    private String salesman;
    private String task_location;
    private String start_time;
    private String end_time;
    private String task_description;
    private String status;

    public Report(String sl,String Date,String Distributor,String Salesman,String task_location,String start_time,String end_time,String task_description,String status){
        this.sl=sl;
        this.date=Date;
        this.distributor=Distributor;
        this.salesman=Salesman;
        this.task_location=task_location;
        this.start_time=start_time;
        this.end_time=end_time;
        this.task_description=task_description;
        this.status=status;

    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getTask_location() {
        return task_location;
    }

    public void setTask_location(String task_location) {
        this.task_location = task_location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTask_description() {
        return task_description;
    }

    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
