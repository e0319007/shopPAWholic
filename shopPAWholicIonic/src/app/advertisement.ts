import { Seller } from './seller';
import {BillingDetail} from './billing-detail';

export class Advertisement {

    advertisementId: number;
    description: string;
    startDate: Date;
    endDate: Date;
    price: number;
    pictures: string[];
    url: string;

    constructor(advertisementId?: number, description?: string, startDate?: Date, endDate?:Date, 
        price?: number, pictures?:string[], url?: string) {
            this.advertisementId = advertisementId;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
            this.price = price;
            this.pictures = pictures;
            this.url = url;
    }

}
