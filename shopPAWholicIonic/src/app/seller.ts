<<<<<<< HEAD
import { User } from './user';

export class Seller extends User{

    constructor(userId?: number, name?: string, email?: string, contactNumber?: string, password?: string, 
        accCreatedDate?: Date) {
        super(userId, name, email, contactNumber, password, accCreatedDate);
    }
}
=======
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
>>>>>>> bd46c27a25a382c33ac0cb1bb7a3804a2c2db01e
