import { OrderEntity } from './order-entity';

export class OrderDisplay {
    order: OrderEntity;
    date: string;

    constructor(order?: OrderEntity, date?: string) {
        this.order = order;
        this.date = date;
    }
}
