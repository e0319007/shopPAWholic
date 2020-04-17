import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-new-tag',
  templateUrl: './create-new-tag.page.html',
  styleUrls: ['./create-new-tag.page.scss'],
})
export class CreateNewTagPage implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  back() {
    this.router.navigate(["/sellerOperation/createNewListing"]);
  }

}
