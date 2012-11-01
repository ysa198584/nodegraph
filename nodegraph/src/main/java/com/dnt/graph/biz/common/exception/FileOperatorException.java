package com.dnt.graph.biz.common.exception;
public  class FileOperatorException extends Exception {

    private static final long   serialVersionUID = 1592174557251842264L;
    private static final String SEPARATOR        = " : ";
    /**
     * 错误代码
     */
    private int                 errorCode;
    /**
     * 详细错误信息
     */
    private String              errorMessage;

    public FileOperatorException() {
    	
    };

    /**
     * 设置异常信息
     * 
     * @param message
     */
    public FileOperatorException(int errorCode, String errorMessage,String errorPath) {
        super(errorCode + SEPARATOR + errorMessage+SEPARATOR +errorPath);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public FileOperatorException(int errorCode, String errorMessage) {
        super(errorCode + SEPARATOR + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    /**
     * 设置异常信息和原始堆栈
     * 
     * @param message
     */
    public FileOperatorException(int errorCode, String errorMessage,String errorPath, Throwable cause) {
        super(errorCode + SEPARATOR + errorMessage+SEPARATOR +errorPath, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
