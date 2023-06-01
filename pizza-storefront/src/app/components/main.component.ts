import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  
  myForm: FormGroup | null = null;
  toppings: FormGroup | null = null;
  pizzaSize = SIZES[0]

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.toppings = this.formBuilder.group({
      chicken: this.formBuilder.control(false),
      seafood: this.formBuilder.control(false),
      beef: this.formBuilder.control(false),
      vegetables: this.formBuilder.control(false),
      cheese: this.formBuilder.control(false),
      arugula: this.formBuilder.control(false),
      pineapple: this.formBuilder.control(false),
    });

    this.myForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      size: ['', Validators.required],
      base: ['', Validators.required],
      sauce: ['', Validators.required],
      toppings: this.toppings,
      comments: ['']
    });
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

}
