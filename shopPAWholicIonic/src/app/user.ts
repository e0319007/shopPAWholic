export class User {
<<<<<<< HEAD
    userId: number;
    email: string;
    password: string;
    

    constructor(userId: number, email?: string, password?: string) {
        this.email = email;
        this.password = password;
    }
=======

    userId: number;
    name: string;
    email: string;
    contactNumber: string;
    password: string;
    accCreatedDate: Date;

    constructor(userId?: number, name?: string, email?: string, contactNumber?: string, password?: string, 
        accCreatedDate?: Date) {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.contactNumber = contactNumber;
            this.password = password;
    }

>>>>>>> master
}
