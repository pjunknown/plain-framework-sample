package jp.techie.plain.sample.action;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import jp.techie.plain.framework.Const;
import jp.techie.plain.framework.action.Action;
import jp.techie.plain.framework.servlet.WebApplicationValue;
import jp.techie.plain.framework.util.LogUtil;
import jp.techie.plain.sample.service.BookListServise;
import jp.techie.plain.sample.service.input.BookListInput;
import jp.techie.plain.sample.service.output.BookListOutput;

@Stateless
public class BookListAction implements Action {

    /**
     * ログユーティリティ
     */
    public static LogUtil logUtil = new LogUtil(BookListAction.class);

    /**
     * サービスクラス（EJBコンテナによりDIされる）
     */
    @EJB
    public BookListServise bookListServise;

    /* (non-Javadoc)
     * @see jp.techie.plain.framework.action.Action#execute(jp.techie.plain.framework.servlet.WebApplicationValue)
     */
    @Override
    public String execute(WebApplicationValue webApplicationValue) {
        // 処理後遷移先
        String dispatchUrl = "/template/bookList.html";

        // サービス実行に必要な値をBeanにしてバリデーションする
        BookListInput bookListInput = makeInput(webApplicationValue);
        String errorMessages = validateInput(bookListInput);
        logUtil.debug(String.valueOf(errorMessages.length()));
        if (errorMessages.length() > 0) {
            webApplicationValue.putRequestAttribute(Const.ERROR_MESSAGES_REQUEST_KEY, errorMessages);
            // バリデーションエラーが有った場合は同じURLにdispatchして表示
            return dispatchUrl;
        }
        // Beanでの受け渡しが冗長化とおもわれる場合はサービスに webApplicationValue を渡してもOK
        BookListOutput bookListOutput = bookListServise.getBookList(bookListInput);
        // サービスから受け取ったBeanで画面表示に必要な値をセット
        setWebApplicationValue(webApplicationValue, bookListOutput);

        // 正常終了してdispath
        return dispatchUrl;
    }

    /**
     * 画面に表示する値をWebApplicationValueにセットする
     * 
     * @param webApplicationValue WebApplicationValue
     * @param bookListInput bookListサービス入力値
     */
    protected void setWebApplicationValue(WebApplicationValue webApplicationValue, BookListOutput bookListOutput) {
        webApplicationValue.putRequestAttribute("books", bookListOutput.displayBooks);
        webApplicationValue.putRequestAttribute("pageNumber", bookListOutput.pageNumber);
        webApplicationValue.putRequestAttribute("beforePageUrl", bookListOutput.beforePageUrl);
        webApplicationValue.putRequestAttribute("nextPageUrl", bookListOutput.nextPageUrl);
    }

    /**
     * サービス実行時に必要な項目を作成
     * 
     * @param webApplicationValue WebApplicationValue
     * @return BookListInput サービスへのインプット項目
     */
    protected BookListInput makeInput(WebApplicationValue webApplicationValue) {

        BookListInput bookListInput = new BookListInput();

        // urlパラメータ値 Action名以降が/区切りでリスト化されている
        List<String> urlParamList = webApplicationValue.urlParamList;

        // throwAwayFlagの値設定
        if (urlParamList.size() > 0) {
            bookListInput.throwAwayFlag = urlParamList.get(0);
        } else {
            // デフォルトの0をセットする
            bookListInput.throwAwayFlag = "0";
        }

        logUtil.debug("makeInput:throwAwayFlag " + bookListInput.throwAwayFlag);

        // pageNumberの値設定
        if (urlParamList.size() > 1) {
            bookListInput.pageNumber = urlParamList.get(1);
        } else {
            // デフォルトの1をセットする
            bookListInput.pageNumber = "1";
        }

        logUtil.debug("makeInput:pageNumber " + bookListInput.pageNumber);

        // 書籍名検索文字列の値設定
        if (urlParamList.size() > 2) {
            bookListInput.bookName = urlParamList.get(2);
        }

        logUtil.debug("makeInput:bookName " + bookListInput.bookName);
        logUtil.debug("makeInput:size " + urlParamList.size());

        return bookListInput;
    }

    /**
     * BeanValidationによるInput値チェック<br />
     * Requestからくる値はStringなのであえてStringで生成してチェックしてる<br />
     * このバリデーションを抜ければCastしてもExceptionは起こらない
     * 
     * @param bookListInputValue ブラウザからの入力値
     * @return String Validationエラーメッセージ
     */
    protected String validateInput(BookListInput bookListInputValue) {
        StringBuffer stringBuffer = new StringBuffer("");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BookListInput>> constraintViolations = validator.validate(bookListInputValue);
        for (ConstraintViolation<BookListInput> constraintViolation : constraintViolations) {
            stringBuffer.append(constraintViolation.getMessage());
            stringBuffer.append("<br />\n");
        }
        return stringBuffer.toString();
    }

}
