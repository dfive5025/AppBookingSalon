package com.example.myapplication.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {

   String name;
   String mobile;
   String email;
   String addres;
   String order;
   String note;
   ArrayList<Services> services=new ArrayList<>();
   String date;
   String time_begin;
   String time_finish;
   ArrayList<Staffcuthair> staffcuthairList1=new ArrayList<Staffcuthair>();
   String id_order;
   String id_saloon;
   @SerializedName("staffcuthairList")
   String jsonlist;
   @SerializedName("serviceslist")
   String jsonlistservice;
   String status;
   String payment;
   int amount;

   public Request() {

   }

   public Request(String name, String mobile, String email, String addres, String note, String order, ArrayList<Services> services, String date, String time_begin, String time_finish, ArrayList<Staffcuthair> staffcuthairList, String id_order, String id_saloon, String status, String payment, int amount) {
      this.name = name;
      this.mobile = mobile;
      this.email = email;
      this.addres = addres;
      this.order = order;
      this.services = services;
      this.date = date;
      this.time_begin = time_begin;
      this.time_finish = time_finish;
      this.staffcuthairList1 = staffcuthairList;
      this.id_order = id_order;
      this.id_saloon = id_saloon;
      this.status = status;
      this.payment = payment;
      this.note= note;
      this.amount=amount;
   }

   public String getJsonlistservice() {
      return jsonlistservice;
   }

   public void setJsonlistservice(String jsonlistservice) {
      this.jsonlistservice = jsonlistservice;
   }

   public String getJsonlist() {
      return jsonlist;
   }

   public void setJsonlist(String jsonlist) {
      this.jsonlist = jsonlist;
   }

   public Request(String name, String mobile, String email, String addres, String order, ArrayList<Services> services, String date, String time_begin, String time_finish, ArrayList<Staffcuthair> staffcuthairList, String id_order, String id_saloon, String status, String payment) {
      this.name = name;
      this.mobile = mobile;
      this.email = email;
      this.addres = addres;
      this.order = order;
      this.services = services;
      this.date = date;
      this.time_begin = time_begin;
      this.time_finish = time_finish;
      this.staffcuthairList1 = staffcuthairList;
      this.id_order = id_order;
      this.id_saloon = id_saloon;
      this.status = status;
      this.payment = payment;
   }
   public void setName(String name) {
      this.name = name;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setAddres(String addres) {
      this.addres = addres;
   }

   public void setOrder(String order) {
      this.order = order;
   }

   public void setServices(ArrayList<Services> services) {
      this.services = services;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public void setTime_begin(String time_begin) {
      this.time_begin = time_begin;
   }

   public void setTime_finish(String time_finish) {
      this.time_finish = time_finish;
   }

   public void setStaffcuthairList(ArrayList<Staffcuthair> staffcuthairList) {
      this.staffcuthairList1 = staffcuthairList;
   }

   public void setId_order(String id_order) {
      this.id_order = id_order;
   }

   public void setId_saloon(String id_saloon) {
      this.id_saloon = id_saloon;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public void setPayment(String payment) {
      this.payment = payment;
   }

   public String getName() {
      return name;
   }

   public String getMobile() {
      return mobile;
   }

   public String getEmail() {
      return email;
   }

   public String getAddres() {
      return addres;
   }

   public String getOrder() {
      return order;
   }

   public ArrayList<Services> getServices() {
      return services;
   }

   public String getDate() {
      return date;
   }

   public String getTime_begin() {
      return time_begin;
   }

   public String getTime_finish() {
      return time_finish;
   }

   public ArrayList<Staffcuthair> getStaffcuthairList() {
      return staffcuthairList1;
   }

   public String getId_order() {
      return id_order;
   }

   public String getId_saloon() {
      return id_saloon;
   }

   public String getStatus() {
      return status;
   }

   public String getPayment() {
      return payment;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }
}
