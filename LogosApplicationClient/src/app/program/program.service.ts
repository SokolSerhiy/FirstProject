import {Injectable} from '@angular/core';
import {AuthHttp} from '../global-service/http';
import {SmartService} from '../global-service/smart.service';
import {Subscription} from 'rxjs/Subscription';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Program} from './program';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/takeUntil';
import {RequestOptions, Headers, Response} from '@angular/http';

@Injectable()
export class ProgramService {
  private programUrl;
  private subscriptions: Array<Subscription> = [];
  private observable = new ReplaySubject<Program[]>(1);
  private resource: Program[];
  constructor(private http: AuthHttp, private smart: SmartService) {
    this.observable.asObservable();
  }
  start() {
    this.subscriptions.push(this.smart.baseLinks.subscribe(
      urls => {
        this.programUrl = urls['program'];
        this.http.get(this.programUrl)
          .subscribe(value => {
            this.toProgramArray(value.json());
            this.observable.next(this.resource);
          });
        this.createRefreshRequest();
      }
    ));
  }
  getResource(): Observable<Program[]> {
    return this.observable;
  }
  stop() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
  save(form: any) {
    this.http.post(this.programUrl, form)
      .map(data => this.toProgram(data.json()))
      .subscribe(
        (program) => this.addToResource(program),
        (err) => console.log(err)
      );
  }
  update (form: any, selfUrl: string) {
    this.http.put(selfUrl, form)
      .map(data => this.toProgram(data.json()))
      .subscribe(
        (program) => this.updateResource(program),
        (err) => console.log(err)
      );
  }
  delete(program: Program) {
    this.http.delete(program.self).subscribe(
      () => this.deleteFromResource(program.self), (err) => console.log(err)
    );
  }
  private createRefreshRequest() {
    const options = new RequestOptions();
    options.headers = new Headers();
    options.headers.append('X-Refresh', '*');
    this.subscriptions.push(this.http.get(this.programUrl, options)
      .subscribe((response) => this.refresh(response), (err) => console.log(err)));
  }
  private refresh(res: Response) {
    if (res.status !== 204) {
      const status = res.headers.get('Entity-Status');
      switch (status) {
        case 'CREATED': {
          this.addToResource(this.toProgram(res.json()));
          break;
        }
        case 'UPDATED': {
          this.updateResource(this.toProgram(res.json()));
          break;
        }
        case 'DELETED': {
          this.deleteFromResource(this.toProgram(res.json()).self);
          break;
        }
      }
    }
    this.createRefreshRequest();
  }
  private addToResource(program: Program) {
    this.resource.push(program);
    this.observable.next(this.resource);
  }
  private updateResource(program: Program) {
    const index = this.resource.findIndex(element => element.self === program.self);
    this.resource[index].name = program.name;
    this.observable.next(this.resource);
  }
  private deleteFromResource(self: string) {
    const index = this.resource.findIndex(element => element.self === self);
    this.resource.splice(index, 1);
    this.observable.next(this.resource);
  }
  private toProgram(json: any): Program {
    const program = new Program();
    program.name = json.name;
    for (let j = 0; j < json.links.length; j++) {
      if (json.links[j].rel === 'self') {
        program.self = json.links[j].href;
      }
    }
    return program;
  }
  private toProgramArray(json: any): void {
    const array = new Array<Program>();
    for (let i = 0; i < json.length; i++) {
      array.push(this.toProgram(json[i]));
    }
    this.resource = array;
  }
}
