import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'lgs-page-size',
  template: `<div ngClass="{{clicked ? 'dropdown show' : 'dropdown'}}">
    <button class="btn btn-outline-success btn-sm dropdown-toggle" (click)="buttonClick()">Елеметів на сторінці: {{size}}</button>
    <div ngClass="{{clicked ? 'dropdown-menu show' : 'dropdown-menu'}}" 
         style="position: absolute; transform: translate3d(0px, 27px, 0px); top: 0px; left: 0px; will-change: transform;">
      <a ngClass="{{size==item ? 'dropdown-item active' : 'dropdown-item'}}" href="#" 
         *ngFor="let item of sizes" (click)="elementClick(item)" >{{item}}</a>
    </div>
  </div>`
})
export class PageSizeComponent {
  @Input() sizes: number[];
  @Input() size: number;
  @Output() change = new EventEmitter<number>();
  clicked = false;
  buttonClick() {
    this.clicked = !this.clicked;
  }
  elementClick(item: number) {
    this.size = item;
    this.change.next(item);
    this.clicked = false;
    return false;
  }
}
