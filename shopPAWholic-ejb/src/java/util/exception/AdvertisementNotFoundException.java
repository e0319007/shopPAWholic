/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Shi Zhan
 */
public class AdvertisementNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AdvertisementNotFoundException</code>
     * without detail message.
     */
    public AdvertisementNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AdvertisementNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AdvertisementNotFoundException(String msg) {
        super(msg);
    }
}
