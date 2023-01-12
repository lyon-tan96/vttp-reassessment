import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostDetails } from '../models';
import { PostService } from '../post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  form!: FormGroup

  @ViewChild('toUpload')
  toUpload!: ElementRef

  constructor(private fb: FormBuilder, private postSvc: PostService, private router: Router) { }

  ngOnInit(): void {
    this.form = this.createForm()
  }

  postListing(){
    const name = this.form.get('name')?.value
    const email = this.form.get('email')?.value
    let phone = this.form.get('phone')?.value
    const title = this.form.get('title')?.value
    const description = this.form.get('description')?.value
    const file = this.toUpload.nativeElement.files[0]

    if (phone == '') {
      phone = '-'
    }

    this.postSvc.createPost(file, name, email, phone, title, description)
      .then(res => {
        console.info("posted: ", res)
        this.router.navigate(['/summary'], {state: {data: res}})
      }).catch(err => {
        console.error(">> error: ", err)
      })
  }

  clearForm() {
    this.form.reset()
  }

  createForm(){
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      phone: this.fb.control<string>(''),
      title: this.fb.control<string>('', [Validators.required]),
      description: this.fb.control<string>('', [Validators.required]),
      image: this.fb.control<any>('', [Validators.required])

    })
  }
}
