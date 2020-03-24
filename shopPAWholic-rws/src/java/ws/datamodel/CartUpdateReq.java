/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Cart;

/**
 *
 * @author shizhan
 */
public class CartUpdateReq {
    private Cart cart;
    private String email;
    private String password;

    public CartUpdateReq() {
    }

    public CartUpdateReq(Cart cart, String email, String password) {
        this.cart = cart;
        this.email = email;
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
