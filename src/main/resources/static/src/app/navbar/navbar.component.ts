import { Component, OnInit, Input } from '@angular/core';

import { TranslateService } from 'ng2-translate';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../app.component.css']
})
export class NavbarComponent implements OnInit  {
  

  constructor(private translate: TranslateService) {}

  ngOnInit() {
  }

  changeLang(lang: string) {
    this.translate.use(lang);
  }

}
