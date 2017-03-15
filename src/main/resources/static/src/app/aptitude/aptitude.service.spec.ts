/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';

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

import { environment } from '../../environments/environment';

import { AptitudeService } from './aptitude.service';
import { Behavior } from './behavior.model';

describe('Service: AptitudeService', () => {
  let backend: MockBackend;
  let service: AptitudeService;
  const _behaviorUrl = environment.apiURL += '/aptitude/';

  beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                BaseRequestOptions,
                MockBackend,
                AptitudeService,
                {
                    deps: [
                        MockBackend,
                        BaseRequestOptions
                    ],
                    provide: Http,
                    useFactory: (backendXHRB: XHRBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backendXHRB, defaultOptions);
                    }
                }
            ]
        });
        backend = TestBed.get(MockBackend);
        service = TestBed.get(AptitudeService);
    }));

  function setupConnections(backendMock: MockBackend, options: any) {
      backendMock.connections.subscribe((connection: MockConnection) => {
          if (connection.request.url === _behaviorUrl) {
              const responseOptions = new ResponseOptions(options);
              const response = new Response(responseOptions);

              connection.mockRespond(response);
          }
      });
  }

  describe('getBehaviors(id):', () => {
    let test: Behavior[];
    it('should return the list of behaviors given the aptitude Id from the server on success', () => {
        setupConnections(backend, {
            body: [
                {
                    id: 1,
                    en: 'They accept suggestions',
                    es: 'Acepta sugerencias'
                },
                {
                    id: 4,
                    en: 'They recognize their mistakes',
                    es: 'Reconoce sus errores'
                },
                {
                    id: 2,
                    en: 'They ask for suggestions',
                    es: 'Solicita sugerencias'
                }
            ],
            status: 200
        });
        /*service.getBehaviors('3').toPromise().then( (result) => {
            expect(result.length).toBe(4);
            expect(true).toBeFalsy();
        });*/
        service.getBehaviors('3').subscribe(res => {
          test = res;
            expect(res.length).toBe(4);
            expect(res[0].en).toBe('They accept suggestions');
            expect(res[1].es).toBe('Reconoce sus errores');
            expect(res[2].en).toBe('They ask for suggestions');
            expect(true).toBeFalsy();
        });
        // expect(test.length).toBe(4);
    });

    it('should log an error to the console on error', () => {
      setupConnections(backend, {
          body: { error: `We got an error on the method getBehaviors(), that's what we expected` },
          status: 500
      });
      spyOn(console, 'error');
      service.getBehaviors(null).subscribe(null, () => {
        expect(true).toBeFalsy();
          expect(console.error)
            .toHaveBeenCalledWith(`We got an error on the method getBehaviors(), that's what we expected`);
      });
    });
  });
});
