import {Injectable} from '@angular/core';
import {ReplaySubject} from 'rxjs/ReplaySubject';

@Injectable()
export class SmartService {
  private baseLinksSource = new ReplaySubject<any>(1);

  baseLinks = this.baseLinksSource.asObservable();

  refreshBaseLinks(links: any): void {
    const map = {};
    for (let i = 0; i < links.length; i++) {
      map[links[i].rel] = links[i].href;
    }
    this.baseLinksSource.next(map);
  }
}
