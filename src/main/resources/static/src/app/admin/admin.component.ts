import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

import { environment } from '../../environments/environment';

@Component({
  moduleId: module.id,
  selector: 'app-admin',
  templateUrl: 'admin.component.html',
  styleUrls: ['../app.component.css', './admin.component.css']
})

export class AdminComponent implements OnInit {
  adminForm: FormGroup;
  url: string = environment.apiURL;
  private dataTypeUrl: string;

  constructor() { }


  ngOnInit(): void {
    this.adminForm = new FormGroup({
      evaluated: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      fileType: new FormControl('user')
    });
  }

  fileRequest() {
    this.url += '/api/survey/report/';
    this.url += this.adminForm.get('fileType').value;

    if (this.adminForm.get('evaluated').value !== '' || this.adminForm.get('startDate').value !== ''
      || this.adminForm.get('endDate').value !== '') {
      this.url += '?';


      if (this.adminForm.get('evaluated').value !== null) {
        this.url += '&name=';
        this.url += this.adminForm.get('evaluated').value;
      }

      if (this.adminForm.get('startDate').value !== null && this.adminForm.get('startDate').value !== '') {
        this.url += '&startdate=';
        this.url += this.adminForm.get('startDate').value;
      }

      if (this.adminForm.get('endDate').value !== null  && this.adminForm.get('endDate').value !== '') {
        this.url += '&enddate=';
        this.url += this.adminForm.get('endDate').value;
      }
    }

    window.location.href = this.url;

    this.url = environment.apiURL;

  }

}
