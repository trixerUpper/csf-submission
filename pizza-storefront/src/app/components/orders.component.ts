import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BACKEND_BASE_URL } from 'environment-variables';
import { PizzaService } from '../pizza.service';

interface LeanOrder {
  orderId: string;
  date: string;
  total: number;
}

interface AllLeanOrder {
  orders: LeanOrder[]
}

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit{
  activatedRoute: ActivatedRoute;
  pizzaService: PizzaService;
  allLeanOrders: AllLeanOrder | null = null;
  httpClient: HttpClient;
  thisEmail!: string | null;

  constructor(activatedRoute: ActivatedRoute, httpClient: HttpClient, pizzaService: PizzaService) {
    this.httpClient = httpClient;
    this.activatedRoute = activatedRoute;
    this.pizzaService = pizzaService;
  }

  markOrderDelivered(orderId: string): void {
    this.pizzaService.delivered(orderId, this.httpClient).subscribe(
      (response) => {
        alert('Order marked as delivered');
        this.fetchOrders();
      },
      (error) => {
        alert(`Failed to mark order as delivered: ${error}`);
      }
    );
  }

  fetchOrders(): void {
    if (this.thisEmail) {
      this.pizzaService.getOrders(this.thisEmail, this.httpClient).subscribe(
        (response) => {
          console.log(`Query to orders for ${this.thisEmail} succeeded with response: ${JSON.stringify(response)}`);
          let allOrders: LeanOrder[] = response as LeanOrder[];
          this.allLeanOrders = {
            "orders": allOrders
          };
        },
        (error) => {
          alert(`Query to orders for ${this.thisEmail} failed with error: ${error}`);
        }
      );
    }
  }

  ngOnInit(): void {
    this.thisEmail = this.activatedRoute.snapshot.paramMap.get('email');
    this.fetchOrders();
  }
}