import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'lgs-pageable',
  template: `<ul class="pagination justify-content-center" *ngIf="totalPages">
    <li ngClass="{{page==1 ? 'page-item disabled' : 'page-item'}}" (click)="onClick(1)">
      <a class="page-link" href="#"><<</a>
    </li>
    <li ngClass="{{page==1 ? 'page-item disabled' : 'page-item'}}" (click)="onClick(page-1)">
      <a class="page-link" href="#"><</a></li>
    <li ngClass="{{item==page ? 'page-item active' : 'page-item'}}" *ngFor="let item of pages" (click)="onClick(item)">
      <a class="page-link" href="#">{{item}}</a>
    </li>
    <li ngClass="{{page==totalPages ? 'page-item disabled' : 'page-item'}}" (click)="onClick(page+1)">
      <a class="page-link" href="#">></a>
    </li>
    <li ngClass="{{page==totalPages ? 'page-item disabled' : 'page-item'}}"  (click)="onClick(totalPages)">
      <a class="page-link" href="#">>></a>
    </li>
  </ul>`
})
export class PageableComponent implements OnChanges {
  @Input() page: number;
  @Input() size: number;
  @Input() totalPages: number;
  @Input() visiblePages = 5;
  @Output() change = new EventEmitter<number>();
  pages: number[];
  ngOnChanges(changes: SimpleChanges): void {
    if (this.totalPages) {
      this.addPages();
    }
  }
  addPages() {
    this.pages = new Array<number>();
    let start = 1;
    let end = this.totalPages;
    if (end - start > this.visiblePages - start) {
      if (this.page + Math.floor(this.visiblePages / 2) <= end && this.page - Math.floor(this.visiblePages / 2) >= start) {
        start = this.page - Math.floor(this.visiblePages / 2);
        end = this.page + Math.floor(this.visiblePages / 2);
      } else if (this.page + Math.floor(this.visiblePages / 2) <= end) {
        end = start + this.visiblePages - 1;
      } else if (this.page - Math.floor(this.visiblePages / 2) >= start) {
        start = end - (this.visiblePages - 1);
      }
    }
    for (let i = start; i <= end; i++) {
      this.pages.push(i);
    }
  }
  onClick(page: number) {
    if (page > 0 && page <= this.totalPages) {
      this.page = page;
      this.change.next(this.page);
    }
    return false;
  }
}
