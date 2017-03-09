/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';

import {
    BaseRequestOptions,
    Http,
    Response,
    ResponseOptions,
    XHRBackend
} from '@angular/http';

// Imports that allow us to fake the 'server' and intercept the calls to that server.
import {
    MockBackend,
    MockConnection
} from '@angular/http/testing';

import { Observable } from 'rxjs/Rx';
import { environment } from '../../environments/environment';

import {
  EmployeeService,
  EmployeeComponent,
  IEmployee
 } from './index';



describe('Service: EmployeeService', () => {
  let backend: MockBackend;
  let service: EmployeeService;
  const _employeeUrl = environment.apiURL += '/person';

  beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                BaseRequestOptions,
                MockBackend,
                EmployeeService,
                {
                    deps: [
                        MockBackend,
                        BaseRequestOptions
                    ],
                    provide: Http,
                    useFactory: (backend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backend, defaultOptions);
                    }
                }
            ]
        });
        backend = TestBed.get(MockBackend);
        service = TestBed.get(EmployeeService);
    }));

  function setupConnections(backend: MockBackend, options: any) {
      backend.connections.subscribe((connection: MockConnection) => {
          if (connection.request.url === 'api/person') {
              const responseOptions = new ResponseOptions(options);
              const response = new Response(responseOptions);

              connection.mockRespond(response);
          }
      });
  }

  describe('getEmployees:', () => {
    it('should return the list of employees from the server on success', () => {
        setupConnections(backend, {
            body: [
                {
                    id: 1,
                    name: 'Alejandra Rodas'
                },
                {
                    id: 4,
                    name: 'Juan Henao'
                },
                {
                    id: 2,
                    name: 'German Potes'
                }
            ],
            status: 200
        });

        service.getEmployees().subscribe((data: IEmployee[]) => {
            expect(data.length).toBe(3);
            expect(data[0].name).toBe('Alejandra Rodas');
            expect(data[1].name).toBe('Juan Henao');
            expect(data[2].name).toBe('German Potes');
        });
    });

    it('should log an error to the console on error', () => {
      setupConnections(backend, {
          body: { error: `We got an error on the method getEmployees(), that's what we expected` },
          status: 500
      });
      spyOn(console, 'error');

      service.getEmployee(null).subscribe(null, () => {
          expect(console.error).toHaveBeenCalledWith(`We got an error on the method getEmployees(), that's what we expected`);
      });
    });
  });

  describe('getEmployee(id):', () => {
    it('should return one employee from the server on success', () => {
      setupConnections(backend, {
            body: [
                {
                    id: 4,
                    name: 'Juan Henao'
                }
            ],
            status: 200
      });
      service.getEmployee(4).subscribe((data: IEmployee) => {
        expect(data.employeeId).toBe(4);
        expect(data.name).toBe('Juan Henao');
      });
    });

    it('should log an error to the console on error', () => {
        setupConnections(backend, {
            body: { error: `We got an error on the method getEmployee(), that's what we expected` },
            status: 500
        });
        spyOn(console, 'error');

        service.getEmployees().subscribe(null, () => {
            expect(console.error).toHaveBeenCalledWith(`We got an error on the method getEmployee(), that's what we expected`);
        });
    });
  });
});
