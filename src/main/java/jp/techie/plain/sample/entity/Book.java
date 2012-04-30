package jp.techie.plain.sample.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Book implements Serializable{
    
    public int id =0;
    
    public String name;
    
    public Timestamp purchaseDate;
    
    public int throwAwaryFlag = 0;
    
    public Timestamp createDate;
    
    public Timestamp editDate;
    
    public Date getPurchaseDate4Date(){
        return new Date(purchaseDate.getTime());
    }
    
    public Date getCreateDate4Date(){
        return new Date(createDate.getTime());
    }
    
    public Date getEditDate4Date(){
        try {
            Timestamp object = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse("2006/10/10").getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Date(editDate.getTime());
    }
}
