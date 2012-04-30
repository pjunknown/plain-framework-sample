package jp.techie.plain.sample.service.output;

import java.io.Serializable;
import java.util.List;

import jp.techie.plain.sample.entity.Book;

/**
 * BookListService 返り値クラス
 * 
 * @author bose999
 *
 */
@SuppressWarnings("serial")
public class BookListOutput implements Serializable {

    /**
     * 画面表示書籍
     */
    public List<Book> displayBooks;
    
    public int pageNumber;

    /**
     * 前のページリンクURL
     */
    public String beforePageUrl;

    /**
     * 次のページリンクURL
     */
    public String nextPageUrl;

}
