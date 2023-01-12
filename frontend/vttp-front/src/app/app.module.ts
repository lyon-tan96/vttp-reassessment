import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { PostService } from './post.service';
import { SummaryComponent } from './components/summary.component';
import { SuccessComponent } from './components/success.component';

const appRoutes: Routes = [
  { path: '', component: MainComponent},
  { path: 'summary', component: SummaryComponent },
  { path: 'success', component: SuccessComponent},
  { path: '**', component: MainComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SummaryComponent,
    SuccessComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [PostService],
  bootstrap: [AppComponent]
})
export class AppModule { }
