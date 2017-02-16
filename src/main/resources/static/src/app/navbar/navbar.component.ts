import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { TranslateService } from 'ng2-translate';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css', '../app.component.css']
})
export class NavbarComponent implements OnInit {
  isWelcome: boolean = false
  @Input() url: string
  constructor(private translate: TranslateService, private route: Router) { }

  ngOnInit() {
    console.log(this.route.url);
    if(this.url === '/welcome') this.isWelcome = true 
  }

  changeLang(lang: string) {
    this.translate.use(lang);
  }


}
