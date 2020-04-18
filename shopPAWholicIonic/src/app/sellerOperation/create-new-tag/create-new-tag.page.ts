import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tag } from 'src/app/tag';
import { TagService } from 'src/app/tag.service';

@Component({
  selector: 'app-create-new-tag',
  templateUrl: './create-new-tag.page.html',
  styleUrls: ['./create-new-tag.page.scss'],
})
export class CreateNewTagPage implements OnInit {

  constructor(private router: Router,
              private tagService: TagService) { }

  newTag: Tag = new Tag();
  tags: Tag[];

  errorMessage: string = null;

  ngOnInit() {
    this.tagService.retrieveAllTags().subscribe(
      response => {
        this.tags = response.tags;
      },
      error => {
        console.log("Retrieve Tags in CreateNewListingPage: " + error);
      }
    )
  }

  back() {
    this.router.navigate(["/sellerOperation/createNewListing"]);
  }

  keyPress() {
    this.errorMessage = null;
  }

  create() {
    for(let t of this.tags) {
      if(t.name == this.newTag.name) {
        this.errorMessage = "Tag already exists!"
        return;
      }
    } 
    this.tagService.createNewTag(this.newTag).subscribe(
      response => {
        this.errorMessage = null;
        this.back();
      },
      error => {
        this.errorMessage = "Create tag error!"
      }
    )
  }

}
