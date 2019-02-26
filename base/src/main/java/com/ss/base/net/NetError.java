package com.ss.base.net;

public class NetError extends Exception {
    private Throwable exception;
    private int type = NoConnectError;

    public static final int NotSubscribe = 0;   //无连接异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int ParseError = 2;   //数据解析异常
    public static final int AuthError = -106;   //token过期
    public static final int BalanceError = -22;   //余额不足
    public static final int NoDataError = 3;   //用户验证异常
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常

    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    public static NetError noDataError() {
        return new NetError("当前无数据", NetError.NoDataError);
    }

    public static NetError noConnectError() {
        return new NetError("网络不通畅,请检查后再试", NetError.NoConnectError);
    }

    @Override
    public String getMessage() {
        if (exception != null) return exception.getMessage();
        return super.getMessage();
    }

    public Throwable getException() {
        return exception;
    }

    public int getType() {
        return type;
    }
}
