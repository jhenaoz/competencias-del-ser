import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { AdminService } from './admin.service';
import { Observable } from 'rxjs/Observable';
import { Http, Response, URLSearchParams, RequestOptions } from '@angular/http';


@Component({
    moduleId: module.id,
    selector: 'app-admin',
    templateUrl: 'admin.component.html',
    styleUrls: ['../app.component.css', './admin.component.css']
})

export class AdminComponent implements OnInit {
  adminForm: FormGroup;
    url: String;

 constructor( private adminService: AdminService) { }


    ngOnInit(): void {
      this.adminForm = new FormGroup({
        evaluated: new FormControl(),
        startDate: new FormControl(),
        endDate: new FormControl(),
        fileType: new FormControl('user')
      });
    }

    fileRequest() {

      this.adminService.fileRequest(this.adminForm.get('evaluated').value,
      this.adminForm.get('startDate').value,
      this.adminForm.get('endDate').value,
      this.adminForm.get('fileType').value);


    }

}
