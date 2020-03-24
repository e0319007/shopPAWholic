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
public class CreateNewBillingDetailException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewBillingDetailException</code>
     * without detail message.
     */
    public CreateNewBillingDetailException() {
    }

    /**
     * Constructs an instance of <code>CreateNewBillingDetailException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewBillingDetailException(String msg) {
        super(msg);
    }
}
