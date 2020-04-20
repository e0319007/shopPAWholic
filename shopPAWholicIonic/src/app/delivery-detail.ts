import { DeliveryMethod } from './delivery-method.enum';

export class DeliveryDetail {
    deliveryDetailId: number;
    address: string;
    contactNumber: string;
    deliveryDate: Date;
    statusLists: string[];
    deliveryMethod: DeliveryMethod;

    constructor(deliveryDetailId?: number,  address?: string, deliverDate?: Date, statusList?: string[]){
        this.deliveryDetailId = deliveryDetailId;
        this.address = address;
        this.deliveryDate = deliverDate;
        this.statusLists = statusList;
    }
}
