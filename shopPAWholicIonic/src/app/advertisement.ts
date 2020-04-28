import { Seller } from './seller';
import {BillingDetail} from './billing-detail';

export class Advertisement {

    advertisementId: number;
    description: string;
    startDate: Date;
    endDate: Date;
    price: number;
    picture: string;
    url: string;
    listDate: Date;

    constructor(advertisementId?: number, description?: string, startDate?: Date, endDate?:Date, 
        price?: number, picture?:string, url?: string, listDate?: Date) {
            this.advertisementId = advertisementId;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
            this.price = price;
            this.picture = picture;
            this.url = url;
            this.listDate = listDate;
    }

}
