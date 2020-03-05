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
public class CreateNewDeliveryDetailException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewDeliveryDetailException</code>
     * without detail message.
     */
    public CreateNewDeliveryDetailException() {
    }

    /**
     * Constructs an instance of <code>CreateNewDeliveryDetailException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewDeliveryDetailException(String msg) {
        super(msg);
    }
}
