import { Component, OnInit, OnChanges, Input } from '@angular/core';

import { TranslateService } from 'ng2-translate';

import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../app.component.css']
})
export class NavbarComponent implements OnInit, OnChanges {
  
  isWelcome: boolean = false
  url: string

  constructor(private translate: TranslateService, private router: Router) {}

  ngOnInit() {
    this.url = this.getPath()
    this.isWelcome = this.isWelcomePath()
  }

  ngOnChanges(){
    this.url = this.getPath()
    this.isWelcome = this.isWelcomePath()
  }

  changeLang(lang: string) {
    this.translate.use(lang);
  }

  isWelcomePath() : boolean {
    return this.url == "/welcome" ? true : false 
  }

  getPath(){
    return window.location.pathname
  }

}
