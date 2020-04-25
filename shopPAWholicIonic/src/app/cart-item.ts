import { Listing } from './listing';

export class CartItem {

    listing: Listing;
    quantity: number;

    constructor(listing?: Listing, quantity?: number) {
        this.listing = listing;
        this.quantity = quantity;
    }

}
