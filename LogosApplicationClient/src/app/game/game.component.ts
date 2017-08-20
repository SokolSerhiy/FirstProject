import {Component, OnDestroy, OnInit} from '@angular/core';
import {GameService} from './game.service';
import {GameResource} from './game-resource';

@Component({
  moduleId: module.id,
  selector: 'lgs-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
  providers: [GameService]
})
export class GameComponent implements OnInit , OnDestroy {
  resource: GameResource = new GameResource();
  constructor(private service: GameService) {}
  ngOnInit(): void {
    this.service.getGameResource().subscribe(data => this.resource = data ? data : this.resource);
    this.service.start();
  }
  ngOnDestroy(): void {
    this.service.stop();
  }
}
