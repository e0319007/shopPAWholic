import { User } from './user';

export class Seller extends User{

    verified: boolean;
    totalRating: number;

    constructor(userId?: number, name?: string, email?: string, contactNumber?: string, password?: string, 
        accCreatedDate?: Date, verified?: boolean, totalRating?: number) {
        super(userId, name, email, contactNumber, password, accCreatedDate);
        this.verified = verified;
        this.totalRating = totalRating;
    }
}
