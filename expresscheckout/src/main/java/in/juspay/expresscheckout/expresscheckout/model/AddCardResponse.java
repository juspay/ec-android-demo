package in.juspay.expresscheckout.expresscheckout.model;

public class AddCardResponse {

    private String cardToken;
    private String cardReference;

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getCardReference() {
        return cardReference;
    }

    public void setCardReference(String cardReference) {
        this.cardReference = cardReference;
    }

    @Override
    public String toString() {
        return "AddCardResponse{" +
                "cardToken='" + cardToken + '\'' +
                ", cardReference='" + cardReference + '\'' +
                '}';
    }
}
