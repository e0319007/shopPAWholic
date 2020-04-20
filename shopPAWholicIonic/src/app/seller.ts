import { User } from './user';


export class Seller extends User{

    constructor(userId?: number, name?: string, email?: string, contactNumber?: string, password?: string, 
        accCreatedDate?: Date) {
        super(userId, name, email, contactNumber, password, accCreatedDate);
    }
}
