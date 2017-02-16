import { Injectable, Output, EventEmitter, OnChanges, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class SharedService implements  OnInit {
 
  url: string
  constructor(private route: Router) { }

  ngOnInit(){
    
  }
  
  getUrl(){
    return this.route.url
  }

}
