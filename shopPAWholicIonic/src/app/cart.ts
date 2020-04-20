import { Customer } from './customer';
import { Listing } from './listing';

export class Cart {
    cartId: number;
    totalPrice: number;
    totalQuantity: number;
    customer: Customer;
    listings: Listing[];

    constructor(cartId?: number, totalPrice?: number, totalQuantity?: number, listings?: Listing[]) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.listings = listings;
    }
}
