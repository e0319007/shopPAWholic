import {Listing} from './listing';
import {Customer} from './customer';

export class Review {

    reviewId: number;
    description: string;
    rating: number;
    reviewDate: Date;
    reviewPictures: string[];

    constructor(reviewId?:number, description?:string, rating?:number, reviewDate?: Date, reviewPictures?:string[]){
        this.reviewId = reviewId;
        this.description = description;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.reviewPictures = reviewPictures;
    }

}
