package in.juspay.expresscheckout.expresscheckout.model;

public class PaymentResponse {

    private String status;
    private String txnId;
    private String orderId;
    private String redirectUrl;
    private String redirectUrlHttpMethod;
    private String redirectUrlPostParams;

    public String getStatus() {
        return status;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void vbvUrl(String vbvUrl) {
        this.redirectUrl = vbvUrl;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrlHttpMethod() {
        return redirectUrlHttpMethod;
    }

    public void setRedirectUrlHttpMethod(String redirectUrlHttpMethod) {
        this.redirectUrlHttpMethod = redirectUrlHttpMethod;
    }

    public String getRedirectUrlPostParams() {
        return redirectUrlPostParams;
    }

    public void setRedirectUrlPostParams(String redirectUrlPostParams) {
        this.redirectUrlPostParams = redirectUrlPostParams;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "status='" + status + '\'' +
                ", txnId='" + txnId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", redirectUrlHttpMethod='" + redirectUrlHttpMethod + '\'' +
                ", redirectUrlPostParams='" + redirectUrlPostParams + '\'' +
                '}';
    }
}
