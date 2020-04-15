
import { Seller } from './seller';
import { Customer } from './customer';
import { Advertisement } from './advertisement';
import { OrderEntity } from './order-entity';

export class BillingDetail {
    billingDetailId: number;
    creditCardDetail: string;
    billingDate: Date;
    customer: Customer;
    seller: Seller;  
    
    constructor(billingDetailId?: number, creditCardDetail?: string, billingDate ?: Date,) {
        this.billingDetailId = billingDetailId;
        this.creditCardDetail = creditCardDetail;
        this.billingDate = billingDate;
    }
}
