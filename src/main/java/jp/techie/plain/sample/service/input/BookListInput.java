package jp.techie.plain.sample.service.input;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * BookListService入力Bean
 * 
 * @author bose999
 *
 */
@SuppressWarnings("serial")
public class BookListInput implements Serializable{
    
    /**
     * 破棄フラグ
     */
    @NotNull
    @Pattern(regexp = "[0-1]", message = "{throwAwayFlag}")
    public String throwAwayFlag = null;
    
    /**
     * ページ番号
     */
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "{pageNumber}")
    public String pageNumber  = null;
    
    /**
     * 検索書籍名
     */
    public String bookName = null;
    
    /**
     * コンストラクタ
     */
    public BookListInput(){
    }
    
    /**
     * コンストラクタ
     * 
     * @param throwAwayFlag 破棄フラグ文字列
     * @param pageNumber ページ番号文字列
     */
    public BookListInput(String throwAwayFlag,String pageNumber){
        this.throwAwayFlag = throwAwayFlag;
        this.pageNumber = pageNumber;
    }
    
    /**
     * throwAwayFlagをintに変換して返す
     * 
     * @return int throwAwayFlag
     */
    public int getThrowAwayFlag(){
        return Integer.valueOf(throwAwayFlag);
    }
    
    /**
     * pageNumberをintに変換して返す
     * 
     * @return int pageNumber
     */
    public int getPageNumber(){
        return Integer.valueOf(pageNumber);
    }  
}
