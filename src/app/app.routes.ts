import { Routes } from '@angular/router';
import { Home } from './home/home';
import { AnexarReceita } from './anexar-receita/anexar-receita';
import { Login } from './login/login';
import { RecuperarSenha } from './recuperar-senha/recuperar-senha';
import { RegisterComponent } from './registrar/registrar';
import { InfoProduto } from './produto-detalhe/produto-detalhe';
import { VendaComponent } from './venda/venda';
import { EspessuraLente } from './espessura-lente/espessura-lente';
import { TipoLente } from './tipo-lente/tipo-lente';
import { Carrinho } from './carrinho/carrinho';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'anexar-receita', component: AnexarReceita },
    { path: 'login', component: Login },
    { path: 'recuperar-senha', component: RecuperarSenha },
    { path: 'registrar', component: RegisterComponent },
    { path: 'produto/:id', component: InfoProduto },
    { path: 'venda', component: VendaComponent },
    { path: 'espessura-lente/:id', component: EspessuraLente },
    { path: 'tipo-lente/:id', component: TipoLente },
    { path: 'anexar-receita/:id', component: AnexarReceita },
    { path: 'carrinho', component: Carrinho },
];
