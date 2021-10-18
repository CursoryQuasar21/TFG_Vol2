import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private accountService: AccountService, private router: Router) {
    //setInterval(this.prueba,1000);
    /*
    let numero=0;
     setInterval(function () {
       switch(numero){
          case 0:
            if(document.getElementsByClassName("homeTitulo")[0].getAttribute("padding-top")){
              document.getElementsByClassName("homeTitulo")[0].innerHTML="Hola";
              //numero++;
            }
            
            break;
          case 1:
            document.getElementsByClassName("homeTitulo")[0].innerHTML="Adios";
            numero++;
            break;
          case 2:
            document.getElementsByClassName("homeTitulo")[0].innerHTML="ADA";
            numero++;
            break;
          default:
            document.getElementsByClassName("homeTitulo")[0].innerHTML="XRP";
            numero=0;
            break;
       }       
     }, 6000);*/
  }

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
