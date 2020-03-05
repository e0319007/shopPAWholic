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
public class DeliveryDetailNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>DeliveryDetailNotFoundException</code>
     * without detail message.
     */
    public DeliveryDetailNotFoundException() {
    }

    /**
     * Constructs an instance of <code>DeliveryDetailNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeliveryDetailNotFoundException(String msg) {
        super(msg);
    }
}
