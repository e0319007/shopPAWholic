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
public class CreateNewCommentException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewCommentException</code> without
     * detail message.
     */
    public CreateNewCommentException() {
    }

    /**
     * Constructs an instance of <code>CreateNewCommentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewCommentException(String msg) {
        super(msg);
    }
}
