package com.example.myapplication.beans;

import android.content.ContentValues;
import android.database.Cursor;

public class ComplainBean {
      Integer id;
      Integer fromId;
      Integer toId;
      String  fromName;
      String  toName;
      Integer oId;
      Integer status;
      Integer grade;
      String comment;


      public static ContentValues getValues(ComplainBean bean){
            ContentValues cv = new ContentValues();
            cv.put("fromId", bean.fromId);
            cv.put("toId", bean.toId);
            cv.put("from_name", bean.fromName);
            cv.put("to_name", bean.toName);
            cv.put("oId", bean.oId);
            cv.put("status", bean.status);
            cv.put("grade", bean.grade);
            cv.put("comment", bean.comment);
            return cv;
      }
      public static ComplainBean getComplainBean(Cursor cursor){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int fromId = cursor.getInt(cursor.getColumnIndex("fromId"));
            int toId = cursor.getInt(cursor.getColumnIndex("toId"));
            String fromName = cursor.getString(cursor.getColumnIndex("from_name"));
            String toName = cursor.getString(cursor.getColumnIndex("to_name"));
            int oId = cursor.getInt(cursor.getColumnIndex("oId"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            int grade = cursor.getInt(cursor.getColumnIndex("grade"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));
            ComplainBean  complainBean = new ComplainBean();
            complainBean.setId(id);
            complainBean.setFromId(fromId);
            complainBean.setToId(toId);
            complainBean.setFromName(fromName);
            complainBean.setToName(toName);
            complainBean.setoId(oId);
            complainBean.setStatus(status);
            complainBean.setGrade(grade);
            complainBean.setComment(comment);
            return complainBean;
      }

      public Integer getId() {
            return id;
      }

      public void setId(Integer id) {
            this.id = id;
      }

      public Integer getFromId() {
            return fromId;
      }

      public void setFromId(Integer fromId) {
            this.fromId = fromId;
      }

      public Integer getToId() {
            return toId;
      }

      public void setToId(Integer toId) {
            this.toId = toId;
      }

      public Integer getoId() {
            return oId;
      }

      public void setoId(Integer oId) {
            this.oId = oId;
      }

      public Integer getStatus() {
            return status;
      }

      public void setStatus(Integer status) {
            this.status = status;
      }

      public Integer getGrade() {
            return grade;
      }

      public void setGrade(Integer grade) {
            this.grade = grade;
      }

      public String getComment() {
            return comment;
      }

      public void setComment(String comment) {
            this.comment = comment;
      }

      public String getFromName() {
            return fromName;
      }

      public void setFromName(String fromName) {
            this.fromName = fromName;
      }

      public String getToName() {
            return toName;
      }

      public void setToName(String toName) {
            this.toName = toName;
      }
}
