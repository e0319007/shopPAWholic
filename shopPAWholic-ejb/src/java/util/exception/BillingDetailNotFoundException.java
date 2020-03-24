/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Joanna Ng
 */
public class BillingDetailNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>BillingDetailNotFoundException</code>
     * without detail message.
     */
    public BillingDetailNotFoundException() {
    }

    /**
     * Constructs an instance of <code>BillingDetailNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public BillingDetailNotFoundException(String msg) {
        super(msg);
    }
}
