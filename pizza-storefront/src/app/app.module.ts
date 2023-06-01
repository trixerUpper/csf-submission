import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';
import { OrdersComponent } from './components/orders.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { PizzaService } from './pizza.service';

const routes: Routes = [
  {path:"", component: MainComponent},
  {path:"orders/:email", component: OrdersComponent},
  {path:"**", redirectTo: "/", pathMatch: "full"}
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    OrdersComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],

  providers: [PizzaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
