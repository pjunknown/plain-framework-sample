package jp.techie.plain.sample.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import jp.techie.plain.framework.util.LogUtil;
import jp.techie.plain.sample.action.BookListAction;
import jp.techie.plain.sample.entity.Book;
import jp.techie.plain.sample.service.input.BookListInput;
import jp.techie.plain.sample.service.output.BookListOutput;

@Stateless
public class BookListServise {

    /**
     * ログユーティリティ
     */
    private static LogUtil logUtil = new LogUtil(BookListServise.class);

    public BookListOutput getBookList(BookListInput bookListInput) {
        // TODO まだMock bookListInputの値で検索する

        List<Book> searchResultBooks = new ArrayList<Book>();
        Book book = new Book();
        //        book.id = 1;
        //        book.name = "JavaEE6入門";
        //        book.purchaseDate = new Timestamp(2012, 4, 12, 12, 0, 0, 0);
        //        book.throwAwaryFlag = 0;
        //        book.createDate = new Timestamp(2012, 4, 13, 12, 0, 0, 0);
        //        book.editDate = new Timestamp(2012, 4, 14, 12, 0, 0, 0);
        //        searchResultBooks.add(book);
        //        book = new Book();
        //        book.id = 2;
        //        book.name = "JavaEE6入門2";
        //        book.purchaseDate = new Timestamp(2012, 4, 13, 12, 0, 0, 0);
        //        book.throwAwaryFlag = 0;
        //        book.createDate = new Timestamp(2012, 4, 14, 12, 0, 0, 0);
        //        book.editDate = new Timestamp(2012, 4, 15, 12, 0, 0, 0);
        //        searchResultBooks.add(book);
        int count = 1;
        while (count < 124) {
            logUtil.debug("getBookList: count " + count);
            book = new Book();
            book.id = count;
            book.name = "JavaEE6入門" + count;
            book.purchaseDate = new Timestamp(112, 3, 12, 12, 0, 0, 0);
            if (count % 2 == 0) {
                book.throwAwaryFlag = 0;
            } else {
                book.throwAwaryFlag = 1;
            }

            book.createDate = new Timestamp(112, 3, 13, 12, 0, 0, 0);
            book.editDate = new Timestamp(112, 3, 14, 12, 0, 0, 0);
            searchResultBooks.add(book);

            count++;

        }

        logUtil.debug("getBookList: searchResultBooks.size() " + searchResultBooks.size());

        int pageNumber = checkPageNumber(bookListInput, searchResultBooks);
        bookListInput.pageNumber = String.valueOf(pageNumber);
        logUtil.debug("getBookList: bookListInput.pageNumber " + bookListInput.pageNumber);

        BookListOutput bookListOutput = new BookListOutput();
        bookListOutput.pageNumber = pageNumber;
        bookListOutput.displayBooks = choiceDisplayBooks(searchResultBooks, pageNumber);
        bookListOutput.beforePageUrl = buildBeforePageUrl(bookListInput, searchResultBooks);
        bookListOutput.nextPageUrl = buildNextPageUrl(bookListInput, searchResultBooks);

        return bookListOutput;
    }

    protected List<Book> choiceDisplayBooks(List<Book> searchResultBooks, int pageNumber) {
        List<Book> displayBooks = new ArrayList<Book>();
        int count = 1;
        int displayFirstBookCount = 20 * (pageNumber - 1) + 1;
        logUtil.debug("choiceDisplayBooks: displayFirstBookCount " + displayFirstBookCount);
        int displayEndBookCount = displayFirstBookCount + 19;
        logUtil.debug("choiceDisplayBooks: displayEndBookCount " + displayEndBookCount);
        for (Book book : searchResultBooks) {
            if (count <= displayEndBookCount && displayFirstBookCount <= count) {
                displayBooks.add(book);
                logUtil.debug("choiceDisplayBooks: add " + count);
            }
            count++;
        }
        return displayBooks;
    }

    protected int checkPageNumber(BookListInput bookListInput, List<Book> books) {
        // Page数の調整とリンクの作成
        int pageNumber = bookListInput.getPageNumber();
        int maxPageNumber = books.size() / 20;
        if (books.size() % 20 > 0) {
            maxPageNumber++;
        }
        if (pageNumber == 0) {
            return 1;
        } else if (pageNumber <= maxPageNumber) {
            return pageNumber;
        }else {
            return maxPageNumber;
        }
    }

    protected String buildBeforePageUrl(BookListInput bookListInput, List<Book> books) {
        // Page数の調整とリンクの作成
        String beforePageUrl = null;
        int pageNumber = bookListInput.getPageNumber();
        int maxPageNumber = books.size() / 20;
        if (books.size() % 20 > 0) {
            maxPageNumber++;
        }
        int beforePageNumber = pageNumber;
        if (maxPageNumber >= pageNumber) {
            beforePageNumber--;
        }
        if (pageNumber > 1) {
            beforePageUrl = "/action/bookList/" + bookListInput.throwAwayFlag + "/" + beforePageNumber;
            if (bookListInput.bookName != null) {
                beforePageUrl = beforePageUrl + "/" + bookListInput.bookName;
            }
        }
        return beforePageUrl;
    }

    protected String buildNextPageUrl(BookListInput bookListInput, List<Book> books) {
        // Page数の調整とリンクの作成
        String nextPageUrl = null;
        int pageNumber = Integer.valueOf(bookListInput.pageNumber);
        int maxPageNumber = books.size() / 20;
        if (books.size() % 20 > 0) {
            maxPageNumber++;
        }
        int nextPageNumber = pageNumber;
        if (maxPageNumber > pageNumber) {
            nextPageNumber++;
        }
        if (pageNumber < maxPageNumber) {
            nextPageUrl = "/action/bookList/" + bookListInput.throwAwayFlag + "/" + nextPageNumber;
            if (bookListInput.bookName != null) {
                nextPageUrl = nextPageUrl + "/" + bookListInput.bookName;
            }
        }

        return nextPageUrl;
    }

}
