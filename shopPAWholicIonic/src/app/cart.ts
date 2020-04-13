import { Customer } from './customer';
import { Listing } from './listing';

export class Cart {
    cartId: number;
    totalPrice: number;
    totalQuantity: number;
    customer: Customer;
    listings: Listing[];

    constructor(cartId?: number) {
        this.cartId = cartId;
        this.totalPrice = 0;
        this.totalQuantity = 0;
    }
}
