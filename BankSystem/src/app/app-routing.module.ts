import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from 'src/app/components/not-found-component/not-found-component.component';
import { AccountListComponent} from 'src/app/components/account-list/account-list.component';
import { SingleAccountComponent} from 'src/app/components/single-account/single-account.component';
import { AccountComponent } from 'src/app/components/account/account.component';
import { CreateAccountComponent} from 'src/app/components/create-account/create-account.component'
import { CreateTransactionComponent} from 'src/app/components/create-transaction/create-transaction.component'
const routes: Routes = [
{ 
	path: 'accounts', component: AccountListComponent, 
},
{ 
	path: 'accounts/:id', component: AccountComponent,
},
{ 
	path: 'accounts/:id/account-info', component: SingleAccountComponent,
},
{ 
	path: 'create-account', component: CreateAccountComponent,
},
{ 
	path: 'create-transaction', component: CreateTransactionComponent,
},
{
	path: '**', component: NotFoundComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
