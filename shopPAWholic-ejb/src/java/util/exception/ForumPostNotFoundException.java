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
public class ForumPostNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ForumPostNotFoundException</code> without
     * detail message.
     */
    public ForumPostNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ForumPostNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ForumPostNotFoundException(String msg) {
        super(msg);
    }
}
