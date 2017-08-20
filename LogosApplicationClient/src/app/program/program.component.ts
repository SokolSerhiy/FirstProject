import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProgramService} from './program.service';
import {Program} from './program';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ProgramSearchPipe} from './program.search.pipe';

@Component({
  moduleId: module.id,
  selector: 'lgs-program',
  templateUrl: './program.component.html',
  styleUrls: ['./program.component.css'],
  providers: [ProgramService]
})
export class ProgramComponent implements OnInit, OnDestroy {
  public programForm: FormGroup;
  public searchControl = new FormControl('');
  public search = '';
  public totalPages = 0;
  public page = 1;
  public size = 10;
  private data: Program[];
  private currentProgramUrl: string;
  constructor(private service: ProgramService, private fb: FormBuilder, private filter: ProgramSearchPipe) {}
  ngOnInit(): void {
    this.buildForm();
    this.service.getResource().subscribe(res => {
      this.data = res;
      this.setTotalPages();
    });
    this.searchControl.valueChanges.subscribe(search => {
      this.search = search;
      this.setTotalPages();
    });
    this.service.start();
  }
  ngOnDestroy(): void {
    this.service.stop();
  }
  delete(program: Program) {
    this.service.delete(program);
  }
  onSubmit(programForm: FormGroup) {
    if (this.currentProgramUrl) {
      this.service.update(programForm.value, this.currentProgramUrl);
    } else {
      this.service.save(programForm.value);
    }
    this.buildForm();
    this.currentProgramUrl = '';
  }
  update(program: Program) {
    this.currentProgramUrl = program.self;
    this.programForm = this.fb.group({
      name: [program.name, Validators.required]
    });
  }
  changePage(page: number) {
    this.page = page;
  }
  changeSize(size: number) {
    this.size = size;
    this.setTotalPages();
  }
  private setTotalPages() {
    this.totalPages = Math.ceil(this.filter.transform(this.data, this.search).length / this.size);
    this.checkPage();
  }
  private checkPage() {
    if (this.totalPages < this.page) {
      this.page = this.totalPages;
    }
  }
  private buildForm() {
    this.programForm = this.fb.group({
      name: ['', Validators.required]
    });
  }
}
