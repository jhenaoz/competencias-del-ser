import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css', '../app.component.css']
})
export class FooterComponent implements OnInit {

  currentUrl
  constructor(private router:Router) { }

  ngOnInit() {
    this.currentUrl = this.router.url
    //console.log(this.currentUrl == '/welcome')
  }

  cancel(){
    if(window.location.pathname == '/survey-setup' || '/surveyteam-setup') this.router.navigate(['welcome'])
  }

}
