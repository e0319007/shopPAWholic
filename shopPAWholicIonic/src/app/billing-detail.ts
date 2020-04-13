
import { Seller } from './seller';
import { Customer } from './customer';
import { Advertisement } from './advertisement';
import { OrderEntity } from './order-entity';

export class BillingDetail {
    billingDetailId: number;
    creditCardDetail: string;
    billingDate: string;
    customer: Customer;
    seller: Seller;
    advertisement: Advertisement;
    order: OrderEntity
    
    constructor(billingDetailId?: number, creditCardDetail?: string, billingDate ?: string,) {

    }
}
