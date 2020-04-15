import { DeliveryMethod } from './delivery-method.enum';

export class DeliveryDetail {
    deliveryDetailId: number;
    address: string;
    contactNumber: string;
    deliveryDate: Date;
    statusList: string[];
    deliveryMethod: DeliveryMethod;

    constructor(deliveryDetailId?: number,  address?: string, deliverDate?: Date){
        this.deliveryDetailId = deliveryDetailId;
        this.address = address;
        this.deliveryDate = deliverDate;
    }
}
