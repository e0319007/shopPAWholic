import { Tag } from './tag';
import { Seller } from './seller';
import { Category } from './category';

export class Listing {

    listingId: number;
    skuCode: string;
    name: string;
    description: string;
    quantityOnHand: number;
    unitPrice: number;
    tags: Tag[];
    seller: Seller;
    listingDate: Date;
    category: Category
    picture: string;

    constructor(listingId?: number, skuCode?: string, name?: string, description?: string, 
        quantityOnHand?: number, unitPrice?: number, category?: Category, picture?: string) {
            this.listingId = listingId;
            this.skuCode = skuCode;
            this.name = name;
            this.description = description;
            this.quantityOnHand = quantityOnHand;
            this.unitPrice = unitPrice;
            let currDate: Date = new Date();
            this.listingDate = currDate;
            this.category = category;
            this.picture = picture
        }
}

