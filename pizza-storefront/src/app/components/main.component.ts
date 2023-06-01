import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PIZZA_TOPPINGS: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

interface OrderPayload {
  orderId: string,
  date: string,
  name: string,
  email: string,
  total: number
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  myForm!: FormGroup;
  toppings!: FormGroup;
  pizzaSize = SIZES[0]

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router) {
  }

  
  private createForm(){
    this.myForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      size: this.pizzaSize,
      base: ['', Validators.required],
      sauce: ['', Validators.required],
      toppings: this.formBuilder.group({
        chicken: [false],
        seafood: [false],
        beef: [false],
        vegetables: [false],
        cheese: [false],
        arugula: [false],
        pineapple: [false]
      }),
      comments: ['']
    });
  }

  ngOnInit(): void {
    this.createForm();
  }

  submitForm() {
    if (this.myForm.valid) {
      const formData = new FormData();
      const selectedToppings = Object.keys(this.myForm.value.toppings)
        .filter((topping) => this.myForm.value.toppings[topping])
        .join(',');
      
      let pizzaSizeInt = 6;
      if (this.pizzaSize === SIZES[0]) {
        pizzaSizeInt = 6;
      } else if (this.pizzaSize === SIZES[1]) {
        pizzaSizeInt = 9;
      } else if (this.pizzaSize === SIZES[2]) {
        pizzaSizeInt = 12;
      } else {
        pizzaSizeInt = 15;
      }

      const payload = {
        name: this.myForm.value.name,
        email: this.myForm.value.email,
        size: pizzaSizeInt,
        base: this.myForm.value.base,
        sauce: this.myForm.value.sauce,
        toppings: Object.keys(this.myForm.value.toppings)
          .filter((topping) => this.myForm.value.toppings[topping]),
        comments: this.myForm.value.comments
      };

      console.log(payload);

      const headers = new HttpHeaders()
      .set("Content-Type", "application/json")
      .set("Accept", "application/json");

      const requestOptions = { headers: headers };
      
      this.http.post(`http://localhost:8080`,JSON.stringify(payload), requestOptions).subscribe(
        response => {
          console.log("Form submitted");
          console.log(response);
          let responseObject: OrderPayload = response as OrderPayload;
          console.log(`Order ${responseObject.orderId} submitted successfully!`);
          this.router.navigate(['/orders', responseObject.email]);
        },
        error => {
          alert(error)
        }
      )
    }
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

}
