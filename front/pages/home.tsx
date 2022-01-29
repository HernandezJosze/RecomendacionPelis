import type { NextPage } from 'next'
import Head from 'next/head'
import { useRouter } from 'next/router'

import { useEffect, useState } from 'react'
import { Container} from 'react-bootstrap'
import NavigationBar from '../components/NavigationBar'
import FormaUsuario from '../models/formausuario'
import TransactionService from '../services/transaction'


const Home: NextPage = () => {

  const router = useRouter()
  const [alias, setAlias] = useState<string>("");
  const [usuario, setUsuario] = useState<FormaUsuario>();

  useEffect(() => {
    let token : string = sessionStorage.getItem("token") || "";
    console.log("token: ", token)
		let nav : HTMLElement|null= document.querySelector("#topnav");
		if (!!nav) {
			nav.hidden = false;	
		}
    TransactionService.checkToken(token).then((res) => {
      setAlias(res.data);
    }).catch( () => { // token erroneo, acceso ilegal, regresarse a la pantalla de inicio de sesión
      router.push('/');
    });
    TransactionService.getUsuario(token).then((res) => {
      setUsuario(res.data);
      console.log("usuario:",res.data);
    }).catch( (error) => { // token erroneo, acceso ilegal, regresarse a la pantalla de inicio de sesión
      console.log("error:",error);
    });
  }, []);

  useEffect(() => {
    let div_alias = document.querySelector("#usuario-alias");
    if (!!div_alias) {
      div_alias.textContent = alias;
    }
  }, [alias]);
  
  
  return (
    <>
      <Head>
        <title>Inicio</title>
        <meta name="description" content="Generated by create next app" />
        <link rel="icon" href="/favicon.ico" />
        <script src="https://kit.fontawesome.com/1348fe1b4f.js" crossOrigin="anonymous" async></script>
      </Head>
      
      <NavigationBar usuario={usuario}/>
      <Container className='p-5' >
      
        <h3>Bienvenido!</h3>
        <p style={{paddingBottom:"20px"}}>
          Estimado cliente, ahora puedes abrir cuentas de ahorro con nosotros. <br/>
          Presiona el enlace requerido de acuerdo a tus necesidades. Ofrecemos los siguientes servicios:
        </p>


      </Container>

    </>
  )
}

export default Home
