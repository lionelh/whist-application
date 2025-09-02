import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatabaseCounters } from './database-counters';

describe('DatabaseCounters', () => {
  let component: DatabaseCounters;
  let fixture: ComponentFixture<DatabaseCounters>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DatabaseCounters]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DatabaseCounters);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
