import { Component, OnInit } from '@angular/core';
import { PendingPost } from '../models';
import { Router } from '@angular/router';
import { PostService } from '../post.service';
import { state } from '@angular/animations';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  postDetails!: PendingPost

  constructor(private router: Router, private postSvc: PostService) { }

  ngOnInit(): void {
    this.postDetails = window.history.state.data as PendingPost
    console.info()
    console.info(">> postdetails: ", this.postDetails.image)
  }

  goBack() {
    this.router.navigate(['/'])
  }

  confirm() {
    const postingId = this.postDetails.postingId
    this.postSvc.confirmPost(postingId)
      .then(res => {
        console.info(">>> submitted: ", res)
        this.router.navigate(['/success'], {state: {data: postingId}})
      }).catch(err => {
        console.error(">> error: ", err)
        alert("Postind ID " + postingId + " not found")
      })
  }

}
