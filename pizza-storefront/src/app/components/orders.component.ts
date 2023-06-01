import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BACKEND_BASE_URL } from 'environment-variables';

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
  allLeanOrders: AllLeanOrder | null = null;
  httpClient: HttpClient;
  thisEmail!: string | null;

  constructor(activatedRoute: ActivatedRoute, httpClient: HttpClient) {
    this.httpClient = httpClient;
    this.activatedRoute = activatedRoute;
  }

  ngOnInit(): void {
    this.thisEmail = this.activatedRoute.snapshot.paramMap.get('email');

    this.httpClient.get<LeanOrder[]>(`${BACKEND_BASE_URL}/api/orders/${this.thisEmail}`).subscribe(
      (response) => {
        alert(`Query to orders for ${this.thisEmail} succeeded with response: ${JSON.stringify(response)}`)
        let output: LeanOrder[] = response as LeanOrder[];
        this.allLeanOrders = {
          "orders": output
        };
      },
      (error) => {
        alert(`Query to orders for ${this.thisEmail} failed with error: ${error}`);
      }
    );
  }
}
