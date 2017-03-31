import { Component, OnInit, Input } from '@angular/core';

import { TranslateService } from 'ng2-translate';

import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../app.component.css']
})
export class NavbarComponent implements OnInit  {

  currentUrl;
  constructor(private translate: TranslateService, private router: Router) {}

  ngOnInit() {
    this.currentUrl = this.router.url;
  }

/*
* Method to change current language
*/
  changeLang(lang: string) {
    this.translate.use(lang);
  }

/*
* Method to verify the current ulr to activate/deactivate the changeLang button
*/
  disableChangeLanguage() {
    return (this.currentUrl === '/welcome' || this.currentUrl === '/admin');
  }

}
