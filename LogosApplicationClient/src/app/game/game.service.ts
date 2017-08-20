import {Injectable} from '@angular/core';
import {AuthHttp} from '../global-service/http';
import {SmartService} from '../global-service/smart.service';
import {GameResource} from './game-resource';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subscription} from 'rxjs/Subscription';

@Injectable()
export class GameService {
  private gameUrl: string;
  private refreshUrl: string;
  private repeatRefresh = true;
  private resource = new ReplaySubject<GameResource>(1);
  private subscriptions: Array<Subscription> = [];
  constructor(private http: AuthHttp, private smart: SmartService) {
  }
  public start() {
    this.repeatRefresh = true;
    this.subscriptions.push(this.smart.baseLinks.subscribe(map => {
      this.gameUrl = map['game'];
      this.loadGameResource(this.gameUrl);
    }));
  }
  public stop() {
    this.repeatRefresh = false;
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    });
  }
  public getGameResource(): Observable<GameResource> {
    return this.resource;
  }
  private loadGameResource(url: string): void {
    this.subscriptions.push(this.http.get(url)
      .map(res => this.toGameResource(res.json()))
      .subscribe(data => this.onSuccess(data), (err) => console.log(err)));
  }
  private onSuccess(data) {
    this.resource.next(data);
    this.refresh();
  }
  private refresh() {
    if (this.repeatRefresh) {
      this.loadGameResource(this.refreshUrl);
    }
  }
  private toGameResource(json: any): GameResource {
    if (json !== 'NO_CONTENT') {
      this.getRefreshUrl(json.links);
      const resource = new GameResource();
      resource.food = json.food;
      resource.wood = json.wood;
      resource.stone = json.stone;
      resource.iron = json.iron;
      resource.woodCollectors = json.woodCollectors;
      resource.foodCollectors = json.foodCollectors;
      resource.stoneCollectors = json.stoneCollectors;
      resource.ironCollectors = json.ironCollectors;
      return resource;
    }
  }
  private getRefreshUrl(links: any) {
    for (let i = 0; i < links.length; i++) {
      if (links[i].rel === 'refresh') {
        this.refreshUrl = links[i].href;
      }
    }
  }
}
