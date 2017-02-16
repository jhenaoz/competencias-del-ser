import { Injectable, Output, EventEmitter, OnChanges, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class SharedService implements OnChanges, OnInit {
  url: EventEmitter<string> = new EventEmitter()
  asdf: string
  constructor(private route: Router) { }

  ngOnInit(){
    this.asdf = this.route.url
  }
  ngOnChanges(){
    this.url.emit(this.route.url)
  }

}
