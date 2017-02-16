import { Component, OnInit, OnChanges } from '@angular/core';

import { TranslateService } from 'ng2-translate';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../app.component.css']
})
export class NavbarComponent implements OnInit, OnChanges {
  isWelcome: boolean = false
  url: string
  welcome = '/welcome'
  constructor(private translate: TranslateService) { }

  ngOnInit() {
    this.url = this.url = window.location.pathname
    if(this.url === '/welcome') this.isWelcome = true 
  }

  ngOnChanges(){
    this.url = window.location.pathname
    if(this.url === '/welcome') this.isWelcome = true
  }

  changeLang(lang: string) {
    this.translate.use(lang);
  }


}
