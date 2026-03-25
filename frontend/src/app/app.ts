import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './main/components/navbar/navbar';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'wsw-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private titleService: Title = inject(Title);

  ngOnInit(): void {
    this.titleService.setTitle("Whist: gestionnaire de parties");
  }
}
