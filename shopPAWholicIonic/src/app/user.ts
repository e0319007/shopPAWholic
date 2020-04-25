export class User {

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
            this.accCreatedDate = accCreatedDate;
    }

}
