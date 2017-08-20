import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ImageData} from './image.data';

@Component({
  moduleId: module.id,
  selector: 'lgs-upload-img',
  templateUrl: './upload.img.component.html',
  styleUrls: ['./upload.img.component.css']
})
export class UploadImgComponent implements OnInit {
  public invalid = true;
  public imgUrl = '';
  public maskWidth = 300;
  public maskHeight = 400;
  public top = 0;
  public left = 15;
  private startX = 0;
  private startY = 0;
  private naturalWidth: number;
  private naturalHeight: number;
  private visibleWidth: number;
  private visibleHeight: number;
  private isMoving = false;
  private addTimeout: any;
  private timeout = 1000;
  @Output()
  public send: EventEmitter<ImageData> = new EventEmitter<ImageData>();
  ngOnInit(): void {
    window.addEventListener('resize', () => this.resize());
  }
  public readUrl(event) {
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.onload = (ev: any) => this.onLoadReader(ev);
      reader.readAsDataURL(event.target.files[0]);
    }
  }
  public onMouseDown(event) {
    this.start(event.clientX, event.clientY);
  }
  public onMouseUp() {
    this.stop();
  }
  public onMove(event) {
    this.move(event.clientX, event.clientY);
  }
  public addSize() {
    if (this.maskWidth !== this.visibleWidth || this.maskHeight !== this.maskHeight) {
      if (this.widthCanBeIncreased() && this.heightCanBeIncreased()) {
        this.increaseBoth();
      } else if (this.widthCanBeIncreased()) {
        this.increaseHeightToMax();
        this.increaseWidthProportional();
      } else if (this.heightCanBeIncreased()) {
        this.increaseWidthToMax();
        this.increaseHeightProportional();
      }
      this.validateRight();
      this.validateBottom();
    }
    if (this.timeout > 30) {
      this.timeout = this.timeout / 2;
    }
    this.addTimeout = setTimeout(() => this.addSize(), this.timeout);
  }
  public stopChangeSize() {
    clearTimeout(this.addTimeout);
    this.timeout = 1000;
  }
  public subtractSize() {
    this.maskWidth -= 9 * this.visibleWidth / this.naturalWidth;
    this.maskHeight -= 12 * this.visibleHeight / this.naturalHeight;
    if (this.timeout > 20) {
      this.timeout = this.timeout / 2;
    }
    this.addTimeout = setTimeout(() => this.subtractSize(), this.timeout);
  }
  public submit() {
    const data = new ImageData();
    data.img = this.imgUrl;
    data.maskWidth = this.maskWidth;
    data.maskHeight = this.maskHeight;
    data.maskLeft = this.left - 15;
    data.maskTop = this.top;
    data.imgHeight = this.visibleHeight;
    data.imgWidth = this.visibleWidth;
    this.imgUrl = '';
    this.invalid = true;
    this.send.emit(data);
  }
  public cancel() {
    this.imgUrl = '';
    this.invalid = true;
  }
  private validateRight() {
    if (this.left + this.maskWidth - 15 > this.visibleWidth) {
      this.left = this.visibleWidth - this.maskWidth + 15;
    }
  }
  private validateBottom() {
    if (this.top + this.maskHeight > this.visibleHeight) {
      this.top = this.visibleHeight - this.maskHeight;
    }
  }
  private onLoadReader(ev: any) {
    this.imgUrl = ev.target.result;
    const img = new Image();
    img.src = this.imgUrl;
    img.onload =  () => this.onLoadImage(img);
  }
  private onLoadImage(img: any) {
    this.setImageSize(img);
    if (this.isValidSize()) {
      this.invalid = false;
      const visibleImg = document.getElementById('img');
      this.setVisibleSize(visibleImg);
      this.setMaskSize(visibleImg);
      this.top = 0;
      this.left = 15;
    } else {
      this.invalid = true;
      this.imgUrl = '';
    }
  }
  private setMaskSize(visibleImg: HTMLElement) {
    this.maskWidth = visibleImg.offsetWidth / this.naturalWidth * 300;
    this.maskHeight = visibleImg.offsetHeight / this.naturalHeight * 400;
  }
  private setVisibleSize(visibleImg: HTMLElement) {
    this.visibleWidth = visibleImg.offsetWidth;
    this.visibleHeight = visibleImg.offsetHeight;
  }
  private setImageSize(img: any) {
    this.naturalWidth = img.naturalWidth;
    this.naturalHeight = img.naturalHeight;
  }
  private isValidSize(): boolean {
    return this.naturalWidth >= 300 && this.naturalHeight >= 400;
  }
  private resize() {
    if (!this.invalid) {
      const visibleImg = document.getElementById('img');
      const wight = this.visibleWidth;
      const height = this.visibleHeight;
      this.setVisibleSize(visibleImg);
      this.setMaskSize(visibleImg);
      this.left = (this.left - 15) * this.visibleWidth / wight + 15;
      this.top = this.top * this.visibleHeight / height;
    }
  }
  private start(clientX: number, clientY: number) {
    this.isMoving = true;
    this.startX = clientX - this.left;
    this.startY = clientY - this.top;
  }
  private stop() {
    this.isMoving = false;
  }
  private move(clientX: number, clientY: number) {
    if (this.isMoving) {
      this.setLeft(clientX - this.startX + 15);
      this.setTop(clientY - this.startY);
    }
  }
  private increaseHeightProportional() {
    this.maskHeight = 4 / 3 * this.maskWidth;
  }
  private increaseWidthProportional() {
    this.maskWidth = 3 / 4 * this.maskHeight;
  }
  private increaseHeightToMax() {
    this.maskHeight = this.visibleHeight;
  }
  private increaseWidthToMax() {
    this.maskWidth = this.visibleWidth;
  }
  private increaseBoth() {
    this.maskWidth += 9 * this.visibleWidth / this.naturalWidth;
    this.maskHeight += 12 * this.visibleHeight / this.naturalHeight;
  }
  private widthCanBeIncreased(): boolean {
    return this.maskWidth + 9 * this.visibleWidth / this.naturalWidth <= this.visibleWidth;
  }
  private heightCanBeIncreased(): boolean {
    return this.maskHeight + 9 * this.visibleHeight / this.naturalHeight <= this.visibleHeight;
  }
  private setLeft(left: number) {
    left = this.setDefaultLeft(left);
    left = this.setDefaultRight(left);
    this.left = left;
  }
  private setTop(top: number) {
    top = this.setDefaultTop(top);
    top = this.setDefaultBottom(top);
    this.top = top;
  }
  private setDefaultLeft(left: number): number {
    if (left < 15) {
      return left = 15;
    }
    return left;
  }
  private setDefaultRight(left: number): number {
    if (left + this.maskWidth > this.visibleWidth + 15) {
      return this.visibleWidth + 15 - this.maskWidth;
    }
    return left;
  }
  private setDefaultTop(top: number): number {
    if (top < 0) {
      return 0;
    }
    return top;
  }
  private setDefaultBottom(top: number): number {
    if (top + this.maskHeight > this.visibleHeight) {
      return this.visibleHeight - this.maskHeight;
    }
    return top;
  }
}
