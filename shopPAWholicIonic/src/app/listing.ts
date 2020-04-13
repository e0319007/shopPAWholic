import { Tag } from './tag';
import { Seller } from './seller';

export class Listing {

    listingId: number;
    skuCode: string;
    name: string;
    description: string;
    quantityOnHand: number;
    unitPrice: number;
    tags: Tag[];
    seller: Seller;

    constructor(listingId?: number, skuCode?: string, name?: string, description?: string, 
        quantityOnHand?: number, unitPrice?: number) {
            this.listingId = listingId;
            this.skuCode = skuCode;
            this.name = name;
            this.description = description;
            this.quantityOnHand = quantityOnHand;
            this.unitPrice = unitPrice;
        }
}
