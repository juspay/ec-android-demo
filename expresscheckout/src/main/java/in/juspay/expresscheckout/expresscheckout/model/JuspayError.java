package in.juspay.expresscheckout.expresscheckout.model;

/**
 * Created by vimal on 2/6/15.
 */
public class JuspayError extends Exception {
    public JuspayError() { super(); }

    public JuspayError(String msg) { super(msg); }

    public JuspayError(String msg, Throwable t) { super(msg, t); }

}
