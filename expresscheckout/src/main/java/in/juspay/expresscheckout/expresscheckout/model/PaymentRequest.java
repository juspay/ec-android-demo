package in.juspay.expresscheckout.expresscheckout.model;

public class PaymentRequest {

    private String orderId;
    private String paymentMethodType;
    private String paymentMethod;

    // Card Payment Fields
    private String cardToken;
    private String cardNumber;
    private String nameOnCard;
    private String cardExpYear;
    private String cardExpMonth;
    private String cardSecurityCode;

    private Boolean saveToLocker = false;
    private Boolean redirectAfterPayment = true;
    private String format = "json";

    public String getOrderId() {
        return orderId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    //to chain the calls
    public PaymentRequest withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentRequest withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public PaymentRequest withCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
        return this;
    }

    public PaymentRequest withCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
        return this;
    }

    public PaymentRequest withCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
        return this;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public Boolean getSaveToLocker() {
        return saveToLocker;
    }

    public void setSaveToLocker(Boolean saveToLocker) {
        this.saveToLocker = saveToLocker;
    }

    public Boolean getRedirectAfterPayment() {
        return redirectAfterPayment;
    }

    public void setRedirectAfterPayment(Boolean redirectAfterPayment) {
        this.redirectAfterPayment = redirectAfterPayment;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }


}
