package in.juspay.expresscheckout.expresscheckout.model;


public class AddCardRequest {

    private String customerId;
    private String customerEmail;
    private String cardNumber;
    private String cardExpYear;
    private String cardExpMonth;
    private String nameOnCard;
    private String nickname;

    //Normal Setters
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
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

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getNickname() {
        return nickname;
    }

    //Setters for method chaining calls
    public AddCardRequest withNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
        return this;
    }

    public AddCardRequest withCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
        return this;
    }

    public AddCardRequest withCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
        return this;
    }

    public AddCardRequest withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public AddCardRequest withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public AddCardRequest withCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public AddCardRequest withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
