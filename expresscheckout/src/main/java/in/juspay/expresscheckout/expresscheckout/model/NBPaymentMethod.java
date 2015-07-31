package in.juspay.expresscheckout.expresscheckout.model;

/**
 * Created by vimal on 1/6/15.
 */
public enum NBPaymentMethod {

    NB_AXIS("Axis Bank"),
    NB_BOI("Bank of India"),
    NB_BOM("Bank of Maharashtra"),
    NB_CBI("Central Bank Of India"),
    NB_CORP("Corporation Bank"),
    NB_DCB("Development Credit Bank"),
    NB_FED("Federal Bank"),
    NB_HDFC("HDFC Bank"),
    NB_ICICI("ICICI Netbanking"),
    NB_IDBI("Industrial Development Bank of India"),
    NB_INDB("Indian Bank"),
    NB_INDUS("IndusInd Bank"),
    NB_IOB("Indian Overseas Bank"),
    NB_JNK("Jammu and Kashmir Bank"),
    NB_KARN("Karnataka Bank"),
    NB_KVB("Karur Vysya"),
    NB_SBBJ("State Bank of Bikaner and Jaipur"),
    NB_SBH("State Bank of Hyderabad"),
    NB_SBI("State Bank of India"),
    NB_SBM("State Bank of Mysore"),
    NB_SBT("State Bank of Travancore"),
    NB_SOIB("South Indian Bank"),
    NB_UBI("Union Bank of India"),
    NB_UNIB("United Bank Of India"),
    NB_VJYB("Vijaya Bank"),
    NB_YESB("Yes Bank"),
    NB_CUB("CityUnion"),
    NB_CANR("Canara Bank"),
    NB_SBP("State Bank of Patiala"),
    NB_CITI("Citi Bank NetBanking"),
    NB_DEUT("Deutsche Bank"),
    NB_KOTAK("Kotak Bank"),
    NB_DLS("Dhanalaxmi Bank"),
    NB_ING("ING Vysya Bank");

    private String description;

    NBPaymentMethod(String desc) {
        description = desc;
    }

    public String getDescription() { return description; }

}
