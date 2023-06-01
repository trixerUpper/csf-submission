import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BACKEND_BASE_URL } from "environment-variables";
import { Observable } from "rxjs/internal/Observable";

export interface LeanOrder {
  orderId: string;
  date: string;
  total: number;
}

export interface AllLeanOrder {
  orders: LeanOrder[]
}

export interface OrderToSubmitPayload {
  name: string,
  email: string,
  size: number,
  base: string,
  sauce: string,
  toppings: string[],
  comments: string
}

export class PizzaService {

  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder(payload: OrderToSubmitPayload, httpClient: HttpClient) {
    const headers = new HttpHeaders()
    .set("Content-Type", "application/json")
    .set("Accept", "application/json");

    const requestOptions = { headers: headers };
    
    return httpClient.post(`${BACKEND_BASE_URL}/api/order`, JSON.stringify(payload), requestOptions)
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(thisEmail: string, httpClient: HttpClient): Observable<AllLeanOrder> {
    const url = `${BACKEND_BASE_URL}/api/orders/${thisEmail}`;
    return httpClient.get<AllLeanOrder>(url)
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered(orderId: string, httpClient: HttpClient) {
    const url = `${BACKEND_BASE_URL}/api/order/${orderId}`;
    return httpClient.delete(url)
  }

}
